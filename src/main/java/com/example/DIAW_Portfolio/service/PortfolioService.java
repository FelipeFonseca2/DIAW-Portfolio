package com.example.DIAW_Portfolio.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.example.DIAW_Portfolio.model.Experiencia;
import com.example.DIAW_Portfolio.model.Projeto;
import com.example.DIAW_Portfolio.model.TipoExperiencia;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

/**
 * Carrega o conteúdo do portfólio dos arquivos JSON em {@code resources/data}.
 * Para adicionar um projeto ou experiência basta editar esses arquivos.
 */
@Service
public class PortfolioService {

	private final List<Projeto> projetos;
	private final List<Experiencia> experiencias;

	public PortfolioService(JsonMapper jsonMapper) {
		this.projetos = ler(jsonMapper, "data/projetos.json", new TypeReference<>() {
		});
		this.experiencias = ler(jsonMapper, "data/experiencias.json", new TypeReference<>() {
		});
	}

	/** Projetos em ordem cronológica, do mais antigo ao mais recente. */
	public List<Projeto> listarProjetos() {
		return projetos.stream()
				.sorted(Comparator.comparing(Projeto::data))
				.toList();
	}

	/** Tecnologias distintas dos projetos, em ordem alfabética (usadas no filtro). */
	public List<String> listarTecnologias() {
		TreeSet<String> tecnologias = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
		projetos.forEach(p -> tecnologias.addAll(p.tecnologias()));
		return List.copyOf(tecnologias);
	}

	/**
	 * Experiências agrupadas por tipo (na ordem do enum) e, dentro de cada grupo,
	 * da mais recente para a mais antiga (as sem data ficam por último, na ordem
	 * do JSON). Grupos vazios são omitidos.
	 */
	public Map<TipoExperiencia, List<Experiencia>> listarExperienciasPorTipo() {
		Map<TipoExperiencia, List<Experiencia>> grupos = new LinkedHashMap<>();
		for (TipoExperiencia tipo : TipoExperiencia.values()) {
			List<Experiencia> doTipo = experiencias.stream()
					.filter(e -> e.tipo() == tipo)
					.sorted(Comparator.comparing(Experiencia::emAndamento)
							.thenComparing(Experiencia::inicio, Comparator.nullsFirst(Comparator.naturalOrder()))
							.reversed())
					.toList();
			if (!doTipo.isEmpty()) {
				grupos.put(tipo, doTipo);
			}
		}
		return grupos;
	}

	private static <T> List<T> ler(JsonMapper jsonMapper, String caminho, TypeReference<List<T>> tipo) {
		try (InputStream in = new ClassPathResource(caminho).getInputStream()) {
			return List.copyOf(jsonMapper.readValue(in, tipo));
		} catch (IOException e) {
			throw new UncheckedIOException("Não foi possível ler " + caminho, e);
		}
	}
}
