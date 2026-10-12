package com.example.DIAW_Portfolio.model;

/** A ordem das constantes define a ordem dos grupos na página Experiências. */
public enum TipoExperiencia {

	PROFISSIONAL("Experiência profissional", "bi-briefcase"),
	ESTAGIO("Estágios", "bi-person-workspace"),
	FREELA("Freelas", "bi-laptop"),
	OPEN_SOURCE("Open source", "bi-github"),
	FORMACAO("Formação acadêmica", "bi-mortarboard"),
	PROJETO_ACADEMICO("Projetos acadêmicos", "bi-kanban"),
	EVENTO("Eventos técnicos", "bi-calendar-event"),
	CURSO("Cursos", "bi-journal-bookmark");

	private final String titulo;
	private final String icone;

	TipoExperiencia(String titulo, String icone) {
		this.titulo = titulo;
		this.icone = icone;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getIcone() {
		return icone;
	}
}
