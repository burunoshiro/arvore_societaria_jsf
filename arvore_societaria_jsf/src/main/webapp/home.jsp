<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home - Árvore Societária</title>
</head>
<body>

	<h1>Teste de página JSP utilizando servlets</h1>
	
	<div>
		<%
		
			String mensagem = (String)request.getAttribute("mensagem");
			if (mensagem != null && !mensagem.isEmpty()) {%>
			
				<%=request.getAttribute("mensagem")%>
			
		<% } %>
	</div>
	
	<form action="teste">
	
		<button name="btn_1" type="submit" value="mensagem_1">Mensagem 1</button>
		<button name="btn_1" type="submit" value="mensagem_2">Mensagem 2</button>
	
	</form>
	
	
	
</body>
</html>