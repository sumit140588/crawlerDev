<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
hi
<form action="CrawlerServlet">
<input type="text" name="url" value=""/>
<input type="submit" value="gotoindex">

Hi <%
String k=(String)request.getAttribute("a");

%>
<%= k %>
</form>
</body>
</html>