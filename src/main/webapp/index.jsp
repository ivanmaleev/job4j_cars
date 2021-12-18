<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <title>Объявления по продаже автомобилей</title>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="jsscripts/findads.js"></script>
    <script>
        $(document).ready(function () {
            findAds();
        });
    </script>
</head>
<body>
<div class="container">
    <div class="row">
        <ul class="nav">
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/ad/edit.jsp">Подать новое объявление</a>
            </li>
            <c:if test="${user != null}">
                <li class="nav-item">
                    <a class="nav-link" href="<%=request.getContextPath()%>/ad/userads.jsp">Мои объявления</a>
                </li>
            </c:if>
            <c:if test="${user != null}">
                <li class="nav-item">
                    <a class="nav-link" href="<%=request.getContextPath()%>/login.jsp"><c:out value="${user.name}"/> |
                        Выйти</a>
                </li>
            </c:if>
            <c:if test="${user == null}">
                <li class="nav-item">
                    <a class="nav-link" href="<%=request.getContextPath()%>/login.jsp">Войти</a>
                </li>
            </c:if>
        </ul>
    </div>
    <div>
        <p>Показывать объявления:</p>
        <label for="unsold"> Непроданные</label>
        <input type="radio" id="unsold" name="viewtype" checked="checked" onchange="findAds()">
        <label for="lastday"> За последний день</label>
        <input type="radio" id="lastday" name="viewtype" onchange="findAds()">
        <label for="withphoto"> С фото</label>
        <input type="radio" id="withphoto" name="viewtype" onchange="findAds()">
    </div>
    <div class="row pt-3">
        <table class="table" id="adstable">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Брэнд</th>
                <th scope="col">Тип кузова</th>
                <th scope="col">Фото</th>
                <th scope="col">Описание</th>
                <th scope="col">Дата создания</th>
            </tr>
            </thead>
            <tbody id="adsEl">
            </tbody>
        </table>
    </div>
</div>
</body>
</html>