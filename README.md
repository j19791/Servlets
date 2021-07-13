## Servlets
Projeto do curso Servlets da Alura

### Ambiente

- Java (JRE ou JDK) >= versão 8
- Eclipse IDE for Java EE Developers : pacote de ferramentas p/ criação de app web
	- Perspectiva Jaca EE (default)
- Servidor: TomCat >= 7 (versão c/ suporte ao Servlet >= 3.0
	- extrair o zip baixado
	- associar o TomCat ao Eclipse
		- aba Servers:
		- Define a new Server: Apache > TomCat 9.0
		- Tomcat installation directory: diretório em q o TomCat foi extraído na máquina
		- JRE
		- acessar c/ http://localhost:8080 : porta do Tomcat diferente da porta padrão (80) 

### Projeto

- Criar projeto
	- File > New > Dynamic Web Project
		- Project Name: gerenciador
		- Target runtime: Apache Tomcat 9
		- Dynamic web module version 4.0
		- Source folder on build path: scr (pasta q abrigara as classes). build\classes: classes compiladas pelo Eclipse
		- Context root: como o projeto é chamado na url 
		- Content directory: WebContent: arquivos html, etc
		- Generate web.xml deployment descriptor
- Associar o projeto com o servidor
- Criar o arquivo bem-vindo.html dentro da pasta WebContent 
- Acessar o endereço: localhost:8080/gerenciador/bem-vindo.html

### Servlet
- Protocolo http: navegador (requisiçao) e servidor (resposta)
- objeto chamado via HTTP, executado para gerar uma resposta HTTP dinâmica
- implementação: ver OiMundoServlet.java
	- extender usando HttpServlet
	- sobrescrever o método q atende uma requisição HTTP: service()
	- o void esta correto pois toda resposta não será por meio do retorno do método
	- automaticamente recebemos as referências que apontam p/ os objetos que representam requisição e resposta: (HttpServletRequest req, HttpServletResponse resp)
	- apelido no endereço URL que mapeia (remete) ao Servlet `@WebServlet(urlPattern="/oi")`
	- devolve a resposta via fluxo binário (PDF, imagem) ou getWriter() p/ devolver HTML
	- getWriter() devolve um PrintWriter (java.io) c/ exceção checked
	- `out.println("<html>")` devolve conteúdo textual p/ o navegador

### Sequencia de implementações
- bem-vindo.html
- OiMundoServlet.java

