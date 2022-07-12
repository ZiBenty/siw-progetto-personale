package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.OCharacterService;

@Controller
public class IndexController {
	@Autowired
	private CredentialsService credentialsService;
	@Autowired
	private OCharacterService characterService;
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("listCharacter", this.characterService.findAllPublic());
		return "index.html";
	}
	
	@GetMapping("/home")
	public String home(Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		if(credentials.getRole().equals(Credentials.ADMIN_ROLE))
			return "admin/home.html";
		else
			return "home.html";
	}
}
