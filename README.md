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
- objeto chamado via requisi��o HTTP, executado para gerar uma resposta HTTP din�mica
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

### Expression Language
- scriplets dentro do HTML n�o � considerado uma boa pr�tica
- ${ } define express�es
- ${ 3 + 3 } interpretado e no navegador ser� impresso 6
- no formul�rio ser� enviada uma requisi��o, o servlet ler� o par�metro e inserir� o atributo na requisi��o
- a expression language ir� ler o atributo inserido na requisi��o pela servlet
```java
<html><body>Empresa ${ empresa } cadastrada com sucesso!</body></html>
```

### taglib core e fmt
- JSP � uma p�gina HTML com JSTL e expression language
- core: controle de fluxo `<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c"%>`
	- condi��o:
	```
	<c:if test= "${not empty empresa}">
		Empresa ${ empresa } cadastrada com sucesso!
	</c:if>
	<c:if test= "${empty empresa}">
		Nenhuma empresa cadastrada!
	</c:if>
	```
- fmt: formata��o de datas e n�meros/ i18n (internacionaliza��o) `<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt"%>`
	- <fmt:formatDate value="${empresa.dataAbertura }" pattern="dd/MM/yy">
	- getParameter sempre devolve String. Trasformar em Date (parsing)
	```java
	String paramDataEmpresa = request.getParameter("data");
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	Date dataAbertura = sdf.parse(paramDataEmpresa); //joga uma exce��o: usar catch and rethrow
	empresa.setDataAbertura(dataAbertura);
	```
- Context Root: conte�do que inserimos na raiz, isto �, tudo aquilo depois da IP. N�o � o projeto mas o contexto
	- escrever o nome do contexto no arquivo JSP n�o � uma boa pr�tica: `<form action="/gerenciador/novaEmpresa" method="post">`
	```java
	<c:url value="/novaEmpresa" var="linkServletNovaEmpresa"/>
	<form action="${linkServletNovaEmpresa}" method="post">	
	```

### Dispatcher de servlet p/ servlet (redirecionamento Server Side)
- ser redirecionado p/ a lista de empresas ao cadastrar empresa
- ao enviarmos a requisi��o e cairnos no Servlet, iremos chamar outro Servlet, e n�o o arquivo JSP.
-  p/ corrigir o erro  HTTP Status 405 - Method Not Allowed. O formul�rio usa post e o ListaEmpresasServlet usa doGet. Alterar p/ service
- m� pr�tica: os Servlets foram chamados, o arquivo JSP e tudo isso em uma mesma requisi��o (risco de cadastro de dados id�nticos)
```
//NovaEmpresaServlet
RequestDispatcher rd = request.getRequestDispatcher("/listaEmpresas");

//listaEmpresasServlet
RequestDispatcher rd = request.getRequestDispatcher("/listaEmpresas.jsp");

//ListaEmpresasServlet
protected void service //mudar de doGet p/ service
```

### Redirecionamento pelo navegador (Client Side)
- quem realiza o redirecionamento n�o � o Servlet dentro do servidor, mas o navegador que enviar� uma nova requisi��o a partir da resposta
- devolver uma resposta c/ um novo endere�o para o navegador, de forma que este envie uma requisi��o. n�o mais utilizar o despachador
```java
request.setAttribute("empresa", empresa.getNome());
response.sendRedirect(listaEmpresas);
```

### Removendo 
- navegador precisa enviar como par�metro o id da empresa a ser removida
- `<a href="/gerenciador/removeEmpresa?id=${empresa.id}">remove</a>`
- pegar no Servlet o id e direcionar p/ lista
```
String paramId = request getParameter("id");
Integer id = Integer.valueof(paramId);
Banco banco = new Banco();
banco.removeEmpresa(id);
response.sendRedirect("listaEmpresas");
```

### Editando
- carregar os dados da empresa antes de serem editados
- `<a href="/gerenciador/mostraEmpresa?id=${empresa.id}">editar</a>`
```java
//MostraEmpresaServlet.java
	String paramId = request.getParameter("id");
	Integer id = Integer.valueOf(paramId);
	Banco banco = new Banco();
	Empresa empresa = banco.buscaEmpresaPelaId(id);
	request.setAttribute("empresa", empresa);
	RequestDispatcher rd = request.getRequestDispatcher("/formAlteraEmpresa.jsp");
	rd.foward(request, response);

//Banco
public Empresa buscaEmpresaPelaId(Integer id) {
	for (Empresa empresa: lista) {
		if(empresa.getId() == id){
			return empresa;
		}
	}
return null;
}

//formAlteraEmpresa.jsp
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="fmt" %>
Nome: <input type="text" name="nome" value="${empresa.nome}" />
Data Abertura: <input type="text" name="data" value="<fmt:formatDate value="${empresa.dataAbertudataAbertura}" pattern="dd/MM/yyyy"/>
<input type="hidden" name="id" value="${empresa.id }">

//AlteraEmpresaServlet
//doPost dados ser�o enviados diretamente no corpo da requisi��o e n�o por meio dos par�metros.

String nomeEmpresa = request.getParameter("nome");
String paramDataEmpresa = request.getParameter("data");

Date dataAbertura = null;
try {
SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
dataAbertura = sdf.parse(paramDataEmpresa);
} catch (ParseException e) {
throw new ServletException(e);
}

Banco banco = new Banco();
Empresa empresa = banco.buscaEmpresaPelaId(id);
empresa.setNome(nomeEmpresa);
empresa.setDataAbertura(dataAbertura);

response.sendRedirect("listaEmpresas");
```

### web.xml
- dentro de WebContent/WEB-INF
```xml
<welcome-file-list>
<welcome-file>bem-vindo.html</welcome-file>
</welcome-file-list>

```

### Invers�o de controle - IOC Inversion Of Control.
- n�o tempos main() no projeto
- TomCat cria o main() (servlet container)
- middleware: Tomcat realiza o papel intermedi�rio entre o navegador e objeto 
- O Tomcat s� criar� o objeto quando ele for completamente necess�rio.
- apesar das diversas requisi��es, o Tomcat criA apenas uma inst�ncia do Servlet e evocou uma �nica vez o construtor. O objeto sempre FIca em mem�ria e esse objeto � reaproveitado nas pr�ximas requisi��es.
- Servlet � chamado de singleton, um escopo, que sobrevive no projeto por tempo indeterminado enquanto o Tomcat estiver no ar, sem nunca recri�-lo
- O escopo � aquilo que determina quanto tempo vive um objeto,

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
- novaEmpresaCriada.jsp
- formNovaEmpresa.jsp
- RemoveEmpresaServlet.java
- MostraEmpresaServlet.java
- formAlteraEmpresa.jsp
- AlteraEmpresaServlet.java
