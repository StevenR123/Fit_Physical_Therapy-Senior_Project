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
@WebServlet("/blockDay")
public class BlockDayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BlockDayServlet() {
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
		String date = request.getParameter("date");
		//String treatmentName = request.getParameter("treatmentName");
		//String customerName = request.getParameter("customerName");
		//String block = request.getParameter("block");
		String userId = request.getParameter("userId");
		
		CalendarBean calendarBean = new CalendarBean();
		calendarBean.setAppointmentDate(date);
		calendarBean.setUserId(userId);
		
		CalendarDao calendarDao = new CalendarDao();
		
		calendarDao.blockDay(calendarBean);
		
		response.sendRedirect("therapistSchedule?userId="+userId);
	}
	
	

}
