// Menu de navegação (mobile)
const navToggle = document.querySelector(".nav-toggle");
const nav = document.querySelector(".nav");

navToggle?.addEventListener("click", () => {
	const aberto = nav.classList.toggle("aberto");
	navToggle.setAttribute("aria-expanded", aberto);
	navToggle.querySelector("i").className = aberto ? "bi bi-x-lg" : "bi bi-list";
});

// Tema claro/escuro
document.querySelector(".theme-toggle")?.addEventListener("click", () => {
	const atual = document.documentElement.dataset.theme
		?? (matchMedia("(prefers-color-scheme: dark)").matches ? "dark" : "light");
	const novo = atual === "dark" ? "light" : "dark";
	document.documentElement.dataset.theme = novo;
	try {
		localStorage.setItem("tema", novo);
	} catch (e) { }
});

// Animação de entrada dos itens ao rolar a página
const revelaveis = document.querySelectorAll(".reveal");
if ("IntersectionObserver" in window && !matchMedia("(prefers-reduced-motion: reduce)").matches) {
	const observer = new IntersectionObserver((entradas) => {
		entradas.forEach((entrada) => {
			if (entrada.isIntersecting) {
				entrada.target.classList.add("visivel");
				observer.unobserve(entrada.target);
			}
		});
	}, { threshold: 0.15 });
	revelaveis.forEach((el) => {
		el.classList.add("animar");
		observer.observe(el);
	});
}

// Projetos: filtro por tecnologia
const filtros = document.querySelectorAll(".filtro");
const itensTimeline = document.querySelectorAll(".timeline-item");
const statusFiltro = document.querySelector(".filtro-status");

filtros.forEach((botao) => {
	botao.addEventListener("click", () => {
		const tecnologia = botao.dataset.filtro;
		filtros.forEach((b) => b.setAttribute("aria-pressed", b === botao));

		let visiveis = 0;
		itensTimeline.forEach((item) => {
			const mostra = !tecnologia || item.dataset.tecnologias.split("|").includes(tecnologia);
			item.hidden = !mostra;
			if (mostra) {
				item.classList.add("visivel");
				visiveis++;
			}
		});

		const texto = visiveis === 1 ? "1 projeto" : `${visiveis} projetos`;
		statusFiltro.textContent = tecnologia ? `${texto} com ${tecnologia}` : texto;
	});
});

// Projetos: imagem ampliada
const lightbox = document.querySelector(".lightbox");
if (lightbox) {
	const imagemAmpliada = lightbox.querySelector("img");
	document.querySelectorAll(".media-zoom").forEach((botao) => {
		botao.addEventListener("click", () => {
			const img = botao.querySelector("img");
			imagemAmpliada.src = img.currentSrc || img.src;
			imagemAmpliada.alt = img.alt;
			lightbox.showModal();
		});
	});
	lightbox.querySelector(".lightbox-fechar").addEventListener("click", () => lightbox.close());
	// Fecha ao clicar fora da imagem
	lightbox.addEventListener("click", (e) => {
		if (e.target === lightbox) lightbox.close();
	});
}

// Contato: validação no navegador (o servidor valida novamente)
const formContato = document.querySelector(".contato-form");
if (formContato) {
	const campos = {
		nome: (v) => !v ? "Informe seu nome." : v.length > 100 ? "O nome deve ter no máximo 100 caracteres." : "",
		email: (v) => !v ? "Informe seu e-mail."
			: !/^[^@\s]+@[^@\s]+\.[^@\s]+$/.test(v) ? "Informe um e-mail válido." : "",
		mensagem: (v) => !v ? "Escreva sua mensagem."
			: v.length < 10 || v.length > 2000 ? "A mensagem deve ter entre 10 e 2000 caracteres." : "",
	};

	const validar = (nome) => {
		const input = formContato.elements[nome];
		const erro = campos[nome](input.value.trim());
		input.closest(".campo").classList.toggle("invalido", !!erro);
		input.setAttribute("aria-invalid", !!erro);
		document.getElementById(`${nome}-erro`).textContent = erro;
		return !erro;
	};

	Object.keys(campos).forEach((nome) => {
		const input = formContato.elements[nome];
		if (input.closest(".campo").classList.contains("invalido")) input.setAttribute("aria-invalid", "true");
		// Valida ao sair do campo e, depois de um erro, a cada digitação
		input.addEventListener("blur", () => input.value && validar(nome));
		input.addEventListener("input", () => {
			if (input.closest(".campo").classList.contains("invalido")) validar(nome);
		});
	});

	const mensagem = formContato.elements.mensagem;
	const contador = document.getElementById("mensagem-contador");
	const atualizarContador = () => {
		contador.textContent = `${mensagem.value.length}/2000`;
	};
	mensagem.addEventListener("input", atualizarContador);
	atualizarContador();

	formContato.addEventListener("submit", (e) => {
		const resultados = Object.keys(campos).map(validar);
		if (resultados.includes(false)) {
			e.preventDefault();
			formContato.querySelector(".invalido input, .invalido textarea").focus();
			return;
		}
		const botao = formContato.querySelector("button[type=submit]");
		botao.disabled = true;
		botao.querySelector("span").textContent = "Enviando...";
	});
}
