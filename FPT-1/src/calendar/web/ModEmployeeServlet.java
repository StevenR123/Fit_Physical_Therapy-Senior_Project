package calendar.web;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import calendar.bean.CalendarBean;
import calendar.database.CalendarDao;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/modEmp")
public class ModEmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModEmployeeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		String firstName = request.getParameter("modFirstName");
		String userName = request.getParameter("modUserName");
		String password = request.getParameter("modPassword");
		String role = request.getParameter("modRole");
		String userId = request.getParameter("userId");
		
		String error = null;		
		
		//if (!firstName.equalsIgnoreCase("null") && !userName.equalsIgnoreCase("null") && !password.equalsIgnoreCase("null") && !role.equalsIgnoreCase("null")) {
		if (!firstName.equalsIgnoreCase("null") && !userName.equalsIgnoreCase("null") && !password.equalsIgnoreCase("null")) {
			CalendarBean calendarBean = new CalendarBean();
			calendarBean.setFirstName(firstName);
			calendarBean.setUserName(userName);
			calendarBean.setPassword(password);
			calendarBean.setRole(role);
			calendarBean.setUserId(userId);
			
			CalendarDao calendarDao = new CalendarDao();
			
			if (role.equals("remove")) {
				calendarDao.removeDefaultSchedule(calendarBean);	
				calendarDao.removeUser(calendarBean);				
				getServletContext().getRequestDispatcher("/admin.jsp").forward(request, response);
			} else if (!role.equalsIgnoreCase("none")) {
				calendarDao.updateUser(calendarBean);
				getServletContext().getRequestDispatcher("/therapistSchedule?userId="+userId).forward(request, response);
			} else {
				error = "1";
				
				request.setAttribute("modError", error);
				
				getServletContext().getRequestDispatcher("/admin.jsp").forward(request, response);
			}
			
		} else {
			error = "1";
			
			request.setAttribute("modError", error);
			
			getServletContext().getRequestDispatcher("/admin.jsp").forward(request, response);
		}
		
		
	}
	
	

}
