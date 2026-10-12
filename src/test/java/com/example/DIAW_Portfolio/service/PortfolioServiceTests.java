package com.example.DIAW_Portfolio.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import com.example.DIAW_Portfolio.model.Experiencia;
import com.example.DIAW_Portfolio.model.Projeto;
import com.example.DIAW_Portfolio.model.TipoExperiencia;

import tools.jackson.databind.json.JsonMapper;

class PortfolioServiceTests {

	private final PortfolioService service = new PortfolioService(JsonMapper.builder().build());

	@Test
	void projetosEmOrdemCronologica() {
		List<Projeto> projetos = service.listarProjetos();

		assertThat(projetos).isNotEmpty();
		assertThat(projetos).extracting(Projeto::data).isSorted();
	}

	@Test
	void imagensDosProjetosExistem() {
		assertThat(service.listarProjetos())
				.filteredOn(p -> p.imagemUrl() != null)
				.allSatisfy(p -> assertThat(new ClassPathResource("static" + p.imagemUrl()).exists())
						.as("imagem de %s: %s", p.nome(), p.imagemUrl())
						.isTrue());
	}

	@Test
	void tecnologiasSemRepeticao() {
		List<String> tecnologias = service.listarTecnologias();

		assertThat(tecnologias).doesNotHaveDuplicates().contains("Java", "Python");
	}

	@Test
	void experienciasAgrupadasPorTipoDaMaisRecente() {
		Map<TipoExperiencia, List<Experiencia>> grupos = service.listarExperienciasPorTipo();

		assertThat(grupos).isNotEmpty().allSatisfy((tipo, lista) -> {
			assertThat(lista).isNotEmpty().allMatch(e -> e.tipo() == tipo);
			// Em andamento primeiro; depois por data de início decrescente; sem data por último
			for (int i = 1; i < lista.size(); i++) {
				Experiencia anterior = lista.get(i - 1);
				Experiencia atual = lista.get(i);
				if (anterior.emAndamento() == atual.emAndamento()) {
					if (atual.inicio() != null) {
						assertThat(anterior.inicio()).isNotNull().isAfterOrEqualTo(atual.inicio());
					}
				} else {
					assertThat(anterior.emAndamento()).isTrue();
				}
			}
		});
	}

	@Test
	void experienciaSemDataNaoTemPeriodo() {
		assertThat(service.listarExperienciasPorTipo().get(TipoExperiencia.CURSO))
				.isNotEmpty()
				.allSatisfy(e -> assertThat(e.periodo()).isNull());
	}
}
