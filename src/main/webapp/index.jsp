<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Crawler</title>
</head>
<body>
	Welcome
	<form action="CrawlerServlet">
		URLS: <input type="text" name="url"
			value="http://www.ics.uci.edu/~lopes/" /> <br /> <input
			type="submit" value="gotoindex"> <br />URLs:<br />
	</form>
	<c:set var="urls" value="${indexURLs }" />
	<c:set var="toplevelPage" value="${topLevelPage}" />
	<c:set var="totalPage" value="${totalPage }" />
	<c:set var="externalURLs" value="${externalURLs }" />
	<br />
	
	<c:if test="${not empty urls}">
	<p>Number of links at top level Hierarchy--${toplevelPage}</p>
	<p>Total Number of links in Hierarchy--${totalPage }</p>
		<c:forEach var="url" items="${urls}">
			<p>
				<c:out value="${url}"></c:out>
			</p>
		</c:forEach>
	</c:if>
	<c:if test="${not empty externalURLs}">
		<p><b>Total Number of external pages-${fn:length(externalURLs) }</b></p>

		<c:forEach var="url" items="${externalURLs}">
			<p>
				<c:out value="${url}"></c:out>
			</p>
		</c:forEach>
	</c:if>
</body>
</html>