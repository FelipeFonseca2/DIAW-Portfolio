package com.example.DIAW_Portfolio;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

/** Sobe o servidor real para testar o comportamento de produção. */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SiteIntegrationTests {

	@LocalServerPort
	private int porta;

	private final HttpClient http = HttpClient.newHttpClient();

	private HttpResponse<String> get(String caminho, String... cabecalhos) throws Exception {
		HttpRequest.Builder req = HttpRequest.newBuilder(URI.create("http://localhost:" + porta + caminho));
		if (cabecalhos.length > 0) {
			req.headers(cabecalhos);
		}
		return http.send(req.build(), HttpResponse.BodyHandlers.ofString());
	}

	@Test
	void paginaInexistenteMostraPagina404DoSite() throws Exception {
		HttpResponse<String> resposta = get("/pagina-que-nao-existe", "Accept", "text/html");

		assertThat(resposta.statusCode()).isEqualTo(404);
		assertThat(resposta.body()).contains("Página não encontrada", "Ir para o início");
	}

	@Test
	void paginasTemTagsDePreviaDoLink() throws Exception {
		String html = get("/projetos").body();

		assertThat(html).contains(
				"<meta property=\"og:url\" content=\"http://localhost:" + porta + "/projetos\">",
				"og:image\" content=\"http://localhost:" + porta + "/img/og-image.jpg\"");
	}

	@Test
	void urlsUsamCabecalhosDoProxy() throws Exception {
		String html = get("/", "X-Forwarded-Proto", "https", "X-Forwarded-Host", "exemplo.onrender.com").body();

		assertThat(html).contains("<link rel=\"canonical\" href=\"https://exemplo.onrender.com/\">");
	}

	@Test
	void arquivosEstaticosSaoVersionadosEComCache() throws Exception {
		Matcher css = Pattern.compile("href=\"(/css/style-[0-9a-f]+\\.css)\"").matcher(get("/").body());
		assertThat(css.find()).as("CSS com hash no nome").isTrue();

		HttpResponse<String> resposta = get(css.group(1));
		assertThat(resposta.statusCode()).isEqualTo(200);
		assertThat(resposta.headers().firstValue("Cache-Control")).hasValueSatisfying(
				valor -> assertThat(valor).contains("max-age=31536000"));
	}
}
