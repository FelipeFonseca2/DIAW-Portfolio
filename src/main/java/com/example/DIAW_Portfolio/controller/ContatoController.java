package com.example.DIAW_Portfolio.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.DIAW_Portfolio.model.ContatoForm;
import com.example.DIAW_Portfolio.service.EmailService;
import com.example.DIAW_Portfolio.service.EmailService.EmailException;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/contato")
public class ContatoController {

	private static final Logger log = LoggerFactory.getLogger(ContatoController.class);
	private static final String MENSAGEM_SUCESSO = "Mensagem enviada! Obrigado pelo contato, responderei em breve.";

	private final EmailService emailService;

	public ContatoController(EmailService emailService) {
		this.emailService = emailService;
	}

	@ModelAttribute("pagina")
	public String pagina() {
		return "contato";
	}

	@GetMapping
	public String contato(Model model) {
		if (!model.containsAttribute("contatoForm")) {
			model.addAttribute("contatoForm", new ContatoForm());
		}
		return "contato";
	}

	@PostMapping
	public String enviar(@Valid @ModelAttribute("contatoForm") ContatoForm form, BindingResult result,
			RedirectAttributes redirectAttributes) {
		if (form.isBot()) {
			// Responde como sucesso para não dar pistas ao bot.
			redirectAttributes.addFlashAttribute("sucesso", MENSAGEM_SUCESSO);
			return "redirect:/contato";
		}
		if (result.hasErrors()) {
			return "contato";
		}

		try {
			emailService.enviarContato(form);
			redirectAttributes.addFlashAttribute("sucesso", MENSAGEM_SUCESSO);
		} catch (EmailException e) {
			log.error("Erro ao enviar mensagem de contato", e);
			redirectAttributes.addFlashAttribute("erro",
					"Não foi possível enviar sua mensagem agora. Tente novamente mais tarde ou use um dos contatos acima.");
			redirectAttributes.addFlashAttribute("contatoForm", form);
		}
		// Post/Redirect/Get: evita reenvio ao atualizar a página.
		return "redirect:/contato";
	}
}
