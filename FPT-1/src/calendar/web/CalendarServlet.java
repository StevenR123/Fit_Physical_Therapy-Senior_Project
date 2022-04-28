package calendar.web;


import java.io.IOException;
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
@WebServlet("/calendar")
public class CalendarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CalendarServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		String userId = request.getParameter("userId");
		String appointmentDate = request.getParameter("appointmentDate");
		String treatmentName = request.getParameter("treatmentName");
		String customerName = request.getParameter("customerName");
		String appointmentNotes = request.getParameter("appointmentNotes");
		
		String date = request.getParameter("date");
		String hours = request.getParameter("hours");
		
		CalendarBean calendarBean = new CalendarBean();
		calendarBean.setUserId(userId);
		calendarBean.setAppointmentDate(appointmentDate);
		calendarBean.setTreatmentName(treatmentName);
		calendarBean.setCustomerName(customerName);
		calendarBean.setAppointmentNotes(appointmentNotes);
		
		CalendarDao calendarDao = new CalendarDao();
		
		if (calendarDao.pushAppointments(calendarBean))
		{
			response.sendRedirect("loginSuccess.jsp");
			
		}
		else 
		{
			response.sendRedirect("calendar.jsp");
			
		}
	}
	
	

}
