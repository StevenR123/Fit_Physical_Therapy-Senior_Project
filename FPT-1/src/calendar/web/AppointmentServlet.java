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
@WebServlet("/appointment")
public class AppointmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AppointmentServlet() {
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
		String customerName = request.getParameter("customerName");
		String appointmentNotes = request.getParameter("appointmentNotes");
		String date = request.getParameter("date");
		
		//Checks if the customer name was filled. If so it makes an appointment
		if (customerName != "") {
			CalendarBean calendarBean = new CalendarBean();
			calendarBean.setAppointmentId(appointmentId);
			calendarBean.setTreatmentName(treatmentName);
			calendarBean.setCustomerName(customerName);
			calendarBean.setAppointmentNotes(appointmentNotes);
			
			CalendarDao calendarDao = new CalendarDao();
			calendarDao.makeAppointment(calendarBean);
		}
		
		getServletContext().getRequestDispatcher("/search?searchTreatment="+treatmentName+"&date="+date).forward(request, response);
	}
	
	

}
