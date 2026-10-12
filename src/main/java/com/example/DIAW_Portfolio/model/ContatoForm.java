package com.example.DIAW_Portfolio.model;

import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Campos obrigatórios são validados primeiro; formato e tamanho só depois, para
 * que cada campo mostre uma única mensagem de erro por vez.
 */
@GroupSequence({ ContatoForm.class, ContatoForm.Formato.class })
public class ContatoForm {

	interface Formato {
	}

	@NotBlank(message = "Informe seu nome.")
	@Size(max = 100, groups = Formato.class, message = "O nome deve ter no máximo {max} caracteres.")
	private String nome;

	@NotBlank(message = "Informe seu e-mail.")
	@Email(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$", groups = Formato.class, message = "Informe um e-mail válido.")
	@Size(max = 254, groups = Formato.class, message = "O e-mail deve ter no máximo {max} caracteres.")
	private String email;

	@NotBlank(message = "Escreva sua mensagem.")
	@Size(min = 10, max = 2000, groups = Formato.class, message = "A mensagem deve ter entre {min} e {max} caracteres.")
	private String mensagem;

	/** Campo invisível para pessoas; se vier preenchido, o envio é de um bot. */
	private String website;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public boolean isBot() {
		return website != null && !website.isBlank();
	}
}
