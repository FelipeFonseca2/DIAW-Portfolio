package com.example.DIAW_Portfolio.model;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

/**
 * @param imagemUrl caminho da imagem ou GIF do projeto; {@code null} exibe um
 *                  placeholder.
 * @param siteUrl   endereço do projeto publicado, se houver.
 */
public record Projeto(
		String nome,
		String contexto,
		String descricao,
		List<String> tecnologias,
		String repositorioUrl,
		String siteUrl,
		String imagemUrl,
		YearMonth data) {

	private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("MMM yyyy", Locale.forLanguageTag("pt-BR"));

	/** Ex.: "set. 2026". */
	public String mesAno() {
		return data.format(FORMATO);
	}
}
