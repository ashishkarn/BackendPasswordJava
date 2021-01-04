package Main;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;  
import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/signuppage")
public class SignUpPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public SignUpPage() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//check if the user is already logged
		//if not then send the signuppage.jsp
		//if logged, redirect to /profil
		if(AuthInfo.isLogged(request)) {
			response.setContentType("text/html");
			PrintWriter pw=response.getWriter();
			pw.println("<html><body>");  
			pw.println("<p>You are already Logged in Click this button to see your profile.</p>");  
			pw.println("<br><form action='/EcjPractical/profile' method='post'><input type='submit' value='submit'></form>");  
			pw.println("</body></html>");  
			pw.close();
			return;
		}
		request.setAttribute("message", "");
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("SignUpPage.jsp");
		requestDispatcher.forward(request, response); 
	}

}
