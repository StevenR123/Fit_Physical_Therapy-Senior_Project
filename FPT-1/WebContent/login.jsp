
<%@ page import="java.sql.*"%>

<%
	if(request.getParameter("btn_login")!=null) 
    {
        String email,password;
        
        email=request.getParameter("username"); 
        password=request.getParameter("password");
        
        String dburl= "jdbc:mysql://192.168.6.3:3306/fpt?autoReconnect=true&useSSL=false";
        String dbuname="student"; //database username   
        String dbpwd ="Cpt275Ttc!"; //database password      

        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver"); //load driver
            Connection con=DriverManager.getConnection(dburl,dbuname,dbpwd); //create connection
            
            PreparedStatement pstmt=null; //create statement
            
            pstmt=con.prepareStatement("SELECT * FROM users WHERE UserName=? AND Password=?"); //sql select query
            pstmt.setString(1,email);
            pstmt.setString(2,password);   
            ResultSet rs=pstmt.executeQuery(); //execute query and set in ResultSet object "rs".
            
            if(rs.next())
            {
                String dbemail=rs.getString("UserName");
                String dbpassword=rs.getString("Password");     
                String dbrole=rs.getString("Role");
                String dbName = rs.getString("FirstName");
                
                if(email.equals(dbemail) && password.equals(dbpassword)) 
                {
                	session.setAttribute("name",dbName); 
                    session.setAttribute("role",dbrole);
                }
            }
            else
            {
                request.setAttribute("errorMsg","invalid email or password or role"); 
            }
            
            pstmt.close(); //close statement 
            con.close(); //close connection 
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
	
	//Moved to the bottom so the page load the fist time you login.
	if(session.getAttribute("name")!=null){
		response.sendRedirect("loginSuccess.jsp");
	}
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>FIT</title>
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.7.1/css/all.css">
<link href="./login/loginStyles.css" rel="stylesheet" type="text/css">
</head>
<body>

	<div class="login">
		<h1>Login</h1>
		<form id="loginForm" method="post">
			<label for="username"> <i class="fas fa-user"></i>
			</label> <input type="text" name="username" placeholder="Username"
				id="username" required> <label for="password"> <i
				class="fas fa-lock"></i>
			</label> <input type="password" name="password" placeholder="Password"
				id="password" required>
			<input type="submit" name="btn_login" value="Login">
		</form>
	</div>
</body>
</html>