package com.example.DIAW_Portfolio.model;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

/**
 * @param inicio {@code null} quando a data não é informada (ex.: cursos e
 *               formação já concluída); nesse caso não há período exibido.
 * @param fim    {@code null} quando a experiência ainda está em andamento.
 */
public record Experiencia(
		TipoExperiencia tipo,
		String instituicao,
		String cargo,
		YearMonth inicio,
		YearMonth fim,
		String descricao,
		List<String> competencias) {

	private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("MMM/yyyy", Locale.forLanguageTag("pt-BR"));

	public boolean emAndamento() {
		return inicio != null && fim == null;
	}

	/** Ex.: "fev./2025 – atual", "mai./2026 – jun./2026" ou {@code null} sem datas. */
	public String periodo() {
		if (inicio == null) {
			return null;
		}
		String termino = emAndamento() ? "atual" : fim.format(FORMATO);
		return inicio.format(FORMATO) + " – " + termino;
	}
}
