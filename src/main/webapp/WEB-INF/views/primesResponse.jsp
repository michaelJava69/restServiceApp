
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

 
<html>
<head>
    <%@ page isELIgnored="false" %>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Prime Numbers</title>
</head>
<body>
    <table border="1">
        <tr>
        <td>Intial Number</td>
        <td>Prime Numbers</td>
        
        </tr>
        <tr>
            <td>${primesResponse.initial}</td>
             
            <td>
                <c:forEach var="item" items="${primesResponse.primes}">
                    <c:out value="${item}"/>&nbsp; 
                </c:forEach>
            </td>
        </tr>
    </table>
</body>
</html>