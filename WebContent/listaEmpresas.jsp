<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="java.util.List, br.com.alura.gerenciador.servlet.Empresa" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:url value="/removeEmpresa" var="linkServletRemoveEmpresa"/>
<c:url value="/mostraEmpresa" var="linkServletMostraEmpresa"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Lista Empresas</title>
</head>
<body>

<ul>

<!-- usando jstl -->
	<c:forEach items="${empresas}" var="empresa">
	
		<li>${ empresa.getNome()}   </li>
		
		<!-- jstl mais simplicado mas é a mesma coisa de cima -->		
		<li>${ empresa.nome} <fmt:formatDate value="${empresa.dataAbertura }" pattern="dd/MM/yyyy" /> 
		<a href="${linkServletRemoveEmpresa}?id=${empresa.id}">remove</a>
		<a href="${linkServletMostraEmpresa}?id=${empresa.id}">edita</a>
		
		 </li>
	</c:forEach>



</ul>





<ul>
	
	<%
			List<br.com.alura.gerenciador.servlet.Empresa> lista = (List<br.com.alura.gerenciador.servlet.Empresa>) request.getAttribute("empresas");	
			
			
			for (br.com.alura.gerenciador.servlet.Empresa empresa : lista) {
		%>
		<li><%= empresa.getNome() %>  </li>
		
	<% } %> 
		
		
		
</ul>


</body>
</html>