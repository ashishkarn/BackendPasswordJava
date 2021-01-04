package Main;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ForgotPass
 */
@WebServlet("/forgotpass")
public class ForgotPass extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ForgotPass() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// check if user with the email exists
		//if not then render forgotpagesubmit with message
		//else update db with new pass and send pass to user email 
		if(AuthInfo.emailExists(request.getParameter("email"))) {
			AuthInfo.generateAndUpdatePassword(request.getParameter("email"));
			request.setAttribute("message", "Password Changed and sent to email");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("LoginPage.jsp");
			requestDispatcher.forward(request, response); 
		}else {
			request.setAttribute("message", "Email doesn't exist. Please signup");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("ForgotPassSubmit.jsp");
			requestDispatcher.forward(request, response); 
		}
	}

}
