<!-- Steven Reninga 
WIP Don't worry about this one
-->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="calendar.bean.CalendarBean"%>
<%@page import="calendar.database.CalendarDao"%>
<%@page import="java.util.*"%>

<%
	String sessionRole = "";
	try {
		sessionRole = session.getAttribute("role").toString();
	} catch (NullPointerException e){}
	
	if(sessionRole.isEmpty() || !sessionRole.equals("admin")){
		response.sendRedirect("./login.jsp");
	}
		
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin</title>
<link href="findSchedule.css" rel="stylesheet">
<style></style>
</head>

<script>


</script>

<body>
	<jsp:include page="login/header.jsp"></jsp:include>
	<jsp:include page="login/menu.jsp"></jsp:include>
	<div align ="center" style="padding-left:150px;"> <!-- align ="center" -->
		<br><br><br><br><br>

	<h1>Admin</h1>
		<div >
			<!-- <h2>Upload Default Schedule</h2>
			
			<form  action ="massUpload" method="post">
				<input type='submit' value='Upload for 1 month'>
			</form> -->
						
			<h2>Add Employee</h2>
			
			<%
				String addError = (String) request.getAttribute("addError");
				String firstName =(String) request.getAttribute("firstName");
			
				if (addError == "1"){		
					out.print("<h1 style='color:red;'>Please fill in all fields.</h1>");
				}
			%>
			
			<form action ="addEmp" method="post"><table>
				<tr>
					<th>Name</th><th>Username</th><th>Password</th><th>Role</th><th>Add</th>
				</tr>
				<tr>
					<td><input type='text' name='newFirstName' value=''></td>
					<td><input type='text' name='newUserName' value=''></td>
					<td><input type='text' name='newPassword' value=''></td>
					<td>
						<select name='newRole'>
							<option value='employee'>Therapist</option>
							<option value='registrar'>Registrar</option>
							<option value='admin'>Administrator</option>
						</select>
					</td>
					<td><input type='submit' value='Add'></td>
				</tr>				
			</table></form>
			
			<h2>Modify Employee</h2>
			
			<%
				String modError = (String) request.getAttribute("modError");
				if(modError == "1"){		
					out.print("<h1 style='color:red;'>Set all fields before modifying</h1>");
				}
			%>
			
			<table>
				<tr>
					<th>Name</th><th>Username</th><th>Password</th><th>Role</th><th>Modify</th>
				</tr>
				<%
					CalendarBean calendarBean = new CalendarBean();
			        CalendarDao calendarDao = new CalendarDao();
			        ResultSet rsTherapist = calendarDao.getTherapistNames(calendarBean);
			        
			        String fName = "";
		        	String uName = "";
		        	String password = "";
		        	
			        while(rsTherapist.next()){
			        	fName = rsTherapist.getString(2);
			        	uName = rsTherapist.getString(3);
			        	password = rsTherapist.getString(4);
			        	
			        	out.print("<form action ='modEmp' method='post'><tr>"+
			        	    "	<input type='hidden' id='userId' name='userId' value='"+ rsTherapist.getString(1) +"'>"+
							"	<td><input type='text' id='modFirstName' name='modFirstName' value='"+ fName +"'></td>"+
							"	<td><input type='text' id='modUserName' name='modUserName' value='"+ uName +"'></td>"+
							"	<td><input type='text' id='modPassword' name='modPassword' value='"+ password +"'></td>"+
							"	<td>"+
							"		<select id='modRole' name='modRole'>"+
							"			<option value='none' selected>Select</option>"+
							"			<option value='employee'>Therapist</option>"+
							"			<option value='registrar'>Registrar</option>"+
							"			<option value='admin'>Administrator</option>"+
							"			<option value='remove'>Remove</option>"+
							"		</select>"+
							"	</td>"+
							"	<td><input type='submit' value='Modify'></td>"+
							"</tr></form>	");
			        }
				%>								
			</table>		
		</div>
	</div>
</body>
</html>
