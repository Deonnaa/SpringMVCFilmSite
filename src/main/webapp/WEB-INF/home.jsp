<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Film Site</title>
<link href="/webapp/styles.css" type="text/css" rel="stylesheet">
</head>
<body>

	<h1>Welcome to Deonna's Film Site</h1>

	<!-- Search by Film ID -->
	<h2>Search Film by ID</h2>
	<form action="searchById.do" method="get">
		<label for="filmId">Film ID:</label> <input type="number" id="filmId"
			name="filmId" required>
		<button type="submit">Search</button>
	</form>

	<!-- Search by Keyword -->
	<h2>Search Film by Keyword</h2>
	<form action="searchByKeyword.do" method="get">
		<label for="keyword">Keyword:</label> <input type="text" id="keyword"
			name="keyword" required>
		<button type="submit">Search</button>
	</form>

</body>
</html>