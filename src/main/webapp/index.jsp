<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
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
		URLS: <input type="text" name="url"
			value="http://www.ics.uci.edu/~lopes/" /> <br /> <input
			type="submit" value="gotoindex"> <br />URLs:<br/>
	</form>
	<%
		Set<String> urls = null;
		Object o = request.getAttribute("indexURLs");
		if (o instanceof Set<?>)
			urls = (Set<String>) o;
		Long toplevelPage = (Long) request.getAttribute("topLevelPage");

		if (null != urls) {
			if (null != toplevelPage)
				out.println("Number of links at top level Hierarchy--"
						+ toplevelPage+"<br/>");
			out.println("Total Number of links in Hierarchy--"
					+ urls.size()+"<br/>");
			out.println("Total Number of links including external links --"
					+ (Long)request.getAttribute("totalPage")+"</br/>");
			Iterator<String> iterator=urls.iterator();
			while (iterator.hasNext())
				out.println(iterator.next() + "<br/>");
			out.flush();

		} else {

		}
		out.println("<b>External URLs</b>");
		Set<String> exturls = null;
		Object exto = request.getAttribute("externalURLs");
		if (exto instanceof Set<?>)
			exturls = (Set<String>) exto;
		

		if (null != exturls) {
			
			out.println("Total Number of external pages--"
					+ exturls.size());
			Iterator<String> iterator=exturls.iterator();
			while (iterator.hasNext())
				out.println(iterator.next() + "<br/>");
			out.flush();

		} else {

		}
	%>
</body>
</html>