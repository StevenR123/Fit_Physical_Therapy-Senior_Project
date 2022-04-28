<!-- Jessica
Session validation added by Steven
 -->

<%
	String sessionRole = "";
	try {
		sessionRole = session.getAttribute("role").toString();
	} catch (NullPointerException e){}
	
	if(sessionRole.isEmpty()){
		response.sendRedirect("./login.jsp");
	}
%>

<jsp:include page='login/header.jsp'></jsp:include>
<jsp:include page='login/menu.jsp'></jsp:include>
<jsp:include page='login/body.jsp'></jsp:include>
<jsp:include page='login/footer.jsp'></jsp:include>