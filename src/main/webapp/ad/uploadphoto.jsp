<%@ page language="java" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Upload</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container">
    <div class="row">
        <ul class="nav">
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/index.jsp">Главная</a>
            </li>
            <c:if test="${user != null}">
                <li class="nav-item">
                    <a class="nav-link" href="<%=request.getContextPath()%>/ad/userads.jsp">Мои объявления</a>
                </li>
            </c:if>
        </ul>
    </div>
    <table class="table">
        <thead>
        <tr>
            <th>Фото</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${images}" var="image" varStatus="status">
            <tr valign="top">
                <td>
                    <img src="<c:url value='/download?filename=${image}'/>" width="100px" height="100px"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    </table>
    <h2>Upload image</h2>
    <c:forEach items="${ads}" var="ad">
        <form action="<c:url value='/ad/uploadphoto.do?id=${ad.id}'/>" method="post" enctype="multipart/form-data">
            <div class="checkbox">
                <input type="file" name="file">
            </div>
            <button type="submit" class="btn btn-default">Submit</button>
        </form>
    </c:forEach>
</div>

</body>
</html>