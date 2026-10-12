package com.example.DIAW_Portfolio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.DIAW_Portfolio.service.PortfolioService;

@Controller
public class PageController {

	private final PortfolioService portfolioService;

	public PageController(PortfolioService portfolioService) {
		this.portfolioService = portfolioService;
	}

	// O idioma (?lang=pt|en) é tratado pelo LocaleChangeInterceptor em WebConfig.
	@GetMapping("/")
	public String sobre(Model model) {
		model.addAttribute("pagina", "sobre");
		return "sobre";
	}

	@GetMapping("/projetos")
	public String projetos(Model model) {
		model.addAttribute("pagina", "projetos");
		model.addAttribute("projetos", portfolioService.listarProjetos());
		model.addAttribute("tecnologias", portfolioService.listarTecnologias());
		return "projetos";
	}

	@GetMapping("/experiencias")
	public String experiencias(Model model) {
		model.addAttribute("pagina", "experiencias");
		model.addAttribute("grupos", portfolioService.listarExperienciasPorTipo());
		return "experiencias";
	}
}
