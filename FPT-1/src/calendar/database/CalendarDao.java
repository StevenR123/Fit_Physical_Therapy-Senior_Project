package calendar.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import calendar.bean.CalendarBean;

public class CalendarDao {
	
	private String dbUrl = "jdbc:mysql://192.168.6.3:3306/fpt?autoReconnect=true&useSSL=false";
	private String dbUname = "student";
	private String dbPassword = "Cpt275Ttc!";
	private String dbDriver = "com.mysql.cj.jdbc.Driver";
	
	
	
	public void loadDriver(String dbDriver)
	{
		try {
			Class.forName(dbDriver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection()
	{
		Connection con = null;
		try {
			con = DriverManager.getConnection(dbUrl, dbUname, dbPassword);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	
	//Gets all information from the Appointments Table in the Database
	public ResultSet pullAppointments()
	{
		ResultSet rs = null;
		
		loadDriver(dbDriver);
		Connection con = getConnection();
		
		String sql = "select * from appointments";
		PreparedStatement ps;
		
		try {
			ps = con.prepareStatement(sql);
	
			rs = ps.executeQuery();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	//Inserts new appointment into the Appointments Table in the Database
	public boolean pushAppointments(CalendarBean calendarBean)
	{
		boolean status = false;
		ResultSet rs = null;
		
		
		loadDriver(dbDriver);
		Connection con = getConnection();
		
		//The Insert statement for the Database
		String sql = "insert into appointments (UserId, AppointmentDate, TreatmentName, CustomerName, AppointmentNotes) values (?, ?, ?, ?, ?)";
		PreparedStatement ps;
		
		//The Select statement used to make sure duplicate appointments aren't made
		String sql2 = "SELECT * FROM appointments WHERE UserId = '"+calendarBean.getUserId()+"' and AppointmentDate = '"+calendarBean.getAppointmentDate()+"';";
		PreparedStatement ps2;
		try {
			ps2 = con.prepareStatement(sql2);
			rs = ps2.executeQuery();
			
			//Checks if there is an appointment for a User at a given time. Prevents duplicate appointments
			if (rs.next() == false) {
				ps = con.prepareStatement(sql);
				ps.setString(1, calendarBean.getUserId());
				ps.setString(2, calendarBean.getAppointmentDate());
				ps.setString(3, calendarBean.getTreatmentName());
				ps.setString(4, calendarBean.getCustomerName());
				ps.setString(5, calendarBean.getAppointmentNotes());
				status = ps.execute();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
	
	//Pulls appointment info and therapist name for a specific day
	public ResultSet pullDay(CalendarBean calendarBean) {
		ResultSet rs = null;
		
		loadDriver(dbDriver);
		Connection con = getConnection();
		
		String sql = "SELECT appointments.*,users.FirstName FROM appointments inner join users on appointments.UserId = users.UserId WHERE CAST(AppointmentDate AS DATE) = '" + calendarBean.getAppointmentDate() + "' ORDER BY AppointmentDate;";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;		
	}
	
	//Updates a given appointment with TreatmentName, CustomerName, and Appointment Notes
	public boolean makeAppointment(CalendarBean calendarBean) {
		boolean status = false;		
		
		loadDriver(dbDriver);
		Connection con = getConnection();
		
		String sql = "update appointments set TreatmentName='" + calendarBean.getTreatmentName() + "', CustomerName='" + calendarBean.getCustomerName() + "', AppointmentNotes='" + calendarBean.getAppointmentNotes() + "' where AppointmentId='" + calendarBean.getAppointmentId() + "'";
		
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(sql);
			status = ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return status;
	}
	
	//Clears a filled appointment of TreatmentName, CustomerName, and AppointmentNotes so that it can be reused
	public boolean clearAppointment(CalendarBean calendarBean) {
		boolean status = false;		
		
		loadDriver(dbDriver);
		Connection con = getConnection();
		
		String sql = "UPDATE appointments SET TreatmentName = NULL, CustomerName = NULL, AppointmentNotes = NULL WHERE AppointmentId = '"+ calendarBean.getAppointmentId() +"';";
		
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(sql);
			status = ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return status;
	}
	
	//Pulls all Treatment Names from the database
	public ResultSet getTreatmentNames(CalendarBean calendarBean) {
		ResultSet rs = null;
		
		loadDriver(dbDriver);
		Connection con = getConnection();
		
		
		String sql = "SELECT * FROM certifications order by TreatmentName;";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;		
	}
	
	//Pulls all Therapist Names from the database
	public ResultSet getTherapistNames(CalendarBean calendarBean) {
		ResultSet rs = null;
		
		loadDriver(dbDriver);
		Connection con = getConnection();
		
		
		String sql = "SELECT * FROM users order by FirstName;";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;		
	}
	
	public ResultSet twoWeekSchedule(CalendarBean calendarBean) {
		ResultSet rs = null;
		
		LocalDate localDate = LocalDate.now();
		String currentDate = localDate.toString();
		String endDate = localDate.plusDays(13).toString();
		
		loadDriver(dbDriver);
		Connection con = getConnection();
		
		String sql = "SELECT * FROM appointments WHERE CAST(AppointmentDate AS DATE) BETWEEN '"+ currentDate +"' AND '"+ endDate +"' AND UserId = "+ calendarBean.getUserId()+" ORDER BY AppointmentDate;";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;
	}
	
	//Pulls all appointments for therapists that are certified for a specific Treatment & Date
	public ResultSet searchTreatmentDate(CalendarBean calendarBean) {
		ResultSet rs = null;
		
		loadDriver(dbDriver);
		Connection con = getConnection();
		
		
		String sql = "select appointments.*, users.FirstName from appointments\n" + 
				"inner join usercertifications on appointments.UserId = usercertifications.UserId\n" + 
				"inner join certifications on usercertifications.CertificationId = certifications.CertificationId\n" + 
				"inner join users on appointments.UserId = users.UserId\n" + 
				"where certifications.TreatmentName = '" + calendarBean.getTreatmentName() + "' AND CAST(AppointmentDate AS DATE) = '"+ calendarBean.getAppointmentDate() +"'ORDER BY AppointmentDate;";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;		
	}
	
	//Pulls Certification List
	public ResultSet getCertList(CalendarBean calendarBean) {
		ResultSet rs = null;
		
		loadDriver(dbDriver);
		Connection con = getConnection();
		
		
		String sql = "SELECT * FROM certifications Order by certifications.CertificationName;";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;		
	}
	
	//Pulls all Therapist Names from the database
	public ResultSet pullDefaultSchedule(CalendarBean calendarBean) {
		ResultSet rs = null;
		
		loadDriver(dbDriver);
		Connection con = getConnection();
		
		
		String sql = "SELECT * FROM defaultschedules WHERE UserId='"+calendarBean.getUserId()+"';";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;		
	}
	
	//Pulls all Therapist Names from the database
		public ResultSet pullAllDefaultSchedule(CalendarBean calendarBean) {
			ResultSet rs = null;
			
			loadDriver(dbDriver);
			Connection con = getConnection();
			
			
			String sql = "SELECT * FROM defaultschedules;";
			PreparedStatement ps;
			try {
				ps = con.prepareStatement(sql);
				rs = ps.executeQuery();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return rs;		
		}
	
	public ResultSet getNumPerCert(CalendarBean calendarBean) {
		ResultSet rs = null;
		
		loadDriver(dbDriver);
		Connection con = getConnection();
		
		
		String sql = "SELECT CertificationId, count(CertificationId) AS 'Count' FROM usercertifications group by CertificationId;";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;		
	}
	
	public ResultSet pullTherapistCertifications(CalendarBean calendarBean) {
		ResultSet rs = null;
		
		loadDriver(dbDriver);
		Connection con = getConnection();
		
		
		String sql = "SELECT usercertifications.*, certifications.CertificationName FROM usercertifications inner join certifications on usercertifications.CertificationId = certifications.CertificationId WHERE usercertifications.UserId='"+calendarBean.getUserId()+"' Order by certifications.CertificationName;";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;		
	}
	
	public boolean blockDay(CalendarBean calendarBean) {
		boolean status = false;		
		
		loadDriver(dbDriver);
		Connection con = getConnection();
		
		String sql = "update appointments set TreatmentName='NULL', CustomerName='OFF', AppointmentNotes='NULL'" + 
				"where UserId="+calendarBean.getUserId()+" AND CAST(AppointmentDate AS DATE)= '"+calendarBean.getAppointmentDate()+"' AND TreatmentName IS NULL;";
		
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(sql);
			status = ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return status;
	}
	
	public ResultSet getAllTherapistCertifications(CalendarBean calendarBean) {
		ResultSet rs = null;
		
		loadDriver(dbDriver);
		Connection con = getConnection();
		
		
		String sql = "SELECT usercertifications.*, certifications.CertificationName, users.FirstName\n" + 
				"FROM usercertifications\n" + 
				"inner join certifications on usercertifications.CertificationId = certifications.CertificationId\n" + 
				"inner join users on usercertifications.UserId = users.UserId\n" +
				"ORDER BY users.UserId, certifications.CertificationId";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;		
	}
	
	public boolean addUserCert(CalendarBean calendarBean)
	{
		boolean status = false;
		
		
		loadDriver(dbDriver);
		Connection con = getConnection();
		
		//The Insert statement for the Database
		String sql = "INSERT INTO usercertifications (UserId, CertificationId) values ("+calendarBean.getUserId()+","+calendarBean.getCertificationId()+");";
		PreparedStatement ps;
		
		try {
			ps = con.prepareStatement(sql);
			status = ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return status;
	}
	
	public boolean removeUserCert(CalendarBean calendarBean)
	{
		boolean status = false;
		
		
		loadDriver(dbDriver);
		Connection con = getConnection();
		
		//The Remove statement for the Database
		String sql = "DELETE FROM usercertifications WHERE UserCertificationId = "+calendarBean.getUserCertificationId()+";";
		PreparedStatement ps;
		
		try {
			ps = con.prepareStatement(sql);
			status = ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return status;
	}
	
	public boolean addUser(CalendarBean calendarBean)
	{
		boolean status = false;
		
		
		loadDriver(dbDriver);
		Connection con = getConnection();
		
		//The Insert statement for the Database
		String sql = "INSERT INTO users (`FirstName`, `UserName`, `Password`, `Role`)\n" + 
				"SELECT * FROM (SELECT '"+calendarBean.getFirstName()+"' AS FirstName, '"+calendarBean.getUserName()+"' AS UserName, '"+calendarBean.getPassword()+"' AS Password,'"+calendarBean.getRole()+"' AS Role) AS temp\n" + 
				"WHERE NOT EXISTS (\n" + 
				"	SELECT FirstName FROM users WHERE FirstName = '"+calendarBean.getFirstName()+"'\n" + 
				") LIMIT 1;";
		PreparedStatement ps;
		
		try {
			ps = con.prepareStatement(sql);
			status = ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return status;
	}
	
	public ResultSet getUserInfo(CalendarBean calendarBean) {
		ResultSet rs = null;
		
		loadDriver(dbDriver);
		Connection con = getConnection();
		
		
		String sql = "SELECT * FROM users WHERE FirstName = '"+calendarBean.getFirstName()+"';";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;		
	}
	
	public boolean addSchedule(CalendarBean calendarBean)
	{
		boolean status = false;
		
		
		loadDriver(dbDriver);
		Connection con = getConnection();
		
		//The Insert statement for the Database
		String sql = "INSERT INTO defaultschedules (`UserId`, `DayOfWeek`)\n" + 
				"	SELECT * FROM (SELECT '"+calendarBean.getUserId()+"' AS UserId, '"+calendarBean.getDayOfWeek()+"' AS DayOfWeek) AS temp\n" + 
				"WHERE NOT EXISTS (\n" + 
				"    SELECT UserId, DayOfWeek FROM defaultschedules WHERE UserId = '"+calendarBean.getUserId()+"' AND DayOfWeek = '"+calendarBean.getDayOfWeek()+"'\n" + 
				") LIMIT 1;";
		PreparedStatement ps;
		
		try {
			ps = con.prepareStatement(sql);
			status = ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return status;
	}
	
	public boolean updateUser(CalendarBean calendarBean) {
		boolean status = false;		
		
		loadDriver(dbDriver);
		Connection con = getConnection();
		
		String sql = "UPDATE users SET FirstName='"+calendarBean.getFirstName()+"', UserName='"+calendarBean.getUserName()+"', Password='"+calendarBean.getPassword()+"', Role='"+calendarBean.getRole()+"' WHERE UserId='"+calendarBean.getUserId()+"'";
		
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(sql);
			status = ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return status;
	}
	
	public boolean removeUser(CalendarBean calendarBean)
	{
		boolean status = false;		
		
		loadDriver(dbDriver);
		Connection con = getConnection();
		
		//The Remove statement for the Database
		String sql = "DELETE FROM users WHERE userId = "+calendarBean.getUserId()+";";
		PreparedStatement ps;
		
		try {
			ps = con.prepareStatement(sql);
			status = ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return status;
	}
	
	public boolean removeDefaultSchedule(CalendarBean calendarBean)
	{
		boolean status = false;		
		
		loadDriver(dbDriver);
		Connection con = getConnection();
		
		//The Remove statement for the Database
		String sql = "DELETE FROM defaultschedules WHERE userId = "+calendarBean.getUserId()+";";
		PreparedStatement ps;
		
		try {
			ps = con.prepareStatement(sql);
			status = ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return status;
	}
	
	public boolean updateDefaultSchedule(CalendarBean calendarBean) {
		boolean status = false;		
		
		loadDriver(dbDriver);
		Connection con = getConnection();
		
		String sql = "UPDATE defaultschedules SET StartHour = "+calendarBean.getStartHour()+", EndHour = "+calendarBean.getEndHour()+" WHERE ScheduleId='"+calendarBean.getScheduleId()+"';";
		
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(sql);
			status = ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return status;
	}
			
	}

