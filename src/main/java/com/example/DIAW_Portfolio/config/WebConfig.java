package com.example.DIAW_Portfolio.config;

import java.time.Duration;
import java.util.List;
import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Idioma da página Sobre Mim: português por padrão, trocado com {@code ?lang=en}
 * ou {@code ?lang=pt} e lembrado em um cookie.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	public static final Locale PORTUGUES = Locale.forLanguageTag("pt-BR");

	@Bean
	public LocaleResolver localeResolver() {
		CookieLocaleResolver resolver = new CookieLocaleResolver("lang");
		resolver.setDefaultLocale(PORTUGUES);
		resolver.setCookieMaxAge(Duration.ofDays(365));
		return resolver;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor() {
			private static final List<String> SUPORTADOS = List.of("pt", "en");

			@Override
			public boolean preHandle(HttpServletRequest request,
					HttpServletResponse response, Object handler) throws ServletException {
				String lang = request.getParameter(getParamName());
				// Ignora valores desconhecidos em vez de aceitar qualquer locale.
				if (lang != null && !SUPORTADOS.contains(lang)) {
					return true;
				}
				return super.preHandle(request, response, handler);
			}
		};
		interceptor.setParamName("lang");
		registry.addInterceptor(interceptor);
	}
}
