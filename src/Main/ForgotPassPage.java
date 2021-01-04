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
 * Servlet implementation class ForgotPassPage
 */
@WebServlet("/forgotpasspage")
public class ForgotPassPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ForgotPassPage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("ForgotPassSubmit.jsp");
		requestDispatcher.forward(request, response); 
	}

}
