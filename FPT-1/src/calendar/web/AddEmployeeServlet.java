package calendar.web;


import java.io.IOException;
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
@WebServlet("/addEmp")
public class AddEmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddEmployeeServlet() {
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
		String firstName = request.getParameter("newFirstName");
		String userName = request.getParameter("newUserName");
		String password = request.getParameter("newPassword");
		String role = request.getParameter("newRole");
		String userId = null;
		
		ResultSet rsInfo = null;
		ResultSet rsInfo2 = null;
		String error = null;	
		boolean makeSchedule = false;
		
		request.setAttribute("firstName",firstName);
		
		if (firstName != "" && userName != "" && password != "" && role != "") {
			CalendarBean calendarBean = new CalendarBean();
			calendarBean.setFirstName(firstName);
			calendarBean.setUserName(userName);
			calendarBean.setPassword(password);
			calendarBean.setRole(role);
			
			CalendarDao calendarDao = new CalendarDao();
			calendarDao.addUser(calendarBean);
			rsInfo = calendarDao.getUserInfo(calendarBean);
			
			try {
				rsInfo.next();
				userId = rsInfo.getString(1);
				
				if(userId != null) {
					calendarBean.setUserId(userId);
				}				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			calendarBean.setDayOfWeek("MONDAY");
			calendarDao.addSchedule(calendarBean);
			
			calendarBean.setDayOfWeek("TUESDAY");
			calendarDao.addSchedule(calendarBean);
			
			calendarBean.setDayOfWeek("WENDSDAY");
			calendarDao.addSchedule(calendarBean);
			
			calendarBean.setDayOfWeek("THURSDAY");
			calendarDao.addSchedule(calendarBean);
			
			calendarBean.setDayOfWeek("FRIDAY");
			calendarDao.addSchedule(calendarBean);
			
			calendarBean.setDayOfWeek("SATURDAY");
			calendarDao.addSchedule(calendarBean);
			
			getServletContext().getRequestDispatcher("/therapistSchedule?userId="+userId).forward(request, response);
		} else {
			error = "1";
			
			request.setAttribute("addError", error);
			
			getServletContext().getRequestDispatcher("/admin.jsp").forward(request, response);
		}		
	}
	
	

}
