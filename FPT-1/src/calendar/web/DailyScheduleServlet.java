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
@WebServlet("/dailySchedule")
public class DailyScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DailyScheduleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String date = request.getParameter("date");//Month and Day (MM-DD)
		
		CalendarBean calendarBean = new CalendarBean();
		calendarBean.setAppointmentDate(date);
		
		CalendarDao calendarDao = new CalendarDao();
		ResultSet rs = calendarDao.pullDay(calendarBean);
		ResultSet rs2 = calendarDao.pullDay(calendarBean);
		
		request.setAttribute("date", date);
		request.setAttribute("rs", rs);
		request.setAttribute("rs2", rs2);

		getServletContext().getRequestDispatcher("/dailySchedule.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		
	}
	
	

}
