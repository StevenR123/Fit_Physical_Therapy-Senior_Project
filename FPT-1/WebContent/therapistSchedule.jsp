<!-- Steven Reninga
Rough WIP-->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="calendar.bean.CalendarBean"%>
<%@page import="calendar.database.CalendarDao"%>
<%@page import="java.util.*"%>

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
<title>Therapist's Schedule</title>
</head>

<script>
function checkRole() {
	 if(<% out.print("'"+session.getAttribute("role")+"'"); %> != "admin"){
		alert("Certifications may only be removed or added by Administrators");
		return false;
	}else {
		return true;
	}	
}

function checkSchedule(){
	//Need to think of something
	return true;
}

function checkTherapist(){
	if (document.getElementById('therapstSelection').value == ''){
		return false;
	} else {
		return true;
	}
}
	
</script>

<body>
  <div><jsp:include page="login/header.jsp"></jsp:include></div>
  <div><jsp:include page="login/menu.jsp"></jsp:include></div>
  <div align ="center">
 	 <br><br><br><br><br>
    <h1>Therapist's Schedule</h1>

    <form action ="therapistSchedule" method="get" onsubmit='return checkTherapist()'>
      <lable for = "userId">Name:</lable>
      <select name="userId" id='therapstSelection'>
      	<option value='' disabled selected>Therapist</option>
         <%
	        CalendarBean calendarBean = new CalendarBean();
	        CalendarDao calendarDao = new CalendarDao();
	        ResultSet rsTherapist = calendarDao.getTherapistNames(calendarBean);
	        
	        if(session.getAttribute("role").equals("therapist")){
	        	while (rsTherapist.next()) {
	        		if(rsTherapist.getString(2).equals(session.getAttribute("name"))) {
	        			out.print("<option value=" + rsTherapist.getString(1) + ">"+ rsTherapist.getString(2) +"</option>");
	        		}
	        	}
	    	} else {    		
	            while (rsTherapist.next()) {
	            out.print("<option value=" + rsTherapist.getString(1) + ">"+ rsTherapist.getString(2) +"</option>");
	            }
	    	}
        %>
      </select>
     
      <input type="submit" value="Submit">
    </form>
   	  <%
		ResultSet rs = (ResultSet) request.getAttribute("rs");
   		ResultSet rsDefault = (ResultSet) request.getAttribute("rsDefault");
   		ResultSet rsCerts = (ResultSet) request.getAttribute("rsCerts");
   		ResultSet rsCertList = (ResultSet) request.getAttribute("rsCertList");
   		
   	  	LocalDate currentDate = (LocalDate) request.getAttribute("currentDate");
   	  	
   		String date = null;
   		String previousDate = null;
   		List<String> certList = new ArrayList <String>();
   		List<String> certIdList = new ArrayList <String>();
   		
   		String userId = null;
   		String scheduleId = null;
   		
   		
   		if (rsDefault != null){
   			out.print("<div><div style='display:inline-block;padding-right:30px;width:20%;'>"+
				"<h2>Default Schedule</h2><form action='modDefault' method='post'><table><tr><th>Day</th><th>Start</th><th>End</th></tr>");
			try {
				
				int counter = 1;
				
				while (rsDefault.next()) {	
					int sHour = 0;
					int eHour = 0;
					String sHourStr = "-";
					String eHourStr = "-";
					
					scheduleId = rsDefault.getString(1);
					
					userId = rsDefault.getString(2);
					
					if (rsDefault.getBoolean(4)) {
						sHour = Integer.parseInt(rsDefault.getString(4));
						if (sHour > 12){
							int sHourNew = sHour - 12;
							sHourStr = Integer.toString(sHourNew) + " PM";
						} else if (sHour == 12){
							sHourStr = Integer.toString(sHour) + " PM";
						} else {
							sHourStr = Integer.toString(sHour) + " AM";
						}
					}
					
					if (rsDefault.getBoolean(5)) {
						eHour = Integer.parseInt(rsDefault.getString(5));
						if (eHour > 12){
							int eHourNew = eHour - 12;
							eHourStr = Integer.toString(eHourNew) + " PM";
						} else if (eHour == 12){
							eHourStr = Integer.toString(eHour) + " PM";
						} else {
							eHourStr = Integer.toString(eHour) + " AM";
						}
					}
					
					out.print(
						"<tr>" + 
						"<td>"+rsDefault.getString(3)+"</td>"+						
						"<td> "+
							"<select name='startTime"+counter+"'> "+
							"	<option value='"+sHour+"' selected='selected' hidden>"+sHourStr+"</option>"+
					      	"	<option value='8'>8 AM</option>"+
					      	"	<option value='9'>9 AM</option>"+
					      	"	<option value='10'>10 AM</option>"+
					      	"	<option value='11'>11 AM</option>"+
					      	"	<option value='12'>12 PM</option>"+
					      	"	<option value='13'>1 PM</option>"+
					      	"	<option value='14'>2 PM</option>"+
					      	"	<option value='15'>3 PM</option>"+
					      	"	<option value='16'>4 PM</option>"+
					      	"	<option value='17'>5 PM</option>"+
			      			"	<option value='NULL'>-</option>"+
					      	"</select>"+
						"</td>"+
						"<td> "+
							"<select name='endTime"+counter+"'> "+
							"	<option value='"+eHour+"' selected='selected' hidden>"+eHourStr+"</option>"+
					      	"	<option value='8'>8 AM</option>"+
					      	"	<option value='9'>9 AM</option>"+
					      	"	<option value='10'>10 AM</option>"+
					      	"	<option value='11'>11 AM</option>"+
					      	"	<option value='12'>12 PM</option>"+
					      	"	<option value='13'>1 PM</option>"+
					      	"	<option value='14'>2 PM</option>"+
					      	"	<option value='15'>3 PM</option>"+
					      	"	<option value='16'>4 PM</option>"+
					      	"	<option value='17'>5 PM</option>"+
			      			"	<option value='NULL'>-</option>"+
					      	"</select>"+
						"</td>"+
						"</tr><input type='hidden' name='scheduleId"+counter+"' value='"+scheduleId+"'>");
					counter++;
				}
				
				out.print("<tr><td></td><td></td><td><input type='submit' value='Change'></td></tr>"+
				"</table>"+
				
				"<input type='hidden' name='userId' value='"+userId+"'></form></div>");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
   		}
   		if (rsCerts != null){ 
   			
   			out.print("<div style='display:inline-block;padding-right:30px;width:20%;'><h2>Certifications</h2><table><tr><th>Certification</th><th>Action</th></tr>");
			   		
   			while (rsCerts.next()){   				
	   			out.print("<tr><form action ='modCert2' method='post' onsubmit='return checkRole()'>"+
	   					"<input type='hidden' name='page' value='therapistSchedule'>"+
 						"<input type='hidden' name='userId' value='"+userId+"'>"+
						"<input type='hidden' name='certId' value='"+rsCerts.getString(1)+"'>"+
						"<td>"+rsCerts.getString(4)+"</td>"+
						"<td><input type='submit' name='action' value='Remove'></td>" +
						"</form></tr>");	   			
				}
   				out.print("<tr><form action ='modCert2' method='post' onsubmit='return checkRole()'>"+
   						"<input type='hidden' name='page' value='therapistSchedule'>"+
						"<input type='hidden' name='userId' value='"+userId+"'>"+
   						"<td><select name='certId' style='width:80%;'><option value='' disabled selected>Certification</option>");
	   			while (rsCertList.next()){
					out.print("<option value=" + rsCertList.getString(1) + ">"+ rsCertList.getString(2) +"</option>");
				}
	   			out.print(
						"</select></td>"+
						"<td><input type='submit' name='action' value='Add'></td>" +
						"</form></tr>"+
						"</table></div></div><div style='width:60%;'>");
   		}
		
		if (rs != null){
			try {
				while (rs.next()) {
					int hourInt = Integer.parseInt(rs.getString(3).split(" ")[1].split(":")[0]);
					String hourStr = null;
					String filled = "Open";
					date = rs.getString(3).split(" ")[0];
					
					if (!date.equals(previousDate)){
						if (previousDate == null) {
							out.print(
									"<div style='display:inline-block;padding-right:30px;'><h2>"+date+"</h2>" +
							
									"<form action ='blockDay' method='post'>" +
									"<input type='hidden' name='userId' value='" + rs.getString(2) + "'>" +
									"<input type='hidden' name='date' value='" + date + "'>" +
									"<input type='submit' name='block' value='Mark off Day'></form><br>" +
									
											
									"<table><tr>" +
								       // "<th>Date</th>" +
								        "<th>Time</th>" +
								        "<th>Status</th>" +
								        "<th>Block</th>" +
								        "<th>Unblock</th>" +
							     	"</tr>"
									);
						} else {
							out.print(
									"</table></div><div style='display:inline-block; padding-right: 30px;'><h2>"+date+"</h2>"+
									"<form action ='blockDay' method='post'>" +
									"<input type='hidden' name='userId' value='" + rs.getString(2) + "'>" +
									"<input type='hidden' name='date' value='" + date + "'>" +
									"<input type='submit' name='block' value='Mark off Day'></form><br>" +
									"<table><tr>" +
								        //"<th>Date</th>" +
								        "<th>Time</th>" +
								        "<th>Status</th>" +
								        "<th>Block</th>" +
								        "<th>Unblock</th>" +
							     	"</tr>"
									);
						}
					}
					
					if (hourInt < 12) {
						hourStr = hourInt + " AM";
					} else if (hourInt == 12) {
						hourStr = hourInt + " PM";
					} else {
						hourStr = hourInt - 12 + " PM";
					}
					
					if (rs.getString(5) != null) {
						if (rs.getString(5).equals("OFF")) {
							filled = "Off";
						} else {
							filled = "Filled";
						}
					}
					
					out.print(
							"<tr>" + 
							//"    <td>" + date + "</td>" +
							"    <td>" + hourStr + "</td>" +
							"    <td>" + filled + "</td>" +
							"    <form action ='blockAppointment' method='post'>" +
							"	 <input type='hidden' name='userId' value='" + rs.getString(2) + "'>" +
							"	 <input type='hidden' name='treatmentName' value='" + rs.getString(4) + "'>" +
							"	 <input type='hidden' name='appointmentId' value='" + rs.getString(1) + "'>" +	
							"    <td><input type='submit' name='block' value='Block'></td>" +
							"    <td><input type='submit' name='block' value='Unblock'></td> </form>" +
							"  </tr>");
					previousDate = date;
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.print("</table></div><br><br><br>");
			
			String userId2 = (String)request.getAttribute("userId");
			
			out.print("<form action ='schedule' method='post'>" + 
					"		<input type='hidden' name='userId' value= " + userId2 + ">" + 
					"     	<table>" + 
					"        <tr><th>Day #</th><th>Date</th><th>Starting Hour</th><th>Ending Hour</th>" + 
					"        </tr>" + 
					"        <tr>" + 
					"          <td>Day 1</td>" + 
					"          <td><input type='date' name='date1' id='date1'></td>" + 
					"          <td>" + 
					"            <select name='hoursStart1' id='hoursStart1'>" + 
					"              <option value='' disabled selected>Starting Hour</option>" + 
					"              <option value='8'>8 AM</option>" + 
					"              <option value='9'>9 AM</option>" + 
					"              <option value='10'>10 AM</option>" + 
					"              <option value='11'>11 AM</option>" + 
					"              <option value='12'>12 PM</option>" + 
					"              <option value='13'>1 PM</option>" + 
					"              <option value='14'>2 PM</option>" + 
					"              <option value='15'>3 PM</option>" + 
					"              <option value='16'>4 PM</option>" + 
					"              <option value='17'>5 PM</option>" + 
					"            </select>" + 
					"          </td>" + 
					"          <td>" + 
					"            <select name='hoursEnd1' id='hoursEnd1'>" + 
					"              <option value='' disabled selected>Ending Hour</option>" + 
					"              <option value='8'>8 AM</option>" + 
					"              <option value='9'>9 AM</option>" + 
					"              <option value='10'>10 AM</option>" + 
					"              <option value='11'>11 AM</option>" + 
					"              <option value='12'>12 PM</option>" + 
					"              <option value='13'>1 PM</option>" + 
					"              <option value='14'>2 PM</option>" + 
					"              <option value='15'>3 PM</option>" + 
					"              <option value='16'>4 PM</option>" + 
					"              <option value='17'>5 PM</option>" + 
					"            </select>" + 
					"          </td>" + 
					"        </tr>" + 
					"" + 
					"        <tr>" + 
					"          <td>Day 2</td>" + 
					"          <td><input type='date' name='date2' id='date2'></td>" + 
					"          <td>" + 
					"            <select name='hoursStart2' id='hoursStart2'>" + 
					"              <option value='' disabled selected>Starting Hour</option>" + 
					"              <option value='8'>8 AM</option>" + 
					"              <option value='9'>9 AM</option>" + 
					"              <option value='10'>10 AM</option>" + 
					"              <option value='11'>11 AM</option>" + 
					"              <option value='12'>12 PM</option>" + 
					"              <option value='13'>1 PM</option>" + 
					"              <option value='14'>2 PM</option>" + 
					"              <option value='15'>3 PM</option>" + 
					"              <option value='16'>4 PM</option>" + 
					"              <option value='17'>5 PM</option>" + 
					"            </select>" + 
					"          </td>" + 
					"          <td>" + 
					"            <select name='hoursEnd2' id='hoursEnd2'>" + 
					"              <option value='' disabled selected>Ending Hour</option>" + 
					"              <option value='8'>8 AM</option>" + 
					"              <option value='9'>9 AM</option>" + 
					"              <option value='10'>10 AM</option>" + 
					"              <option value='11'>11 AM</option>" + 
					"              <option value='12'>12 PM</option>" + 
					"              <option value='13'>1 PM</option>" + 
					"              <option value='14'>2 PM</option>" + 
					"              <option value='15'>3 PM</option>" + 
					"              <option value='16'>4 PM</option>" + 
					"              <option value='17'>5 PM</option>" + 
					"            </select>" + 
					"          </td>" + 
					"        </tr>" + 
					"" + 
					"        <tr>" + 
					"          <td>Day 3</td>" + 
					"          <td><input type='date' name='date3' id='date3'></td>" + 
					"          <td>" + 
					"            <select name='hoursStart3' id='hoursStart3'>" + 
					"              <option value='' disabled selected>Starting Hour</option>" + 
					"              <option value='8'>8 AM</option>" + 
					"              <option value='9'>9 AM</option>" + 
					"              <option value='10'>10 AM</option>" + 
					"              <option value='11'>11 AM</option>" + 
					"              <option value='12'>12 PM</option>" + 
					"              <option value='13'>1 PM</option>" + 
					"              <option value='14'>2 PM</option>" + 
					"              <option value='15'>3 PM</option>" + 
					"              <option value='16'>4 PM</option>" + 
					"              <option value='17'>5 PM</option>" + 
					"            </select>" + 
					"          </td>" + 
					"          <td>" + 
					"            <select name='hoursEnd3' id='hoursEnd3'>" + 
					"              <option value='' disabled selected>Ending Hour</option>" + 
					"              <option value='8'>8 AM</option>" + 
					"              <option value='9'>9 AM</option>" + 
					"              <option value='10'>10 AM</option>" + 
					"              <option value='11'>11 AM</option>" + 
					"              <option value='12'>12 PM</option>" + 
					"              <option value='13'>1 PM</option>" + 
					"              <option value='14'>2 PM</option>" + 
					"              <option value='15'>3 PM</option>" + 
					"              <option value='16'>4 PM</option>" + 
					"              <option value='17'>5 PM</option>" + 
					"            </select>" + 
					"          </td>" + 
					"        </tr>" + 
					"" + 
					"        <tr>" + 
					"          <td>Day 4</td>" + 
					"          <td><input type='date' name='date4' id='date4'></td>" + 
					"          <td>" + 
					"            <select name='hoursStart4' id='hoursStart4'>" + 
					"              <option value='' disabled selected>Starting Hour</option>" + 
					"              <option value='8'>8 AM</option>" + 
					"              <option value='9'>9 AM</option>" + 
					"              <option value='10'>10 AM</option>" + 
					"              <option value='11'>11 AM</option>" + 
					"              <option value='12'>12 PM</option>" + 
					"              <option value='13'>1 PM</option>" + 
					"              <option value='14'>2 PM</option>" + 
					"              <option value='15'>3 PM</option>" + 
					"              <option value='16'>4 PM</option>" + 
					"              <option value='17'>5 PM</option>" + 
					"            </select>" + 
					"          </td>" + 
					"          <td>" + 
					"            <select name='hoursEnd4' id='hoursEnd4'>" + 
					"              <option value='' disabled selected>Ending Hour</option>" + 
					"              <option value='8'>8 AM</option>" + 
					"              <option value='9'>9 AM</option>" + 
					"              <option value='10'>10 AM</option>" + 
					"              <option value='11'>11 AM</option>" + 
					"              <option value='12'>12 PM</option>" + 
					"              <option value='13'>1 PM</option>" + 
					"              <option value='14'>2 PM</option>" + 
					"              <option value='15'>3 PM</option>" + 
					"              <option value='16'>4 PM</option>" + 
					"              <option value='17'>5 PM</option>" + 
					"            </select>" + 
					"          </td>" + 
					"        </tr>" + 
					"" + 
					"        <tr>" + 
					"          <td>Day 5</td>" + 
					"          <td><input type='date' name='date5' id='date5'></td>" + 
					"          <td>" + 
					"            <select name='hoursStart5' id='hoursStart'>" + 
					"              <option value='' disabled selected>Starting Hour</option>" + 
					"              <option value='8'>8 AM</option>" + 
					"              <option value='9'>9 AM</option>" + 
					"              <option value='10'>10 AM</option>" + 
					"              <option value='11'>11 AM</option>" + 
					"              <option value='12'>12 PM</option>" + 
					"              <option value='13'>1 PM</option>" + 
					"              <option value='14'>2 PM</option>" + 
					"              <option value='15'>3 PM</option>" + 
					"              <option value='16'>4 PM</option>" + 
					"              <option value='17'>5 PM</option>" + 
					"            </select>" + 
					"          </td>" + 
					"          <td>" + 
					"            <select name='hoursEnd5' id='hoursEnd5'>" + 
					"              <option value='' disabled selected>Ending Hour</option>" + 
					"              <option value='8'>8 AM</option>" + 
					"              <option value='9'>9 AM</option>" + 
					"              <option value='10'>10 AM</option>" + 
					"              <option value='11'>11 AM</option>" + 
					"              <option value='12'>12 PM</option>" + 
					"              <option value='13'>1 PM</option>" + 
					"              <option value='14'>2 PM</option>" + 
					"              <option value='15'>3 PM</option>" + 
					"              <option value='16'>4 PM</option>" + 
					"              <option value='17'>5 PM</option>" + 
					"            </select>" + 
					"          </td>" + 
					"        </tr>" + 
					"" + 
					"      </table> <br>" + 
					"      <input type='submit' value='Submit'>" + 
					"    </form>");
		}
		%>		
    
  </div>
</body>
</html>
