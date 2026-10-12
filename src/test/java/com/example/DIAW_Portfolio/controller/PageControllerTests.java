package com.example.DIAW_Portfolio.controller;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.example.DIAW_Portfolio.service.PortfolioService;

import jakarta.servlet.http.Cookie;

@WebMvcTest(PageController.class)
@Import(PortfolioService.class)
class PageControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@ParameterizedTest
	@CsvSource({ "/, sobre", "/projetos, projetos", "/experiencias, experiencias" })
	void paginasRenderizam(String url, String nomeView) throws Exception {
		mockMvc.perform(get(url))
				.andExpect(status().isOk())
				.andExpect(view().name(nomeView));
	}

	@Test
	void sobreEmPortuguesPorPadrao() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(content().string(containsString("Sobre Mim")))
				.andExpect(content().string(containsString("lang=\"pt-BR\"")));
	}

	@Test
	void sobreEmInglesComParametroEGuardaCookie() throws Exception {
		mockMvc.perform(get("/").param("lang", "en"))
				.andExpect(content().string(containsString("About Me")))
				.andExpect(content().string(containsString("lang=\"en\"")))
				.andExpect(cookie().value("lang", "en"));
	}

	@Test
	void sobreExibeHabilidadesNosDoisIdiomas() throws Exception {
		mockMvc.perform(get("/").param("lang", "pt"))
				.andExpect(content().string(containsString("Habilidades e Competências")))
				.andExpect(content().string(containsString("Estruturas de Dados")))
				.andExpect(content().string(containsString("MIPS Assembly")));
		mockMvc.perform(get("/").param("lang", "en"))
				.andExpect(content().string(containsString("Skills")))
				.andExpect(content().string(containsString("Data Structures")));
	}

	@Test
	void sobreUsaIdiomaDoCookie() throws Exception {
		mockMvc.perform(get("/").cookie(new Cookie("lang", "en")))
				.andExpect(content().string(containsString("About Me")));
	}

	@Test
	void idiomaDesconhecidoMantemPortugues() throws Exception {
		mockMvc.perform(get("/").param("lang", "xx"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Sobre Mim")));
	}

	@Test
	void projetosExibeTimelineEFiltros() throws Exception {
		mockMvc.perform(get("/projetos"))
				.andExpect(model().attribute("projetos", hasSize(4)))
				.andExpect(content().string(containsString("data-filtro=\"Spring Boot\"")))
				.andExpect(content().string(containsString("PySnake")));
	}
}
