package Main;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Profile
 */
@WebServlet("/profile")
public class Profile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Profile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//check if the user is logged
		//if logged paste the loginTime cookie to database
		//render profile page
		//if not logged then render login page
		//Profile page will have last login, name, logout, and change password option
		if(AuthInfo.isLogged(request)) {
			String username=AuthInfo.getCookieUsername(request);
			String email=AuthInfo.getEmailByUsername(username);
			int regDate=AuthInfo.getRegDateByUsername(username);
			int lastOnline=AuthInfo.getLastOnlineByUsername(username);
			
			LocalDateTime dateTime = LocalDateTime.ofEpochSecond(regDate, 0, ZoneOffset.UTC);
			ZoneId zoneId = ZoneId.of("Asia/Kolkata");
			ZonedDateTime zdt = ZonedDateTime.of(dateTime, zoneId); 
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE,MMMM d,yyyy h:mm,a", Locale.ENGLISH);
			String formattedRegDate = zdt.format(formatter);
			
			LocalDateTime dateTime1 = LocalDateTime.ofEpochSecond(lastOnline, 0, ZoneOffset.UTC);
			ZoneId zoneId1 = ZoneId.of("Asia/Kolkata");
			ZonedDateTime zdt1 = ZonedDateTime.of(dateTime1, zoneId1); 
			DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("EEEE,MMMM d,yyyy h:mm,a", Locale.ENGLISH);
			String formattedLastOnlineDate = zdt1.format(formatter1);

			request.setAttribute("message", "");
			request.setAttribute("username", username);
			request.setAttribute("email", email);
			request.setAttribute("regDate", formattedRegDate);
			
			if(lastOnline!=0) {
				request.setAttribute("lastOnline", formattedLastOnlineDate);
			}else {
				request.setAttribute("lastOnline", "Right Now");
			}
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("ProfilePage.jsp");
			requestDispatcher.forward(request, response); 
		}
		else {
			request.setAttribute("message", "You have to login first");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("LoginPage.jsp");
			requestDispatcher.forward(request, response); 
		}
	}

}
