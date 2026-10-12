package com.example.DIAW_Portfolio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.DIAW_Portfolio.service.PortfolioService;

@Controller
public class PageController {

	private final PortfolioService portfolioService;

	public PageController(PortfolioService portfolioService) {
		this.portfolioService = portfolioService;
	}

	@GetMapping("/")
	public String sobre(Model model) {
		model.addAttribute("pagina", "sobre");
		return "sobre";
	}

	@GetMapping("/projetos")
	public String projetos(Model model) {
		model.addAttribute("pagina", "projetos");
		model.addAttribute("projetos", portfolioService.listarProjetos());
		return "projetos";
	}

	@GetMapping("/experiencias")
	public String experiencias(Model model) {
		model.addAttribute("pagina", "experiencias");
		model.addAttribute("experiencias", portfolioService.listarExperiencias());
		return "experiencias";
	}

	@GetMapping("/contato")
	public String contato(Model model) {
		model.addAttribute("pagina", "contato");
		return "contato";
	}

	// O envio real por e-mail será implementado na próxima sprint.
	@PostMapping("/contato")
	public String enviarContato(RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("aviso",
				"O envio de mensagens ainda está em desenvolvimento. Use os links de contato acima.");
		return "redirect:/contato";
	}
}
