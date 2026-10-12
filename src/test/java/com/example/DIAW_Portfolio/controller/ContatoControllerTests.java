package com.example.DIAW_Portfolio.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.example.DIAW_Portfolio.model.ContatoForm;
import com.example.DIAW_Portfolio.service.EmailService;
import com.example.DIAW_Portfolio.service.EmailService.EmailException;

@WebMvcTest(ContatoController.class)
class ContatoControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private EmailService emailService;

	private static MockHttpServletRequestBuilder envioValido() {
		return post("/contato")
				.param("nome", "Maria Silva")
				.param("email", "maria@exemplo.com")
				.param("mensagem", "Olá, gostaria de conversar sobre uma vaga.");
	}

	@Test
	void paginaDeContatoRenderiza() throws Exception {
		mockMvc.perform(get("/contato"))
				.andExpect(status().isOk())
				.andExpect(view().name("contato"))
				.andExpect(model().attributeExists("contatoForm"));
	}

	@Test
	void envioValidoMandaEmailERedireciona() throws Exception {
		mockMvc.perform(envioValido())
				.andExpect(redirectedUrl("/contato#formulario"))
				.andExpect(flash().attributeExists("sucesso"));
		verify(emailService).enviarContato(any(ContatoForm.class));
	}

	@Test
	void camposVaziosMostramErros() throws Exception {
		mockMvc.perform(post("/contato"))
				.andExpect(status().isOk())
				.andExpect(view().name("contato"))
				.andExpect(model().attributeHasFieldErrors("contatoForm", "nome", "email", "mensagem"));
		verify(emailService, never()).enviarContato(any());
	}

	@Test
	void formatoInvalidoMostraErros() throws Exception {
		mockMvc.perform(post("/contato")
				.param("nome", "Maria")
				.param("email", "maria@")
				.param("mensagem", "curta"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasFieldErrors("contatoForm", "email", "mensagem"));
		verify(emailService, never()).enviarContato(any());
	}

	@Test
	void honeypotPreenchidoNaoEnviaEmail() throws Exception {
		mockMvc.perform(envioValido().param("website", "http://spam.example"))
				.andExpect(redirectedUrl("/contato#formulario"))
				.andExpect(flash().attributeExists("sucesso"));
		verify(emailService, never()).enviarContato(any());
	}

	@Test
	void falhaNoEnvioMostraErroEMantemDados() throws Exception {
		doThrow(new EmailException("falha")).when(emailService).enviarContato(any());

		mockMvc.perform(envioValido())
				.andExpect(redirectedUrl("/contato#formulario"))
				.andExpect(flash().attributeExists("erro"))
				.andExpect(flash().attributeExists("contatoForm"));
	}
}
