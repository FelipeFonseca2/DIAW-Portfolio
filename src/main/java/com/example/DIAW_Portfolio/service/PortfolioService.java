package com.example.DIAW_Portfolio.service;

import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.DIAW_Portfolio.model.Experiencia;
import com.example.DIAW_Portfolio.model.Projeto;

/**
 * Fonte dos dados exibidos no site. Na Sprint 01 os dados são fictícios e
 * servem apenas para validar o layout.
 */
@Service
public class PortfolioService {

	private final List<Projeto> projetos = List.of(
			new Projeto("Projeto Exemplo 3", "Descrição breve do projeto.",
					List.of("Java", "Spring Boot"), "https://github.com/FelipeFonseca2",
					null, YearMonth.of(2026, 6)),
			new Projeto("Projeto Exemplo 1", "Descrição breve do projeto.",
					List.of("HTML", "CSS", "JavaScript"), "https://github.com/FelipeFonseca2",
					null, YearMonth.of(2025, 3)),
			new Projeto("Projeto Exemplo 2", "Descrição breve do projeto.",
					List.of("Java"), "https://github.com/FelipeFonseca2",
					null, YearMonth.of(2025, 10)));

	private final List<Experiencia> experiencias = List.of(
			new Experiencia("Instituição Exemplo", "Cargo / Atividade", "2026 – atual",
					"Descrição breve da experiência."),
			new Experiencia("Evento Técnico Exemplo", "Participante", "2025",
					"Descrição breve da experiência."));

	/** Projetos em ordem cronológica, do mais antigo ao mais recente. */
	public List<Projeto> listarProjetos() {
		return projetos.stream()
				.sorted(Comparator.comparing(Projeto::data))
				.toList();
	}

	public List<Experiencia> listarExperiencias() {
		return experiencias;
	}
}
