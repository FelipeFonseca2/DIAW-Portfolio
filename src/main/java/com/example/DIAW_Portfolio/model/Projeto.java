package com.example.DIAW_Portfolio.model;

import java.time.YearMonth;
import java.util.List;

public record Projeto(
		String nome,
		String descricao,
		List<String> tecnologias,
		String repositorioUrl,
		String imagemUrl,
		YearMonth data) {
}
