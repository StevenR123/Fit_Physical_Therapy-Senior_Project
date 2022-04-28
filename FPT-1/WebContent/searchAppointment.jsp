<!-- Steven Reninga -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="calendar.bean.CalendarBean"%>
<%@page import="calendar.database.CalendarDao"%>

<%
	String sessionRole = "";
	try {
		sessionRole = session.getAttribute("role").toString();
	} catch (NullPointerException e){}
	
	if(sessionRole.isEmpty() || sessionRole.equals("therapist")){
		response.sendRedirect("./login.jsp");
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Find Appointment</title>
<link href="findSchedule.css" rel="stylesheet">
<style></style>
</head>

<script>
function checkField() {
	 if(document.getElementById('date').value == ""){
		alert("Please Enter Date");
		return false;
	}else {
		//alert("Here's Your Appointments");
		return true;
	}
	
}
</script>

<body>
	<div><jsp:include page="login/header.jsp"></jsp:include></div>
	<div><jsp:include page="login/menu.jsp"></jsp:include></div>
	<div align ="center">
		<br><br><br><br><br>
		<form action ="search" method="get" onsubmit="return checkField()">
			<select name="searchTreatment">
			<%
				CalendarBean calendarBean = new CalendarBean();
				CalendarDao calendarDao = new CalendarDao();
				ResultSet rsTreatment = calendarDao.getTreatmentNames(calendarBean);
				
				while (rsTreatment.next()) {
					out.print("<option value='" + rsTreatment.getString(4) + "' >"+ rsTreatment.getString(4) +"</option>");
				}
			%>
			</select>
			<input type="date" id="date" name="date" value="<%out.print(java.time.LocalDate.now().toString());%>">
			<input type="submit" value="Submit">
		</form>
		
				<%
				ResultSet rs1 = (ResultSet) request.getAttribute("rs1");
				String treatment = (String) request.getAttribute("searchTreatment");
				String date = (String) request.getAttribute("date");
				
				//Keeps track of the number of open appointments
				int counter = 0;
				
		   		String previousHour = null;
		   		
				if (rs1 != null){
					//Only prints the table and title after the search.
					out.print("<h1>"+ treatment +" on "+ date +"</h1>" +
					"<table>");
					
					try {
						while (rs1.next()) {
							int hourInt = Integer.parseInt(rs1.getString(3).split(" ")[1].split(":")[0]);
							String hourstr = null;
							
							//Splits the time from appointmentDate and converts to 12 hour clock
							if (hourInt < 12) {
								hourstr = hourInt + " AM";
							} else if (hourInt == 12) {
								hourstr = hourInt + " PM";
							} else {
								hourstr = hourInt - 12 + " PM";
							}
							
							//Prints a new table header row for each hour
							if (!hourstr.equals(previousHour) && rs1.getString(4) == null){
								out.print(
										"<tr>" +
									        "<th>Therapist</th>" +
									        "<th>Hour</th>" +
									        "<th>Patient Name</th>" +
									        "<th>Notes</th>" +
									        "<th>Add</th>" +
								     	"</tr>"
										);
							}
							
							//Prints row for each appointment
							if (rs1.getString(4) == null) {
								out.print(
									"<tr> <form action ='appointment' method='post'>" + 		
									"	 <input type='hidden' name='date' value='" + date + "'>" +
									"	 <input type='hidden' name='treatmentName' value='" + treatment + "'>" +
									"	 <input type='hidden' name='appointmentId' value='" + rs1.getString(1) + "'>" +								
									"    <td>" + rs1.getString(7) + "</td>" + 
									"    <td>" + hourstr + "</td>" +
									"    <td><input type='text' name='customerName' value=''></td>" + 
									"    <td><input type='text' name='appointmentNotes' value=''></td>" + 
									"    <td><input type='submit' value='Add'></td>" +
									"</form></tr>");
								
								//Used to determine when a new table header row needs to be inderted
								previousHour = hourstr;
								counter++;
							}
						}
						
						if (counter < 1){
							out.print("<h1>No Open Appointments</h1>");
						}else{
							out.print("</table>");
						}						
		
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				%>
		<!-- </table>-->	
	</div>

</body>
</html>
