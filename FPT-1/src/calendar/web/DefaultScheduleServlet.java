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
@WebServlet("/defaultSchedule")
public class DefaultScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DefaultScheduleServlet() {
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
		String appointmentDate = null;
		
		String timeMon = null;
		String timeTue = null;
		String timeWen = null;
		String timeThu = null;
		String timeFri = null;
		String timeSat = null;
		
		CalendarBean calendarBean = new CalendarBean();
		calendarBean.setUserId(userId);
		
		CalendarDao calendarDao = new CalendarDao();
		ResultSet rsSchedule = calendarDao.pullDefaultSchedule(calendarBean);
		
		try {
			while (rsSchedule.next()) {
				switch (rsSchedule.getString(3)) {
					case "MONDAY":
						timeMon = rsSchedule.getString(4) + "-" + rsSchedule.getString(5);
						break;
					case "TUESDAY":
						timeTue = rsSchedule.getString(4) + "-" + rsSchedule.getString(5);
						break;
					case "WEDNESDAY":
						timeWen = rsSchedule.getString(4) + "-" + rsSchedule.getString(5);
						break;
					case "THURSDAY":
						timeThu = rsSchedule.getString(4) + "-" + rsSchedule.getString(5);
						break;
					case "FRIDAY":
						timeFri = rsSchedule.getString(4) + "-" + rsSchedule.getString(5);
						break;
					case "SATURDAY":
						timeSat = rsSchedule.getString(4) + "-" + rsSchedule.getString(5);
						break;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		LocalDate curDate = LocalDate.now();
		LocalDate appDate = curDate;
		String startHour = null;
		String endHour = null;
		
		for (int i = 0; i < 60; i++) {
			switch (appDate.getDayOfWeek().toString()) {
				case "MONDAY":
					if (!timeMon.contains("null")) {
						startHour = timeMon.split("-")[0];
						endHour = timeMon.split("-")[1];
					}
					break;
				case "TUESDAY":
					if (!timeTue.contains("null")) {
						startHour = timeMon.split("-")[0];
						endHour = timeMon.split("-")[1];
					}
					break;
				case "WEDNESDAY":
					if (!timeWen.contains("null")) {
						startHour = timeMon.split("-")[0];
						endHour = timeMon.split("-")[1];
					}
					break;
				case "THURSDAY":
					if (!timeThu.contains("null")) {
						startHour = timeMon.split("-")[0];
						endHour = timeMon.split("-")[1];
					}
					break;
				case "FRIDAY":
					if (!timeFri.contains("null")) {
						startHour = timeMon.split("-")[0];
						endHour = timeMon.split("-")[1];
					}
					break;
				case "SATURDAY":
					if (!timeSat.contains("null")) {
						startHour = timeMon.split("-")[0];
						endHour = timeMon.split("-")[1];
					}
					break;
			}
		
			int hour = Integer.parseInt(startHour);			
			int num = Integer.parseInt(endHour) - Integer.parseInt(startHour);//Number of hours a person works
			
			//stops the loop if the hour ended was lower than the hour started
			if (num < 1) {
				curDate.plusDays(1);
				continue;//Stops the process for the current day while moving to the next day
			}
			
			String date = curDate.toString();
			
			//Loop to insert an appointment in the database for each hour worked.
			for (int ix = 0; ix < num; ix++) {
				
				//Checks that hour is double digit. If not it adds a 0 to hours string to keep the datetime input correct.
				if (hour < 10) {
					appointmentDate = date + " 0" + hour + ":00:00";
				} else {
					appointmentDate = date + " " + hour + ":00:00";
				}
	
				CalendarBean calendarBean2 = new CalendarBean();
				calendarBean2.setUserId(userId);
				calendarBean2.setAppointmentDate(appointmentDate);
				
				CalendarDao calendarDao2 = new CalendarDao();
				calendarDao2.pushAppointments(calendarBean2);
				
				hour++;//Increases hour for the next interaction of the loop
			}
		}
		
		response.sendRedirect("loginSuccess.jsp");//Redirects to the original page
	}
	
	

}
