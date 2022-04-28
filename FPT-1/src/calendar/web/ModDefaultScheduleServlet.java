package calendar.web;


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

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
@WebServlet("/modDefault")
public class ModDefaultScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModDefaultScheduleServlet() {
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
		//This is a WIP and non functional
		
		String userId = request.getParameter("userId");
		
		String startHour = null;
		String endHour = null;
		String scheduleId = null;
		
		CalendarBean calendarBean = new CalendarBean();
		CalendarDao calendarDao = new CalendarDao();
		
		for(int i = 1;i<7;i++) {
			System.out.println(i);
			startHour = request.getParameter("startTime"+i);
			endHour = request.getParameter("endTime"+i);
			scheduleId = request.getParameter("scheduleId"+i);
			
			calendarBean.setScheduleId(scheduleId);
			
			calendarBean.setStartHour(startHour);
			calendarBean.setEndHour(endHour);

			
			calendarDao.updateDefaultSchedule(calendarBean);
		}

		getServletContext().getRequestDispatcher("/therapistSchedule?userId="+userId).forward(request, response);
	}
	
	

}
