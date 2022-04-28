package calendar.web;


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
@WebServlet("/massUpload")
public class UploadScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadScheduleServlet() {
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
		String userId = null;
		int startHour = 0;
		int endHour = 0;
		String startHourStr = null;
		String endHourStr = null;		
		String appointmentDate = null;
		
		ResultSet rsTherapists = null;
		ResultSet rsDefaultSchedule = null;
		LocalDate currentDate = LocalDate.now();
		String dayOfWeek = null;
		
		List<String> startList = new ArrayList <String>();
		List<String> endList = new ArrayList <String>();
		
		CalendarBean calendarBean = new CalendarBean();
		CalendarDao calendarDao = new CalendarDao();
		
		rsTherapists = calendarDao.getTherapistNames(calendarBean);
		
		try {
			while (rsTherapists.next()) {
				LocalDate date = currentDate;
				
				userId = rsTherapists.getString(1);
				
				calendarBean.setUserId(userId);
				
				rsDefaultSchedule = calendarDao.pullDefaultSchedule(calendarBean);
				
				while (rsDefaultSchedule.next()) {
					startList.add(rsDefaultSchedule.getString(4));
					endList.add(rsDefaultSchedule.getString(5));
				}
				
				System.out.println(startList.toString());
				System.out.println(endList.toString());
				
				
				for (int i = 0; i < 30; i++) {
					
					dayOfWeek = date.getDayOfWeek().toString();
					
					
					
					switch (dayOfWeek) {
					case "MONDAY":				
							startHourStr = startList.get(0);
							endHourStr = endList.get(0);
					case "TUESDAY":					
							startHourStr = startList.get(1);
							endHourStr = endList.get(1);
					
					case "WENDSDAY":						
							startHourStr = startList.get(2);
							endHourStr = endList.get(2);
					
					case "THURSDAY":						
							startHourStr = startList.get(3);
							endHourStr = endList.get(3);
							
					case "FRIDAY":				
							startHourStr = startList.get(4);
							endHourStr = endList.get(4);
					
					case "SATURDAY":						
							startHourStr = startList.get(5);
							endHourStr = endList.get(5);
						
					}
					
					if(startHourStr != null && endHourStr != null) {
						
						startHour = Integer.parseInt(startHourStr);
						endHour = Integer.parseInt(endHourStr);
						
						int hour = startHour;
						
						int num = endHour - startHour;//Number of hours a person works
						
						//stops the loop if the hour ended was lower than the hour started
						if (num < 1) {
							continue;//Stops the process for the current day while moving to the next day
						}
						
						//Loop to insert an appointment in the database for each hour worked.
						for (int ix = 0; ix < num; ix++) {
							
							//Checks that hour is double digit. If not it adds a 0 to hours string to keep the datetime input correct.
							if (hour < 10) {
								appointmentDate = date.toString() + " 0" + hour + ":00:00";
							} else {
								appointmentDate = date.toString() + " " + hour + ":00:00";
							}

							calendarBean.setUserId(userId);
							calendarBean.setAppointmentDate(appointmentDate);

							calendarDao.pushAppointments(calendarBean);
							
							
							hour++;//Increases hour for the next interaction of the loop
						}
					}		
					
					date.plusDays(1);
				}
				startList.clear();
				endList.clear();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		response.sendRedirect("loginSuccess.jsp");//Redirects to the home page
	}
	
	

}
