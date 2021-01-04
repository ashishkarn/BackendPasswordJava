package Main;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(AuthInfo.isLogged(request)) {
			//redirect profile
			//request.setAttribute("message", "User logged already");
			//RequestDispatcher requestDispatcher = request.getRequestDispatcher("LoginPage.jsp");
			//requestDispatcher.forward(request, response); 
			response.setContentType("text/html");
			PrintWriter pw=response.getWriter();
			pw.println("<html><body>");  
			pw.println("<p>You are already Logged in Click this button to see your profile.</p>");  
			pw.println("<br><form action='/EcjPractical/profile' method='post'><input type='submit' value='submit'></form>");  
			pw.println("</body></html>");  
			pw.close();
			return;
		}
		String username= request.getParameter("username");
		String password= request.getParameter("password");
		boolean auth= AuthInfo.authUser(username,password);
		if(auth) {
			//create auth cookie
			//redirect to /profile
			//update db  last login
			System.out.println("User authorized");
			 
			int token=AuthInfo.generateRandomToken();
			AuthInfo.logTokens.put(token, username);
			
			Cookie tkn=new Cookie("token",Integer.toString(token));
			Cookie usr=new Cookie("username",username);
			int time =(int)Instant.now().getEpochSecond();
			Cookie loginTime=new Cookie("loginTime",Integer.toString(time));	
			response.addCookie(loginTime);
			response.addCookie(tkn);
			response.addCookie(usr);
			
			//redirect to profile
			//request.setAttribute("message", "User authorized");
			//RequestDispatcher requestDispatcher = request.getRequestDispatcher("LoginPage.jsp");
			//requestDispatcher.forward(request, response); 
			response.setContentType("text/html");
			PrintWriter pw=response.getWriter();
			pw.println("<html><body>");  
			pw.println("<p>Congrats. You are Logged in Click this button to go to your Profile.</p>");  
			pw.println("<br><form action='/EcjPractical/profile' method='post'><input type='submit' value='submit'></form>");  
			pw.println("</body></html>");  
			pw.close();
			
		}else {
			//render loginpage with message wrong username pass
			System.out.println("User not auth");
			request.setAttribute("message", "Wrong username/password");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("LoginPage.jsp");
			requestDispatcher.forward(request, response); 
		}
	}

}
