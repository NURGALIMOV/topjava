<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <title>Meals</title>
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
    <h2>Meals</h2>
    <h3><a href="${pageContext.request.contextPath}/meal">Add Meal</a></h3>
    <table class="table">
        <thead>
        <tr>
            <th>Дата/Время</th>
            <th>Описание</th>
            <th>Калории</th>
            <th>Обновить</th>
            <th>Удалить</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="meal" items="${meals}">
            <c:choose>
                <c:when test="${meal.excess}">
                    <tr>
                        <td class="text-danger">${meal.dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))}</td>
                        <td class="text-danger"><c:out value="${meal.description}"/></td>
                        <td class="text-danger"><c:out value="${meal.calories}"/></td>
                        <td class="text-danger"><a href="${pageContext.request.contextPath}/meal?id=<c:out value="${meal.id}"/>">Update</a></td>
                        <td class="text-danger"><a href="${pageContext.request.contextPath}/meal?id=<c:out value="${meal.id}"/>">Delete</a></td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td class="text-success">${meal.dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))}</td>
                        <td class="text-success"><c:out value="${meal.description}"/></td>
                        <td class="text-success"><c:out value="${meal.calories}"/></td>
                        <td class="text-danger"><a href="${pageContext.request.contextPath}/meal?id=<c:out value="${meal.id}&action=update"/>">Update</a></td>
                        <td class="text-danger"><a href="${pageContext.request.contextPath}/meal?id=<c:out value="${meal.id}&action=delete"/>">Delete</a></td>
                    </tr>
                </c:otherwise>
            </c:choose>

        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>