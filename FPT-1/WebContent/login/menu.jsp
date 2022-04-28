<!-- Created by Jessica
	Links added by Steven
 -->

<!DOCTYPE html>
<html>
<link href="./login/homePageStyles.css" rel="stylesheet" type="text/css">
<div id="nav" class="vmenu">

	<%
		String sessionRole = "";
		try {
			sessionRole = session.getAttribute("role").toString();
		} catch (NullPointerException e){}
		
		if(sessionRole.isEmpty()){
			response.sendRedirect("./login.jsp");
		}
		
		if(session.getAttribute("role").equals("admin")) {
			out.print(
					"<a class='menu_link active' href='loginSuccess.jsp'>Home Page</a>" +
					"<a class='menu_link' href='./searchAppointment.jsp'>Make Appointments</a>" +
					"<a class='menu_link' href='./dailySchedule.jsp'>Daily Schedule</a>" +
					"<a class='menu_link' href='./therapistSchedule.jsp'>Employee Schedule</a>" +
					"<a class='menu_link' href='./certifications.jsp'>Certifications</a>" +
					"<a class='menu_link' href='./admin.jsp'>Admin</a>"
					);
		} else if (session.getAttribute("role").equals("registrar")) {
			out.print(
					"<a class='menu_link active' href='loginSuccess.jsp'>Home Page</a>" +
					"<a class='menu_link' href='./searchAppointment.jsp'>Make Appointments</a>" +
					"<a class='menu_link' href='./dailySchedule.jsp'>Daily Schedule</a>" +
					"<a class='menu_link' href='./therapistSchedule.jsp'>Employee Schedule</a>" 
					);
		}
		else {
			out.print(
					"<a class='menu_link active' href='loginSuccess.jsp'>Home Page</a>" +
					"<a class='menu_link' href='./dailySchedule.jsp'>Daily Schedule</a>" +
					"<a class='menu_link' href='./therapistSchedule.jsp'>Employee Schedule</a>" 
					);
		}
	
	%>
</div>

<script>
	var header = document.getElementById("nav");
	var links = header.getElementsByClassName("menu_link");
	for (var i = 0; i < links.length; i++){
		links[i].addEventListener("click", function(){
			var current = document.getElementsByClassName("active");
			current[0].className = current[0].className.replace(" active", "");
			this.className += " active";
		});
	}
</script>
</html>