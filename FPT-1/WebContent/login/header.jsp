<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>FIT</title>
<link href="homePageStyles.css" rel="stylesheet" type="text/css">
</head>
<body>
<div id="header">
<div style="text-align: center; font-size:50px;color:white">FIT Physical Therapy</div>
<div style="position: fixed; right: 10px; top:10px; color:white;">Hello, <%=session.getAttribute("name")%></div>
<div style="position: fixed; right: 10px; top:30px; color:white;"><a href="logout.jsp">Logout</a></div>
</div>

</body>
</html>