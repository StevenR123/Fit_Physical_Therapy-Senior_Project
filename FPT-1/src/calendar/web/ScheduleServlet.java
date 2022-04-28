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
@WebServlet("/schedule")
public class ScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ScheduleServlet() {
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
		String appointmentDate = null;
		
		ArrayList<String> dateList = new ArrayList<>();		
		ArrayList<String> hoursStartList = new ArrayList<>();		
		ArrayList<String> hoursEndList = new ArrayList<>();
		
		//The following if statements check that all the scheduling information was entered for a given day before adding any of the information to their lists
			if(!request.getParameter("date1").isBlank() && request.getParameter("hoursStart1") != null && request.getParameter("hoursEnd1") != null) {
				dateList.add(request.getParameter("date1"));
				hoursStartList.add(request.getParameter("hoursStart1"));
				hoursEndList.add(request.getParameter("hoursEnd1"));
			}
			
			if(!request.getParameter("date2").isBlank() && request.getParameter("hoursStart2") != null && request.getParameter("hoursEnd2") != null) {
				dateList.add(request.getParameter("date2"));
				hoursStartList.add(request.getParameter("hoursStart2"));
				hoursEndList.add(request.getParameter("hoursEnd2"));
			}
			
			if(!request.getParameter("date3").isBlank() && request.getParameter("hoursStart3") != null && request.getParameter("hoursEnd3") != null) {
				dateList.add(request.getParameter("date3"));
				hoursStartList.add(request.getParameter("hoursStart3"));
				hoursEndList.add(request.getParameter("hoursEnd3"));
			} 
			
			if(!request.getParameter("date4").isBlank() && request.getParameter("hoursStart4") != null && request.getParameter("hoursEnd4") != null) {
				dateList.add(request.getParameter("date4"));
				hoursStartList.add(request.getParameter("hoursStart4"));
				hoursEndList.add(request.getParameter("hoursEnd4"));
			}
			
			if(!request.getParameter("date5").isBlank() && request.getParameter("hoursStart5") != null && request.getParameter("hoursEnd5") != null) {
				dateList.add(request.getParameter("date5"));
				hoursStartList.add(request.getParameter("hoursStart5"));
				hoursEndList.add(request.getParameter("hoursEnd5"));
			}
		
		//Loops the open appointment process for each day given
		for	(int i = 0; i < dateList.size(); i++) {
			String date = dateList.get(i);		
			int hoursStart = Integer.parseInt(hoursStartList.get(i));
			int hoursEnd = Integer.parseInt(hoursEndList.get(i));
			int hour = hoursStart;
			
			int num = hoursEnd - hoursStart;//Number of hours a person works
			
			//stops the loop if the hour ended was lower than the hour started
			if (num < 1) {
				continue;//Stops the process for the current day while moving to the next day
			}
			
			//Loop to insert an appointment in the database for each hour worked.
			for (int ix = 0; ix < num; ix++) {
				
				//Checks that hour is double digit. If not it adds a 0 to hours string to keep the datetime input correct.
				if (hour < 10) {
					appointmentDate = date + " 0" + hour + ":00:00";
				} else {
					appointmentDate = date + " " + hour + ":00:00";
				}
	
				CalendarBean calendarBean = new CalendarBean();
				calendarBean.setUserId(userId);
				calendarBean.setAppointmentDate(appointmentDate);
				
				CalendarDao calendarDao = new CalendarDao();
				calendarDao.pushAppointments(calendarBean);
				
				hour++;//Increases hour for the next interaction of the loop
			}
		}
		
		
		response.sendRedirect("therapistSchedule?userId="+userId);//Redirects to the original page
	}
	
	

}
