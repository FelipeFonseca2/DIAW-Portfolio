# 🏷️ DIAW Portfolio 👨‍💻

> [!NOTE]
> Website de portfólio profissional para apresentar trajetória, projetos, experiências e formas de contato de maneira **moderna, responsiva e acessível**.

<table>
  <tr>
    <td width="800px">
      <div align="justify">
        Este repositório contém o <b>portfólio profissional de Felipe de Castro Fonseca</b>, desenvolvido na disciplina <i>Desenvolvimento de Interações e Aplicações Web</i> da <b>PUC Minas</b>. O site reúne uma apresentação bilíngue (português e inglês), uma <i>linha do tempo de projetos</i> com tecnologias, repositórios e demonstrações, um espaço para <i>experiências profissionais e acadêmicas</i> e uma página de <i>contato</i> com links diretos e formulário de envio por e-mail. A aplicação é construída com <b>Spring Boot</b> e <b>Thymeleaf</b> e será hospedada gratuitamente em nuvem.
      </div>
    </td>
    <td>
      <div>
        <img src="src/main/resources/static/img/favicon.svg" alt="Logo do Projeto" width="120px"/>
      </div>
    </td>
  </tr>
</table>

---

## 🚧 Status do Projeto

![Versão](https://img.shields.io/badge/Versão-Sprint_01-007ec6?style=for-the-badge) ![Java](https://img.shields.io/badge/Java-25-007ec6?style=for-the-badge&logo=openjdk&logoColor=white) ![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.1.1-007ec6?style=for-the-badge&logo=springboot&logoColor=white) ![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3.1-007ec6?style=for-the-badge&logo=thymeleaf&logoColor=white) ![Maven](https://img.shields.io/badge/Maven-3.10-007ec6?style=for-the-badge&logo=apachemaven&logoColor=white)

| Sprint | Entrega | Status |
| :--- | :--- | :---: |
| **Lab01S01** | Planejamento, wireframes, protótipo inicial, navegação e layout principal | ✅ |
| **Lab01S02** | Conteúdo das seções e envio do formulário de contato por e-mail | ⏳ |
| **Lab01S03** | Hospedagem em nuvem e apresentação final | ⏳ |

---

## 📚 Índice
- [Sobre o Projeto](#-sobre-o-projeto)
- [Funcionalidades Principais](#-funcionalidades-principais)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Arquitetura](#-arquitetura)
- [Instalação e Execução](#-instalação-e-execução)
- [Estrutura de Pastas](#-estrutura-de-pastas)
- [Demonstração](#-demonstração)
- [Testes](#-testes)
- [Autores](#-autores)

---

## 📝 Sobre o Projeto

O projeto surgiu como laboratório da disciplina **DIAW (PUC Minas)**, cujo objetivo é projetar, desenvolver e hospedar em nuvem um website de portfólio profissional pessoal.

O site resolve um problema comum de quem está entrando no mercado: reunir, em um único lugar, **quem eu sou, o que já construí e como me encontrar**. Em vez de informações espalhadas entre currículo, GitHub e LinkedIn, recrutadores e colegas encontram uma apresentação objetiva, projetos com demonstrações visuais e canais de contato diretos.

---

## ✨ Funcionalidades Principais

- 👤 **Sobre Mim:** Apresentação em português e inglês com formação, área de atuação, interesses e objetivos.
- 🗂️ **Projetos:** Linha do tempo do mais antigo ao mais recente, com nome, descrição, tecnologias, link do repositório e imagem/GIF de cada projeto.
- 💼 **Experiências:** Experiências profissionais, estágios, freelas, open source e eventos técnicos, com instituição, cargo, período e descrição.
- 📨 **Contato:** Ícones clicáveis (E-mail, WhatsApp, LinkedIn, GitHub) e formulário com envio por e-mail.
- 📱 **Design Responsivo:** Layout adaptado para desktop e mobile, com menu de navegação recolhível.
- 🌗 **Tema Claro/Escuro:** Segue a preferência do sistema, com alternância manual.

---

## 🛠 Tecnologias Utilizadas

### 💻 Front-end

* **Template Engine:** Thymeleaf 3.1 (fragmentos de layout reutilizáveis)
* **Linguagens:** HTML5, CSS3, JavaScript (ES6+)
* **Ícones e Fontes:** [Bootstrap Icons](https://icons.getbootstrap.com/) 1.11 e Google Fonts (Inter, Space Grotesk)

### 🖥️ Back-end

* **Linguagem/Runtime:** Java 25
* **Framework:** Spring Boot 4.1.1 (Spring Web MVC)
* **Build Tool:** Maven (via Maven Wrapper)
* **E-mail (previsto):** Spring Boot Starter Mail

### ⚙️ Infraestrutura & DevOps

* **Prototipação:** Figma
* **Cloud (previsto):** Render

---

## 🏗 Arquitetura

A aplicação segue o padrão **MVC** do Spring, renderizando as páginas no servidor:

- **Controller** (`PageController`) — mapeia as rotas `/`, `/projetos`, `/experiencias` e `/contato` e entrega os dados para as views.
- **Service** (`PortfolioService`) — fornece os projetos (ordenados cronologicamente) e as experiências exibidas no site.
- **Model** (`Projeto`, `Experiencia`) — records Java que representam o conteúdo do portfólio.
- **View** (templates Thymeleaf) — `fragments/layout.html` define `head`, cabeçalho com menu de navegação e rodapé, reaproveitados por todas as páginas.

A renderização no servidor dispensa um build de front-end separado e mantém o projeto em um único artefato, simples de hospedar.

---

## 🔧 Instalação e Execução

### Pré-requisitos

* **Java JDK:** Versão **25** ou superior
* **Git**

> O Maven não precisa estar instalado: o projeto usa o Maven Wrapper (`mvnw`).

### 📦 Instalação

```bash
git clone https://github.com/FelipeFonseca2/DIAW-Portfolio.git
cd DIAW-Portfolio
```

### ⚡ Como Executar a Aplicação

```bash
# Linux/macOS
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

🚀 *A aplicação estará disponível em **http://localhost:8080**.*

---

## 📂 Estrutura de Pastas

```
.
├── docs/
│   ├── prototipo/                  # 🖼️ Capturas de tela do protótipo
│   └── wireframes/                 # 📐 Wireframes exportados do Figma
├── src/
│   ├── main/
│   │   ├── java/com/example/DIAW_Portfolio/
│   │   │   ├── controller/         # 🎮 Rotas das páginas
│   │   │   ├── model/              # 🧬 Records de Projeto e Experiência
│   │   │   ├── service/            # ⚙️ Dados exibidos no portfólio
│   │   │   └── DiawPortfolioApplication.java
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── css/            # 🎨 Estilos globais e tema claro/escuro
│   │       │   ├── js/             # 🛠️ Menu mobile, tema e idioma
│   │       │   └── img/            # 🖼️ Imagens e logo
│   │       ├── templates/
│   │       │   ├── fragments/      # 🧱 Layout: head, cabeçalho e rodapé
│   │       │   └── *.html          # 📄 Páginas: sobre, projetos, experiencias, contato
│   │       └── application.properties
│   └── test/                       # 🧪 Testes das rotas
├── mvnw / mvnw.cmd                 # 📦 Maven Wrapper
└── pom.xml                         # 🛠️ Build e dependências
```

---

## 🎥 Demonstração

### 📐 Wireframes (Figma)

🔗 [Acesse o protótipo no Figma](<link-do-figma>)

| Sobre Mim | Projetos |
| :---: | :---: |
| <img src="docs/wireframes/sobre.png" alt="Wireframe Sobre Mim" width="400px"> | <img src="docs/wireframes/projetos.png" alt="Wireframe Projetos" width="400px"> |
| **Experiências** | **Contato** |
| <img src="docs/wireframes/experiencias.png" alt="Wireframe Experiências" width="400px"> | <img src="docs/wireframes/contato.png" alt="Wireframe Contato" width="400px"> |

### 🌐 Protótipo Inicial

| Sobre Mim | Projetos |
| :---: | :---: |
| <img src="docs/prototipo/sobre.png" alt="Página Sobre Mim" width="400px"> | <img src="docs/prototipo/projetos.png" alt="Página Projetos" width="400px"> |
| **Experiências** | **Contato** |
| <img src="docs/prototipo/experiencias.png" alt="Página Experiências" width="400px"> | <img src="docs/prototipo/contato.png" alt="Página Contato" width="400px"> |

**Versão mobile**

<img src="docs/prototipo/mobile.png" alt="Páginas na versão mobile" width="820px">

---

## 🧪 Testes

Testes das rotas e do envio do formulário com **JUnit 5** e **MockMvc**:

```bash
./mvnw test
```

---

## 👥 Autores

| 👤 Nome | 🖼️ Foto | :octocat: GitHub | 📤 Gmail |
|---------|----------|-----------------|-----------|
| Felipe Fonseca | <div align="center"><img src="https://github.com/FelipeFonseca2.png" width="70px" height="70px"></div> | <div align="center"><a href="https://github.com/FelipeFonseca2"><img src="https://joaopauloaramuni.github.io/image/github6.png" width="50px" height="50px"></a></div> | <div align="center"><a href="mailto:felipefonseca2006@gmail.com"><img src="https://joaopauloaramuni.github.io/image/gmail3.png" width="50px" height="50px"></a></div> |
