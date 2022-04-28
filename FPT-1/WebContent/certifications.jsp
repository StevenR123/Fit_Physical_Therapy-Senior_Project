<!-- Steven Reninga -->

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
<title>Certifications</title>
<link href="findSchedule.css" rel="stylesheet">
<style></style>
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
</script>

<body>
	<jsp:include page="login/header.jsp"></jsp:include>
	<jsp:include page="login/menu.jsp"></jsp:include>
	<div align ="center"> <!-- align ="center" -->
		<br><br><br><br><br>
		
		<div>
			<h1>Certification List</h1>
			<table style="width:800px;">
				<tr><th style='width:33%;'>Certification</th><th>Abbreviation</th><th>Treatment Name</th><th style='width:20%;'># of Therpist with Certification</th></tr>
				<%
					CalendarBean calendarBean = new CalendarBean();
			        CalendarDao calendarDao = new CalendarDao();
			        ResultSet rsCertList = calendarDao.getCertList(calendarBean);
			        ResultSet rsCertList2 = calendarDao.getCertList(calendarBean);
			        ResultSet rsNumPerCert = calendarDao.getNumPerCert(calendarBean);
			        ResultSet rsAll = calendarDao.getAllTherapistCertifications(calendarBean);
			        List<String> certList = new ArrayList <String>();
			        List<String> certIdList = new ArrayList <String>();
			
			        while (rsCertList.next() && rsNumPerCert.next()) {
			        	certList.add(rsCertList.getString(2));
			        	certIdList.add(rsCertList.getString(1));
				        out.print(
				        		"<tr><td>"+rsCertList.getString(2)+"</td><td>"+rsCertList.getString(3)+"</td><td>"+rsCertList.getString(4)+"</td><td>"+rsNumPerCert.getString(2)+"</td></tr>"
						);
			        }
				%>
			</table>
		</div>
		<h1>Certifications per user</h1>
		
		<div>
			<%
				String therapistName = null;
				String lastTherapistName = null;
				String therapistId = null;
				
				while (rsAll.next()){
					therapistName = rsAll.getString(5);					
					
					if (lastTherapistName == null){
						out.print("<div style='display:inline-block;padding-right:30px;width:20%;'><h2>"+rsAll.getString(5)+"</h2><table><tr><th>Certification</th><th>Action</th></tr>");
						therapistId = rsAll.getString(2);
					} else if (!therapistName.equals(lastTherapistName)){
						out.print("<tr><form action ='modCert' method='post' onsubmit='return checkRole()'>"+
						"<input type='hidden' name='userId' value='"+therapistId+"'>"+
						"<td><select name='certId' style='width:80%;'><option value='' disabled selected>Certification</option>");
						for (int i = 0;i < certList.size();i++){
							out.print("<option value=" + certIdList.get(i)+ ">"+ certList.get(i) +"</option>");
						}
						out.print(
						"</select></td>"+
						"<td><input type='submit' name='action' value='Add'></td>" +
						"</form></tr>"+
						"</table></div><div style='display:inline-block;padding-right:30px;width:20%;'><h2>"+rsAll.getString(5)+"</h2><table><tr><th>Certification</th><th>Action</th></tr>");
						therapistId = rsAll.getString(2);
					}
					
					
					out.print(
							"<tr><form action ='modCert' method='post' onsubmit='return checkRole()'>"+
							"<input type='hidden' name='certId' value='"+rsAll.getString(1)+"'>"+
							"<td>"+rsAll.getString(4)+"</td>"+
							"<td><input type='submit' name='action' value='Remove'></td>" +
							"</form></tr>");
					
			   		
					lastTherapistName = therapistName;
				}
				
				out.print("<tr><form action ='modCert' method='post' onsubmit='return checkRole()'>"+
						"<input type='hidden' name='userId' value='"+therapistId+"'>"+
						"<td><select name='certId' style='width:80%;'><option value='' disabled selected>Certification</option>");
						for (int i = 0;i < certList.size();i++){
							out.print("<option value=" + certIdList.get(i)+ ">"+ certList.get(i) +"</option>");
						}
						out.print(
						"</select></td>"+
						"<td><input type='submit' name='action' value='Add'></td>" +
						"</form></tr>"+"</table></div>");
			%>
		</div>
	</div>
</body>
</html>
