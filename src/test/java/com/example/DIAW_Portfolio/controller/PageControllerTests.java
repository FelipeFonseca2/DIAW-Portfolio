package com.example.DIAW_Portfolio.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.example.DIAW_Portfolio.service.PortfolioService;

@WebMvcTest(PageController.class)
@Import(PortfolioService.class)
class PageControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@ParameterizedTest
	@CsvSource({ "/, sobre", "/projetos, projetos", "/experiencias, experiencias", "/contato, contato" })
	void paginasRenderizam(String url, String nomeView) throws Exception {
		mockMvc.perform(get(url))
				.andExpect(status().isOk())
				.andExpect(view().name(nomeView));
	}

	@Test
	void envioDeContatoRedirecionaComAviso() throws Exception {
		mockMvc.perform(post("/contato"))
				.andExpect(redirectedUrl("/contato"))
				.andExpect(flash().attributeExists("aviso"));
	}
}
