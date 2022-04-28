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
@WebServlet("/therapistSchedule")
public class TherapistScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TherapistScheduleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("userId");
		LocalDate currentDate = LocalDate.now();
		
		CalendarBean calendarBean = new CalendarBean();
		calendarBean.setUserId(userId);
		
		CalendarDao calendarDao = new CalendarDao();
		ResultSet rs = calendarDao.twoWeekSchedule(calendarBean);
		ResultSet rsDefault = calendarDao.pullDefaultSchedule(calendarBean);
		ResultSet rsCerts = calendarDao.pullTherapistCertifications(calendarBean);
        ResultSet rsCertList = calendarDao.getCertList(calendarBean);
		
		
		request.setAttribute("rs", rs);
		request.setAttribute("rsDefault", rsDefault);
		request.setAttribute("rsCerts", rsCerts);
		request.setAttribute("currentDate", currentDate);
		request.setAttribute("rsCertList", rsCertList);
		request.setAttribute("userId", userId);
		
		getServletContext().getRequestDispatcher("/therapistSchedule.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		String userId = request.getParameter("userId");
		LocalDate currentDate = LocalDate.now();
		
		CalendarBean calendarBean = new CalendarBean();
		calendarBean.setUserId(userId);
		
		CalendarDao calendarDao = new CalendarDao();
		ResultSet rs = calendarDao.twoWeekSchedule(calendarBean);
		ResultSet rsDefault = calendarDao.pullDefaultSchedule(calendarBean);
		ResultSet rsCerts = calendarDao.pullTherapistCertifications(calendarBean);
        ResultSet rsCertList = calendarDao.getCertList(calendarBean);
		
		
		request.setAttribute("rs", rs);
		request.setAttribute("rsDefault", rsDefault);
		request.setAttribute("rsCerts", rsCerts);
		request.setAttribute("currentDate", currentDate);
		request.setAttribute("rsCertList", rsCertList);
		request.setAttribute("userId", userId);
		
		getServletContext().getRequestDispatcher("/therapistSchedule.jsp").forward(request, response);
	}
	

}
