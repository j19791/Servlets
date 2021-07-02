<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 


<!-- Nao quero html dentro do código Java. com jsp posso programar código java dentro de uma página html -->
<!-- nao precisa recarregar o servidor -->

<%
//scriplet
	//String nomeEmpresa = "Alura"; fixo
	
	String nomeEmpresa = (String) request.getAttribute("empresa"); //por padrao devolve um objeto mas fazemos cast p/ especificar melhor

	System.out.println(nomeEmpresa);

%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

Empresa <% out.println(nomeEmpresa); %>  cadastrada c/ sucesso
Empresa <%= nomeEmpresa %>  cadastrada c/ sucesso <!-- atalho p/ imprimir na tela do navegador -->

<!-- expression language -->
Empresa ${3+3} <!-- linguagem q interpreta e  imprime na tela essa expressao -->

<c:if test="${not empty empresa }">Empresa ${ empresa } cadastrada c/ sucesso</c:if>

<c:if test="${empty empresa }">Nenhuma Empresa cadastrada</c:if>




</body>
</html>