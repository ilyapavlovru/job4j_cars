<%@ page import="ru.job4j.cars.model.User" %>
<%@ page import="ru.job4j.cars.model.Adv" %>
<%@ page import="java.util.Collection" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>

    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>

    <title>Сайт по продажам автомобилей</title>
</head>
<body>

<div class="container">
    <div class="row">
        <ul class="nav">
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/adv.do">Объявления</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/adv/edit.jsp">Добавить объявление</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/login.jsp"> <%=((User)request.getAttribute("user")).getName()%> | Выйти</a>
            </li>
        </ul>
    </div>
    <hr align="left" size="5">
</div>

<div class="container">
    <h2>Список объявлений</h2>
    <div class="row">
        <div class="col-12">
            <table class="table table-bordered" id='table'>
                <thead>
                <tr>
                    <th scope="col">Фото</th>
                    <th scope="col"></th>
                    <th scope="col">Название</th>
                    <th scope="col">Описание</th>
                    <th scope="col">Марка</th>
                    <th scope="col">Тип кузова</th>
                    <th scope="col">Цена</th>
                </tr>
                </thead>
                <tbody id="allCarsTable">
                <% for (Adv adv : (Collection<Adv>) request.getAttribute("ads")) { %>
                <tr>
                    <td>
                        <img src="/cars/download?id=<%=adv.getId()%>" width="200px" height="200px"/>
                    </td>
                    <td>

                        <form action='<c:url value="/photoUpload"/>' method="get">
<%--                        <form action="<%=request.getContextPath()%>/photoUpload/" method="get">--%>

                            <input type="hidden" name="action" value="add"/>
                            <input type="hidden" name="id" value="<%=adv.getId()%>"/>
                            <input type="submit" value="Добавить"/>
                        </form>
                    </td>
                    <td>
                        <%=adv.getName()%>
                    </td>
                    <td>
                        <%=adv.getDescription()%>
                    </td>
                    <td>
                        <%=adv.getCarBrand().getName()%>
                    </td>
                    <td>
                        <%=adv.getCarBodyType()%>
                    </td>
                    <td>
                        <%=adv.getPrice()%>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
