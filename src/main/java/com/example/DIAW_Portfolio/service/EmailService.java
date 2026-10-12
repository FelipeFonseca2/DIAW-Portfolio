package com.example.DIAW_Portfolio.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import com.example.DIAW_Portfolio.model.ContatoForm;

/**
 * Envia as mensagens do formulário de contato pela API HTTP do Resend
 * (https://resend.com/docs/api-reference/emails/send-email). Usar HTTP em vez
 * de SMTP permite o envio em hospedagens que bloqueiam portas SMTP.
 */
@Service
public class EmailService {

	private final RestClient restClient;
	private final String apiKey;
	private final String remetente;
	private final String destinatario;

	public EmailService(
			@Value("${resend.api-key}") String apiKey,
			@Value("${contato.remetente}") String remetente,
			@Value("${contato.destinatario}") String destinatario) {
		this.restClient = RestClient.create("https://api.resend.com");
		this.apiKey = apiKey;
		this.remetente = remetente;
		this.destinatario = destinatario;
	}

	public void enviarContato(ContatoForm form) {
		if (apiKey == null || apiKey.isBlank()) {
			throw new EmailException("RESEND_API_KEY não configurada.");
		}

		String nome = form.getNome().strip().replaceAll("[\\r\\n]+", " ");
		String corpo = """
				Nova mensagem enviada pelo portfólio.

				Nome: %s
				E-mail: %s

				%s
				""".formatted(nome, form.getEmail().strip(), form.getMensagem().strip());

		Map<String, Object> email = Map.of(
				"from", remetente,
				"to", List.of(destinatario),
				"reply_to", form.getEmail().strip(),
				"subject", "Portfólio: mensagem de " + nome,
				"text", corpo);

		try {
			restClient.post()
					.uri("/emails")
					.header("Authorization", "Bearer " + apiKey)
					.contentType(MediaType.APPLICATION_JSON)
					.body(email)
					.retrieve()
					.toBodilessEntity();
		} catch (RestClientException e) {
			throw new EmailException("Falha ao enviar e-mail pelo Resend.", e);
		}
	}

	public static class EmailException extends RuntimeException {

		public EmailException(String message) {
			super(message);
		}

		public EmailException(String message, Throwable cause) {
			super(message, cause);
		}
	}
}
