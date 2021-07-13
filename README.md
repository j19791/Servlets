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
	
	
### Passando par�metros p/ o Servlet
- New > Servlet (ver NovaEmpresaServlet.java)
	- URL Mappings: /novaEmpresa
- http://localhost:8080/gerenciador/novaEmpresa?nome=Alura (par�metro vis�vel na url)
- ler o novo par�metro enviado a partir da requisi��o `request.getParameter("nome")` que devolve uma String
- m�todo GET (padr�o) da requisi��o : enviar informa��es e receber resultados
- m�todo POST: enviar informa��es p/ o servidor alterando os dados
- formul�rio html `<form action="/gerenciador/novaEmpresa" method="post">` ver arquivo formNovaEmpresa.html
- c/ POST o par�metro n�o ser� exibido na URL
- servidor n�o dever� permitir requisi��o c/ GET (ex: login c/ senha)
	- usar `doPost()` no lugar de `service()`
	- caso o usu�rio tente utilizar parametros na url que mapeia p/ NovaEmpresaServlet.java, ser� retornado o status 405 - m�todo da requisi��o n�o � permitido pelo servidor.
	
### Modelo
- Empresa.java
- no servlet NovaEmpresaServlet, passar o par�metro p/ um novo objeto Empresa `Empresa empresa = new Empresa();empresa.setNome(nomeEmpresa);` 
- Banco.java: simular um acesso ao BD (na verdade, uma lista est�tica): `Banco banco = new Banco();banco.adiciona(empresa);`

### Listando Empresas
- criar servlet ListaEspresasServlet, mapear c/ /listaEmpresas e usar `doGet()`
- coletaremos a List de Empresa do Banco `Banco banco = new banco();List<Empresa> lista = banco.getEmpresas();`
- realizar um la�o da List coletada e incluir na resposta  
```html
out.println("<ul>");
for (Empresa empresa : lista) {
out.println("<li>" + empresa.getNome() + "</li>");
}
out.println("</ul>");
```

### JSP
- N�o � uma boa pr�tica possuir c�digo de interface e visualiza��o HTML dentro de uma classe.
- usar novaEmpresaCriada.jsp
- JSP: transformar� a p�gina HTML em algo din�mico, que permite algumas a��es de programa��o
- scriptlet
```java
<%
String nomeEmpresa = "Alura";
System.out.println(nomeEmpresa);
%>
<html><body>
Empresa " + <% out.println(nomeEmpresa); %> + " cadastrada com sucesso!
</body></html>
```
- Quando alteramos um arquivo JSP n�o precisamos reiniciar o servidor
- usar `<%= (nomeEmpresa); %>` para simplificar

### Despachando a Requisi��o do Servlet p/ uma p�gina JSP
- criar uma conex�o entre o Servlet e o JSP, para o JSP enviar a resposta ao navegador
- separa��o de responsabilidades
```java
RequestDispatcher rd = request.getRequestDispatcher("/novaEmpresaCriada");
request.setAttribute("empresa", empresa.getNome()); //empresa � o apelido q ser� usado no JSP
rd.forward(request, response);
```
- no  JSP:  `String nomeEmpresa = (String)request.getAttribute("empresa");`
- encoding de como interpretar os caracteres `<% page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>`
- pode ser enviado aoplado uma lista para uma JSP `request.setAttribute("empresas", lista);`
- importar no JSP `<% page import=page import="java.util.List, br.com.alura.gerenciador.servlet.Empresa"%>`
- criando um la�o no JSP
```java
<%
List<Empresa> lista = (List<Empresa>)request.getAttribute("empresas");
for (Empresa empresa : lista) {
%>
<li><%= empresa.getNome() %></li>
<%
}
%>

```


### Sequencia de implementa��es
- bem-vindo.html
- OiMundoServlet.java
- NovaEmpresaServlet.java
- formNovaEmpresa.html
- Empresa.java
- Banco.java
- ListaEmpresasServlet.java
- novaEmpresaCriada.jsp
- listaEmpresas.jsp
