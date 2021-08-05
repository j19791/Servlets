<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="java.util.List, br.com.alura.gerenciador.modelo.Empresa,  br.com.alura.gerenciador.modelo.Usuario" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:url value="/entrada?acao=RemoveEmpresa" var="linkServletRemoveEmpresa"/>
<c:url value="/entrada?acao=MostraEmpresa" var="linkServletMostraEmpresa"/>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Lista Empresas</title>
</head>
<body>

	<c:import url="logout-parcial.jsp" /> <!-- o logout deverá estar em todos os jsp. P/ evitar o copiar e colar, utilizar o import  -->

	<!-- o código abaixo esta em logout-parcial
	<a href="entrada?acao=Logout"">Logout</a>
	<br>
	<br>
	 -->
	
	Usuario logado: ${usuarioLogado.login } <!-- vai encontrar o usuario na session -->
	<br>
	<br>

<ul>

<!-- usando jstl -->
	<c:forEach items="${empresas}" var="empresa">
	
		<li>${ empresa.getNome()}   </li>
		
		<!-- jstl mais simplicado mas é a mesma coisa de cima -->		
		<li>${ empresa.nome} <fmt:formatDate value="${empresa.dataAbertura }" pattern="dd/MM/yyyy" /> 
		<a href="${linkServletRemoveEmpresa}&id=${empresa.id}">remove</a>
		<a href="${linkServletMostraEmpresa}&id=${empresa.id}">edita</a>
		
		 </li>
	</c:forEach>



</ul>





<ul>
	
	<%
			List<Empresa> lista = (List<Empresa>) request.getAttribute("empresas");	
			
			
			for (Empresa empresa : lista) {
		%>
		<li><%= empresa.getNome() %>  </li>
		
	<% } %> 
		
		
		
</ul>


</body>
</html>