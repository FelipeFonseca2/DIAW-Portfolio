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

// Apresentação PT/EN
document.querySelectorAll(".lang-switch button").forEach((botao) => {
	botao.addEventListener("click", () => {
		const lang = botao.dataset.lang;
		document.querySelectorAll(".lang-switch button").forEach((b) =>
			b.setAttribute("aria-selected", b === botao));
		document.querySelectorAll("[data-lang-content]").forEach((el) => {
			el.hidden = el.dataset.langContent !== lang;
		});
	});
});
