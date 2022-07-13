package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.service.UserService;

@Controller
public class AdminController {
	@Autowired
	private UserService userService;
	
	@GetMapping("admin/users")
	public String getUsers(Model model) {
		model.addAttribute("listUsers", this.userService.getAllUsers());
		return "admin/users.html";
	}
	
	@GetMapping("admin/ban/{userId}")
	public String banUser(@PathVariable("userId") Long userId, Model model) {
		this.userService.banUser(this.userService.getUser(userId));
		return "redirect:/admin/users";
	}
}
