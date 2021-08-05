<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<!--<c:url value="/alteraEmpresa" var="linkServletAlteraEmpresa"/> utilizando controlador agora-->
<c:url value="/entrada" var="linkServletUnicaEntrada"/>

<!-- contexto: gerenciador. Pode ser dinamico -->
<!-- <form action="/gerenciador/novaEmpresa" method="post" > -->
<!-- /gerenciador ja esta configurado em Properties/Web Project Settings/Context Root-->
<form action="${linkServletUnicaEntrada}" method="post" >
	Nome: <input type="text" name="nome" value="${ empresa.nome }"/>	
	Data Abertura <input type="text" name="data" value="<fmt:formatDate value="${empresa.dataAbertura }" pattern="dd/MM/yyyy" />" />
	<input type="hidden" name="id" value="${empresa.id }">
	<input type="hidden" name="acao" value="AlteraEmpresa">
	<input type="submit">
</form>

</body>
</html>