package it.uniroma3.siw.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.controller.validator.OCharacterValidator;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.OCharacter;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.OCharacterService;
import it.uniroma3.siw.service.UserService;
import it.uniroma3.siw.util.FileUploadUtil;

@Controller
public class OCharacterController {
	@Autowired
	private OCharacterService characterService;
	@Autowired
	private UserService userService;
	@Autowired
	private CredentialsService credentialsService;
	@Autowired
	private OCharacterValidator characterValidator;
	
	private String saveAndGetFileName(Long userId, MultipartFile multipartFile) throws IOException {
		String uploadDir = "user-pics/" + userId +"/";
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		return fileName;
		
	}
	
	//salva e ritorna il personaggio salvato
	@PostMapping("/character/{userId}")
	public String newOCharacter(@PathVariable("userId") Long userId, 
			@RequestParam("image") MultipartFile multipartFile, 
			@Valid @ModelAttribute("character") OCharacter character, 
			BindingResult bindingResult, Model model) throws IOException {
		User user = this.userService.getUser(userId);
		this.characterValidator.validate(character, bindingResult);
		if(!bindingResult.hasErrors()) {
			if (character.getId() == null) { //personaggio creato
				user.addCharacter(character);
				//se una nuova immagine è stata inserita, la sostituiamo, altrimenti no
				if (multipartFile.getOriginalFilename() != "") {
					String fileName = saveAndGetFileName(userId, multipartFile);
					character.setPic(fileName);
				}
				this.characterService.save(character);
				model.addAttribute("character", character);
			} else { //personaggio modificato
				OCharacter toModify = this.characterService.findById(character.getId()); //entità originale da modificare
				//se una nuova immagine è stata inserita, la sostituiamo, altrimenti no
				if (multipartFile.getOriginalFilename() != "") {
					String fileName = saveAndGetFileName(userId, multipartFile);
					character.setPic(fileName);
				}
				this.characterService.edit(toModify, character);
				model.addAttribute(toModify);
				model.addAttribute("listChapters", toModify.getStory());
			}
			return "character.html";
		} else {
			model.addAttribute("user", user);
			return "characterForm.html";
		}
	}
	
	//richiede la form per creare un personaggio
	@GetMapping("/character/new")
	public String getOCharacterForm(Model model) {
		model.addAttribute("character", new OCharacter());
		//prende l'utente loggato
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		model.addAttribute("user", credentials.getUser());
		return "characterForm.html";
	}
	
	//richiede la form per modificare un personaggio
	@GetMapping("/character/edit/{id}")
	public String editOCharacter(@PathVariable("id") Long id, Model model) {
		model.addAttribute("character", this.characterService.findById(id));
		//prende l'utente loggato
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		model.addAttribute("user", credentials.getUser());
		return "characterForm.html";
	}
	
	//richiede il personaggio con un specifico id
	@GetMapping("/character/{id}")
	public String getOCharacter(@PathVariable("id") Long id, Model model) {
		OCharacter character = this.characterService.findById(id);
		model.addAttribute("character", character);
		model.addAttribute("listChapters", character.getStory());
		if (character.getPic() != null) {
			model.addAttribute("image", character.getPicImagePath(character.getUser().getId()));
		} else {
			model.addAttribute("image", "/images/no-image.jpg");
		}
		return "character.html";
	}
	
	//richiede la lista dei personaggi creati
	@GetMapping("/userCharacters")
	public String getUserOCharacters(Model model) {
		//prende l'utente loggato
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		model.addAttribute("user", credentials.getUser());
		model.addAttribute("listCharacter", credentials.getUser().getCharacters());
		return "userCharacters.html";
	}
	
	@GetMapping("/character/delete/{id}")
	public String deleteOCharacter(@PathVariable("id") Long id, Model model){
		OCharacter character = this.characterService.findById(id);
		//cancella l'immagine dal server se esiste
		if (character.getPic() != null) {
			try {
				Files.deleteIfExists(Paths.get(character.getPicImagePath(character.getUser().getId()).substring(1)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		character.getUser().removeCharacter(character);
		this.characterService.delete(character);
		return "redirect:/userCharacters";
	}
	
}
