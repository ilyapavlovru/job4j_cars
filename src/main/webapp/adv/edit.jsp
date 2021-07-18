<%@ page import="ru.job4j.cars.model.User" %>
<%@ page import="ru.job4j.cars.model.Adv" %>
<%@ page import="ru.job4j.cars.store.AdRepository" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link type="text/css" rel="stylesheet" href="../css/styles.css"/>


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
        </ul>
    </div>
    <hr align="left" size="5">
</div>

<%
    String id = request.getParameter("id");
    Adv adv = new Adv(0, "");
    int carBrandId = 0;
    int carBodyTypeId = 0;
    String carDescription = "";
    if (id != null) {
        adv = AdRepository.instOf().findAdvById(Integer.parseInt(id));
        carBrandId = adv.getCarBrand().getId();
        carBodyTypeId = adv.getCarBodyType().getId();
        carDescription = adv.getDescription();
    }
%>


<script>

    $(document).ready(function () {

        loadCarBrandsFromDB();
        loadCarBodyTypesFromDB();

        $('#descriptionTextArea').val('<%=carDescription%>');

    });

    function loadCarBrandsFromDB() {
        const el = document.getElementById('carBrandSelector');
        let curCarBrandId = <%=carBrandId%>;
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/cars/carbrands',
            dataType: 'json'
        }).done(function (response) {
            response.forEach(function (arrayItem) {
                el.append(new Option(arrayItem.name, arrayItem.id));
                console.log(arrayItem.id + " " + arrayItem.name);
            });
            el.value = curCarBrandId;
        }).fail(function (err) {
            alert(err);
        });
    }

    function loadCarBodyTypesFromDB() {
        const el = document.getElementById('carBodyTypeSelector');
        let curCarBodyTypeId = <%=carBodyTypeId%>;
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/cars/carbodytypes',
            dataType: 'json'
        }).done(function (response) {
            response.forEach(function (arrayItem) {
                el.append(new Option(arrayItem.name, arrayItem.id));
                console.log(arrayItem.id + " " + arrayItem.name);
            });
            el.value = curCarBodyTypeId;
        }).fail(function (err) {
            alert(err);
        });
    }

</script>

<div class="container pt-3">
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                <% if (id == null) { %>
                Новое объявление
                <% } else { %>
                Редактирование объявления
                <% } %>
            </div>

            <div class="card-body">

                <form action="<%=request.getContextPath()%>/adv.do?id=<%=adv.getId()%>" method="post">

                    <div class="form-group">
                        <label>Заголовок объявления:</label>
                        <input type="text" class="form-control" id="name" name="name" value="<%=adv.getName()%>">
                    </div>

                    <div class="form-group">
                        <label for="carBrandSelector">Марка:</label>
                        <select class="form-control" id="carBrandSelector" name = "carBrandId">
                            <option value="0">Выберите марку автомобиля</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="carBodyTypeSelector">Тип кузова:</label>
                        <select class="form-control" id="carBodyTypeSelector" name = "carBodyTypeId">
                            <option value="0">Выберите тип кузова автомобиля</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label>Цена:</label>
                        <input type="text" class="form-control" id="price" name="price" value="<%=adv.getPrice()%>">
                    </div>

                    <div class="form-group">
                        <label for="descriptionTextArea">Описание:</label>
                        <textarea class="form-control" id="descriptionTextArea" name = "description" rows="3"></textarea>
                    </div>

                    <div class="form-group">
                        <% if (id != null) { %>
                        <label for="advStatusSelector">Статус объявления:</label>
                        <select class="form-control" id="advStatusSelector" name = "advStatusSelectorId">
                            <option value="0">Выберите статус объявления</option>
                            <option value="Продается">Продается</option>
                            <option value="Продано">Продано</option>
                        </select>
                        <% } %>
                    </div>

                    <input type="hidden" name="action" value="update"/>
                    <button type="submit" value="UPDATE" class="btn btn-primary" onclick="return validate()">Сохранить</button>
                </form>
            </div>

        </div>
    </div>
</div>

</body>
</html>
