package Main;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ChangePass
 */
@WebServlet("/changepass")
public class ChangePass extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangePass() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(AuthInfo.isLogged(request)) {
			AuthInfo.updatePassword(request, request.getParameter("password"));
			response.setContentType("text/html");
			PrintWriter pw=response.getWriter();
			pw.println("<html><body>");  
			pw.println("<p>Password Changed</p>");  
			pw.println("<br><form action='/EcjPractical/logout' method='post'><input type='submit' value='Logout'></form>");  
			pw.println("</body></html>");  
			pw.close();
		}
		else {
			request.setAttribute("message", "You have to login first");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("LoginPage.jsp");
			requestDispatcher.forward(request, response); 
		}
	}

}
