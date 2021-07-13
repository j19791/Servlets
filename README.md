## Servlets
Projeto do curso Servlets da Alura

### Ambiente

- Java (JRE ou JDK) >= vers�o 8
- Eclipse IDE for Java EE Developers : pacote de ferramentas p/ cria��o de app web
	- Perspectiva Jaca EE (default)
- Servidor: TomCat >= 7 (vers�o c/ suporte ao Servlet >= 3.0
	- extrair o zip baixado
	- associar o TomCat ao Eclipse
		- aba Servers:
		- Define a new Server: Apache > TomCat 9.0
		- Tomcat installation directory: diret�rio em q o TomCat foi extra�do na m�quina
		- JRE
		- acessar c/ http://localhost:8080 : porta do Tomcat diferente da porta padr�o (80) 

### Projeto

- Criar projeto
	- File > New > Dynamic Web Project
		- Project Name: gerenciador
		- Target runtime: Apache Tomcat 9
		- Dynamic web module version 4.0
		- Source folder on build path: scr (pasta q abrigara as classes). build\classes: classes compiladas pelo Eclipse
		- Context root: como o projeto � chamado na url 
		- Content directory: WebContent: arquivos html, etc
		- Generate web.xml deployment descriptor
- Associar o projeto com o servidor
- Criar o arquivo bem-vindo.html dentro da pasta WebContent 
- Acessar o endere�o: localhost:8080/gerenciador/bem-vindo.html

### Servlet
- Protocolo http: navegador (requisi�ao) e servidor (resposta)
- objeto chamado via HTTP, executado para gerar uma resposta HTTP din�mica
- implementa��o: ver OiMundoServlet.java
	- extender usando HttpServlet
	- sobrescrever o m�todo q atende uma requisi��o HTTP: service()
	- o void esta correto pois toda resposta n�o ser� por meio do retorno do m�todo
	- automaticamente recebemos as refer�ncias que apontam p/ os objetos que representam requisi��o e resposta: (HttpServletRequest req, HttpServletResponse resp)
	- apelido no endere�o URL que mapeia (remete) ao Servlet `@WebServlet(urlPattern="/oi")`
	- devolve a resposta via fluxo bin�rio (PDF, imagem) ou getWriter() p/ devolver HTML
	- getWriter() devolve um PrintWriter (java.io) c/ exce��o checked
	- `out.println("<html>")` devolve conte�do textual p/ o navegador

### Sequencia de implementa��es
- bem-vindo.html
- OiMundoServlet.java

