<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Film Detail</title>
<link href="/webapp/styles.css" type="text/css" rel="stylesheet">
</head>
<body>

	<h1>Film Detail</h1>

	<c:choose>
		<c:when test="${! empty film}">
			<h2>${film.title}</h2>
			<ul>
				<li>Description: ${film.desc}</li>
				<li>Release Year: ${film.releaseYear}</li>
				<li>Language: ${film.language}</li>
				<li>Rating: ${film.rating}</li>
				<li>Actors:
					<ul>
						<c:forEach var="actor" items="${film.actors}">
							<li>${actor.firstName} ${actor.lastName}</li>
						</c:forEach>
					</ul>
				</li>
				<!-- Placeholder for categories -->
				<li>Categories: ${film.category}</li>
			</ul>
		</c:when>
		<c:otherwise>
			<p>No Film Found
			<p>
		</c:otherwise>
	</c:choose>
</body>
</html>