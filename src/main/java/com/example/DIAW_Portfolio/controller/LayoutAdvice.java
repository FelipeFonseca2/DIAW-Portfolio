package com.example.DIAW_Portfolio.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * Dados comuns a todas as páginas. As URLs absolutas são usadas nas tags
 * Open Graph (prévia do link no WhatsApp, LinkedIn etc.) e consideram os
 * cabeçalhos X-Forwarded-* do proxy do Render.
 */
@ControllerAdvice
public class LayoutAdvice {

	@ModelAttribute("urlBase")
	public String urlBase() {
		return ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
	}

	@ModelAttribute("urlAtual")
	public String urlAtual() {
		return ServletUriComponentsBuilder.fromCurrentRequestUri().replaceQuery(null).build().toUriString();
	}
}
