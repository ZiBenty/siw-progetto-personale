package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.OCharacterService;
import it.uniroma3.siw.service.UserService;

@Controller
public class IndexController {
	@Autowired
	private CredentialsService credentialsService;
	@Autowired
	private OCharacterService characterService;
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("listCharacter", this.characterService.findAllPublic());
		return "index.html";
	}
	
	@GetMapping("/home")
	public String userHome(Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		return home(credentials.getUser().getId(), model);
	}
	
	@GetMapping("/home/{userId}")
	public String home(@PathVariable("userId") Long userId, Model model) {
		model.addAttribute("user", this.userService.getUser(userId));
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		if(credentials.getUser().getId() == userId)
		    model.addAttribute("listCharacter", this.userService.getUser(userId).getCharacters());
		else
			model.addAttribute("listCharacter", this.userService.getUser(userId).getPublicCharacters());
		return "home.html";
	}
	
	
}
