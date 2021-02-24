<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <title>Meal</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>
<div class="jumbotron text-center">
    <h3><a href="index.html">Home</a></h3>
</div>
<hr>
<div class="container" >
    <h2>${title}</h2>
    <c:if test="${isNew == true}">
    <form action="${pageContext.request.contextPath}/meal" method="post">
        <label for="dateTime">Date Time:</label>
        <input type="datetime-local" name="dateTime" id="dateTime"></p>
        <label for="description">Description:</label>
        <textarea name="description" id="description"></textarea></p>
        <label for="calories">Calories:</label>
        <input type="number" name="calories" id="calories"/></p>
        <input type="submit" name="Save" value="Save"/>
        <input type="reset" name="Cancel" value="Cancel"/></p>
    </form>
    </c:if>
    <c:if test="${isNew == false}">
        <form action="${pageContext.request.contextPath}/meal" method="post">
            <label for="dateTime">Date Time:</label>
            <input type="datetime-local" name="dateTime" id="dateTime" value="${meal.dateTime}"></p>
            <label for="description">Description:</label>
            <textarea name="description" id="description" value="${meal.description}"></textarea></p>
            <label for="calories">Calories:</label>
            <input type="number" name="calories" id="calories" value="${meal.calories}"/></p>
            <input type="hidden" id="id" name="id" value="${meal.id}">
            <input type="submit" name="Save" value="Save"/>
            <input type="reset" name="Cancel" value="Cancel"/></p>
        </form>
    </c:if>
</div>
</body>
</html>