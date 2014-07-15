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

    <ul>
        <li>Nearest clinic locator: <a href="service/locateNearestClinic?longitude=-33.933782&latitude=18.417606">Example for 123 Hope Street</a></li>
        <li>Nearest clinic locator, specifying groups: <a href="service/locateNearestClinicWithGroups?longitude=-33.933782&latitude=18.417606&includeGroups=Clinic%2CCommunity%20Day%20Centre%2CCommunity%20Health%20Centre%2CDistrict%20Hospital%2CSatellite%20Clinic&excludeGroups=For-profit%20Facility">Example for 123 Hope Street</a></li>
        <li>Find clinic by code: <a href="service/searchClinic?code=0000">Example for Demo clinic</a></li>
        <li>Find clinic by name: <a href="service/searchClinicByName?name=demo">Example for Demo clinic</a></li>
    </ul>

    <hr>

    <div class="footer">
        <p>&copy; Cell-Life (NPO) - 2013</p>
    </div>

</div>

</body>
</html>
