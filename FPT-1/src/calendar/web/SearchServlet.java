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
@WebServlet("/search")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String searchTreatment = request.getParameter("searchTreatment");
		String date = request.getParameter("date");
		
		CalendarBean calendarBean = new CalendarBean();
		calendarBean.setTreatmentName(searchTreatment);
		calendarBean.setAppointmentDate(date);
		
		CalendarDao calendarDao = new CalendarDao();
		ResultSet rs1 = calendarDao.searchTreatmentDate(calendarBean);
		
		
		request.setAttribute("date", date);
		request.setAttribute("rs1", rs1);
		request.setAttribute("searchTreatment", searchTreatment);
		
		getServletContext().getRequestDispatcher("/searchAppointment.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		String searchTreatment = request.getParameter("searchTreatment");
		String date = request.getParameter("date");
		
		CalendarBean calendarBean = new CalendarBean();
		calendarBean.setTreatmentName(searchTreatment);
		calendarBean.setAppointmentDate(date);
		
		CalendarDao calendarDao = new CalendarDao();
		ResultSet rs1 = calendarDao.searchTreatmentDate(calendarBean);
		
		
		request.setAttribute("date", date);
		request.setAttribute("rs1", rs1);
		request.setAttribute("searchTreatment", searchTreatment);
		
		getServletContext().getRequestDispatcher("/searchAppointment.jsp").forward(request, response);
	}
	
	

}
