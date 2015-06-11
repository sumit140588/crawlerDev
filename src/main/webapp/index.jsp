<%@page import="java.util.List"%>
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
URLS:
<input type="text" name="url" value="http://www.google.com" />
<br/><input type="submit" value="gotoindex">

URLs:<br/> <%

List<String> urls=(List<String>)request.getAttribute("indexURLs");
Long toplevelPage=(Long)request.getAttribute("topLevelPage");


 if(null!=urls){
	 out.println("Number of pages at top level Hierarchy--"+toplevelPage);
	 out.println("Total Number of pages in Hierarchy--"+urls.size());
	 for(String url:urls)
	 out.println(url+"<br/>");
	 out.flush();
	 
	 
 }else{
	 
 }
%>

</form>
</body>
</html>