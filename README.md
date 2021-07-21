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
- objeto chamado via requisição HTTP, executado para gerar uma resposta HTTP dinâmica
- implementação: ver OiMundoServlet.java
	- extender usando HttpServlet
	- sobrescrever o método q atende uma requisição HTTP: service()
	- o void esta correto pois toda resposta não será por meio do retorno do método
	- automaticamente recebemos as referências que apontam p/ os objetos que representam requisição e resposta: (HttpServletRequest req, HttpServletResponse resp)
	- apelido no endereço URL que mapeia (remete) ao Servlet `@WebServlet(urlPattern="/oi")`
	- devolve a resposta via fluxo binário (PDF, imagem) ou getWriter() p/ devolver HTML
	- getWriter() devolve um PrintWriter (java.io) c/ exceção checked
	- `out.println("<html>")` devolve conteúdo textual p/ o navegador
	
	
### Passando parâmetros p/ o Servlet
- New > Servlet (ver NovaEmpresaServlet.java)
	- URL Mappings: /novaEmpresa
- http://localhost:8080/gerenciador/novaEmpresa?nome=Alura (parâmetro visível na url)
- ler o novo parâmetro enviado a partir da requisição `request.getParameter("nome")` que devolve uma String
- método GET (padrão) da requisição : enviar informações e receber resultados
- método POST: enviar informações p/ o servidor alterando os dados
- formulário html `<form action="/gerenciador/novaEmpresa" method="post">` ver arquivo formNovaEmpresa.html
- c/ POST o parâmetro não será exibido na URL
- servidor não deverá permitir requisição c/ GET (ex: login c/ senha)
	- usar `doPost()` no lugar de `service()`
	- caso o usuário tente utilizar parametros na url que mapeia p/ NovaEmpresaServlet.java, será retornado o status 405 - método da requisição não é permitido pelo servidor.
	
### Modelo
- Empresa.java
- no servlet NovaEmpresaServlet, passar o parâmetro p/ um novo objeto Empresa `Empresa empresa = new Empresa();empresa.setNome(nomeEmpresa);` 
- Banco.java: simular um acesso ao BD (na verdade, uma lista estática): `Banco banco = new Banco();banco.adiciona(empresa);`

### Listando Empresas
- criar servlet ListaEspresasServlet, mapear c/ /listaEmpresas e usar `doGet()`
- coletaremos a List de Empresa do Banco `Banco banco = new banco();List<Empresa> lista = banco.getEmpresas();`
- realizar um laço da List coletada e incluir na resposta  
```html
out.println("<ul>");
for (Empresa empresa : lista) {
out.println("<li>" + empresa.getNome() + "</li>");
}
out.println("</ul>");
```

### JSP
- Não é uma boa prática possuir código de interface e visualização HTML dentro de uma classe.
- usar novaEmpresaCriada.jsp
- JSP: transformará a página HTML em algo dinâmico, que permite algumas ações de programação
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
- Quando alteramos um arquivo JSP não precisamos reiniciar o servidor
- usar `<%= (nomeEmpresa); %>` para simplificar

### Despachando a Requisição do Servlet p/ uma página JSP
- criar uma conexão entre o Servlet e o JSP, para o JSP enviar a resposta ao navegador
- separação de responsabilidades
```java
RequestDispatcher rd = request.getRequestDispatcher("/novaEmpresaCriada");
request.setAttribute("empresa", empresa.getNome()); //empresa é o apelido q será usado no JSP
rd.forward(request, response);
```
- no  JSP:  `String nomeEmpresa = (String)request.getAttribute("empresa");`
- encoding de como interpretar os caracteres `<% page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>`
- pode ser enviado aoplado uma lista para uma JSP `request.setAttribute("empresas", lista);`
- importar no JSP `<% page import=page import="java.util.List, br.com.alura.gerenciador.servlet.Empresa"%>`
- criando um laço no JSP
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
- scriplets dentro do HTML não é considerado uma boa prática
- ${ } define expressões
- ${ 3 + 3 } interpretado e no navegador será impresso 6
- no formulário será enviada uma requisição, o servlet lerá o parâmetro e inserirá o atributo na requisição
- a expression language irá ler o atributo inserido na requisição pela servlet
```java
<html><body>Empresa ${ empresa } cadastrada com sucesso!</body></html>
```

### taglib core e fmt
- JSP é uma página HTML com JSTL e expression language
- core: controle de fluxo `<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c"%>`
	- condição:
	```
	<c:if test= "${not empty empresa}">
		Empresa ${ empresa } cadastrada com sucesso!
	</c:if>
	<c:if test= "${empty empresa}">
		Nenhuma empresa cadastrada!
	</c:if>
	```
- fmt: formatação de datas e números/ i18n (internacionalização) `<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt"%>`
	- <fmt:formatDate value="${empresa.dataAbertura }" pattern="dd/MM/yy">
	- getParameter sempre devolve String. Trasformar em Date (parsing)
	```java
	String paramDataEmpresa = request.getParameter("data");
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	Date dataAbertura = sdf.parse(paramDataEmpresa); //joga uma exceção: usar catch and rethrow
	empresa.setDataAbertura(dataAbertura);
	```
- Context Root: conteúdo que inserimos na raiz, isto é, tudo aquilo depois da IP. Não é o projeto mas o contexto
	- escrever o nome do contexto no arquivo JSP não é uma boa prática: `<form action="/gerenciador/novaEmpresa" method="post">`
	```java
	<c:url value="/novaEmpresa" var="linkServletNovaEmpresa"/>
	<form action="${linkServletNovaEmpresa}" method="post">	
	```

### Dispatcher de servlet p/ servlet (redirecionamento Server Side)
- ser redirecionado p/ a lista de empresas ao cadastrar empresa
- ao enviarmos a requisição e cairnos no Servlet, iremos chamar outro Servlet, e não o arquivo JSP.
-  p/ corrigir o erro  HTTP Status 405 - Method Not Allowed. O formulário usa post e o ListaEmpresasServlet usa doGet. Alterar p/ service
- má prática: os Servlets foram chamados, o arquivo JSP e tudo isso em uma mesma requisição (risco de cadastro de dados idênticos)
```
//NovaEmpresaServlet
RequestDispatcher rd = request.getRequestDispatcher("/listaEmpresas");

//listaEmpresasServlet
RequestDispatcher rd = request.getRequestDispatcher("/listaEmpresas.jsp");

//ListaEmpresasServlet
protected void service //mudar de doGet p/ service
```

### Redirecionamento pelo navegador (Client Side)
- quem realiza o redirecionamento não é o Servlet dentro do servidor, mas o navegador que enviará uma nova requisição a partir da resposta
- devolver uma resposta c/ um novo endereço para o navegador, de forma que este envie uma requisição. não mais utilizar o despachador
```java
request.setAttribute("empresa", empresa.getNome());
response.sendRedirect(listaEmpresas);
```

### Removendo 
- navegador precisa enviar como parâmetro o id da empresa a ser removida
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
//doPost dados serão enviados diretamente no corpo da requisição e não por meio dos parâmetros.

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

### Inversão de controle - IOC Inversion Of Control.
- não tempos main() no projeto
- TomCat cria o main() (servlet container)
- middleware: Tomcat realiza o papel intermediário entre o navegador e objeto 
- O Tomcat só criará o objeto quando ele for completamente necessário.
- apesar das diversas requisições, o Tomcat criA apenas uma instância do Servlet e evocou uma única vez o construtor. O objeto sempre FIca em memória e esse objeto é reaproveitado nas próximas requisições.
- Servlet é chamado de singleton, um escopo, que sobrevive no projeto por tempo indeterminado enquanto o Tomcat estiver no ar, sem nunca recriá-lo
- O escopo é aquilo que determina quanto tempo vive um objeto,

### Sequencia de implementações
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
