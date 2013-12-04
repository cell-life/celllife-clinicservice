<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"  %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Clinic Service</title>

    <c:set var="url">${pageContext.request.requestURL}</c:set>
    <base href="${fn:substring(url, 0, fn:length(url) - fn:length(pageContext.request.requestURI))}${pageContext.request.contextPath}/" />

    <link href="resources/css/bootstrap-3.0.0.min.css" rel="stylesheet" media="screen">
    <link href="resources/css/bootstrap-responsive.min.css" rel="stylesheet">
</head>
<body>

<div class="container">

    <div class="masthead">
        <ul class="nav nav-pills pull-right">
            <li class="active"><a href="http://www.cell-life.org/">Home</a></li>
            <li><a href="j_spring_cas_security_logout">Logout</a>
        </ul>
         <h2><img src="resources/img/logo.png"></h2>
        <h3 class="muted">Clinic Service</h3>
    </div>

    <hr>

    <h1>Cell-Life Clinic (REST) Service</h1>

    <p>
    <ul>
        <li>Nearest clinic locator: <a href="service/locateNearestClinic?longitude=-33.933782&latitude=18.417606">Example for 123 Hope Street</a></li>
    </ul>
    </p>

    <hr>

    <div class="footer">
        <p>&copy; Cell-Life (NPO) - 2013</p>
    </div>

</div>

</body>
</html>
