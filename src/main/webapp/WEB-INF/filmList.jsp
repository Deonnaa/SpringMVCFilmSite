<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Film List</title>
<link href="/webapp/styles.css" type="text/css" rel="stylesheet">
</head>
<body>

	<h1>Film List</h1>
    
    <c:forEach var="film" items="${filmList}">
        <h2><a href="searchById.do?filmId=${film.filmId}">${film.title}</a></h2>
        <p>Description: ${film.desc}</p>
    </c:forEach>
	
</body>
</html>