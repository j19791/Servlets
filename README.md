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
