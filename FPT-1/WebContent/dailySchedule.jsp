<!-- Steven Reninga -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.time.LocalDateTime"%>

<%
	String sessionRole = "";
	try {
		sessionRole = session.getAttribute("role").toString();
	} catch (NullPointerException e){}
	
	if(sessionRole.isEmpty()){
		response.sendRedirect("./login.jsp");
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Daily Schedule</title>
<link href="findSchedule.css" rel="stylesheet">
<style></style>
</head>

<script>
function checkRole() {
	 if(<% out.print("'"+session.getAttribute("role")+"'"); %> == "therapist"){
		alert("Appointments may only be removed by Administrators or Registrars");
		return false;
	}else {
		return true;
	}	
}
</script>

<body>
	<div><jsp:include page="login/header.jsp"></jsp:include></div>
	<div><jsp:include page="login/menu.jsp"></jsp:include></div>
	<div align ="center">
		<br><br><br><br><br>
		
		<form action ="dailySchedule" method="get">
			<input type="date" id="date" name="date" value="<%out.print(java.time.LocalDate.now().plusDays(1).toString());%>">
			<input type="submit" value="Submit">
		</form>
				<%
				ResultSet rs = (ResultSet) request.getAttribute("rs");
				
				if (rs != null){
					//Only prints the table and title after the search.
					out.print(
					"<h1>Filled Appointments for "+request.getAttribute("date")+"</h1>" +
					"<table>"+
						"<tr>"+
							"<th>Therapist</th>"+
							"<th>Hour</th>"+
							"<th>Treatment</th>"+
							"<th>Patient Name</th>"+
							"<th>Notes</th>"+
							"<th>Remove</th>"+
						"</tr>");
					
					try {
						while (rs.next()) {
							int hourInt = Integer.parseInt(rs.getString(3).split(" ")[1].split(":")[0]);
							String hourStr = null;
							
							if (hourInt < 12) {
								hourStr = hourInt + " AM";
							} else if (hourInt == 12) {
								hourStr = hourInt + " PM";
							} else {
								hourStr = hourInt - 12 + " PM";
							}
							
							if (rs.getString(4) != null && !rs.getString(5).equals("OFF")) {
								out.print(
										"<tr> <form action ='clear' method='post' onsubmit='return checkRole()'>" + 
										"	 <input type='hidden' name='date' value='" + request.getAttribute("date") + "'>" +
										"	 <input type='hidden' name='appointmentId' value='" + rs.getString(1) + "'>" + 
										"    <td>" + rs.getString(7) + "</td>" + 
										"    <td>" + hourStr + "</td>" +
										"    <td>" + rs.getString(4) + "</td>" + 
										"    <td>" + rs.getString(5) + "</td>" + 
										"    <td>" + rs.getString(6) + "</td>" + 
										"    <td><input type='submit' value='Remove'></td>" +
										"  </form></tr>");	
							}
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					out.print("</table>");
				}
				%>
		
	</div>

</body>
</html>
