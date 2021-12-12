<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="ru.job4j.model.Advertisement" %>
<%@ page import="ru.job4j.store.AdRepostiroty" %>
<%@ page import="ru.job4j.model.Car" %>
<%@ page import="ru.job4j.model.CarBrand" %>
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
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script>
        function validate() {
            return true;
        }
    </script>
    <title>Добавить новое объявление</title>
</head>
<body>
<%
    String id = request.getParameter("id");
    Advertisement advertisement = new Advertisement();
    advertisement.setDescription("");
    Car car = new Car();
    CarBrand carBrand = new CarBrand();
    carBrand.setName("");
    String bodytype = "";
    if (id != null) {
        advertisement = AdRepostiroty.instOf().findAdById(Integer.valueOf(id));
        car = advertisement.getCar();
        carBrand = car.getBrand();
        bodytype = car.getBodytype().toString();
    }
%>
<div class="container pt-3">
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                <% if (id == null) { %>
                Новое объявление.
                <% } else { %>
                Редактирование объявления.
                <% } %>
            </div>
            <div class="card-body">
                <form action="<%=request.getContextPath()%>/ad/userads.do?id=<%=advertisement.getId()%>" method="post">
                    <div class="form-group">
                        <label>Описание</label>
                        <input type="text" class="form-control" name="description" id="description" value="<%=advertisement.getDescription()%>">
                        <label>Бренд</label>
                        <input type="text" class="form-control" name="carbrand" id="carbrand" value="<%=carBrand.getName()%>">
                        <label>Тип кузова</label>
                        <input type="text" class="form-control" name="bodytype" id="bodytype" value="<%=bodytype%>">
                    </div>
                    <button type="submit" class="btn btn-primary" onclick="return validate();">Сохранить</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>