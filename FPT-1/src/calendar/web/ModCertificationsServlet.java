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
@WebServlet("/modCert")
public class ModCertificationsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModCertificationsServlet() {
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
		String userId = request.getParameter("userId");
		String action = request.getParameter("action");
		String certId = request.getParameter("certId");
		//String page = request.getParameter("page");
		
		CalendarBean calendarBean = new CalendarBean();
		
		CalendarDao calendarDao = new CalendarDao();
		
		
		if (action.equalsIgnoreCase("add")) {
			calendarBean.setUserId(userId);
			calendarBean.setCertificationId(certId);
			
			calendarDao.addUserCert(calendarBean);
			response.sendRedirect("certifications.jsp");
		} else if (action.equalsIgnoreCase("remove")) {
			calendarBean.setUserCertificationId(certId);
			
			calendarDao.removeUserCert(calendarBean);
			response.sendRedirect("certifications.jsp");
		}
		
		/*if (page.equals("therapistSchedule")) {
			response.sendRedirect("therapistSchedule?userId="+userId);
		} else {
			response.sendRedirect("certifications.jsp");
		}*/
		
	}
	
	

}
