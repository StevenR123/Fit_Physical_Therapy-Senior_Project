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
@WebServlet("/blockAppointment")
public class BlockAppointmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BlockAppointmentServlet() {
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
		String appointmentId = request.getParameter("appointmentId");
		String treatmentName = request.getParameter("treatmentName");
		//String customerName = request.getParameter("customerName");
		String block = request.getParameter("block");
		String userId = request.getParameter("userId");
		
		
		if (treatmentName.equalsIgnoreCase("null")) {
			CalendarBean calendarBean = new CalendarBean();
			calendarBean.setAppointmentId(appointmentId);
			calendarBean.setCustomerName("OFF");
			
			CalendarDao calendarDao = new CalendarDao();
			
			if (block.equals("Block")) {
				calendarBean.setTreatmentName("NULL");
				calendarBean.setAppointmentNotes("NULL");
				
				calendarDao.makeAppointment(calendarBean);
			} else {
				calendarDao.clearAppointment(calendarBean);
			}
		}
		
		response.sendRedirect("therapistSchedule?userId="+userId);
	}
	
	

}
