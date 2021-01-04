package Main;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Logout
 */
@WebServlet("/logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Logout() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//check in loginTime cookie into db
		if(AuthInfo.isLogged(request)) {
			AuthInfo.updateLastOnline(request);
		}
		int token= AuthInfo.getCookieToken(request);
		if(token!=-1) {
			AuthInfo.logTokens.put(token, "");
			AuthInfo.logTokens.remove(token);
		}
		Cookie ck=new Cookie("token","");//deleting value of cookie  
	    ck.setMaxAge(0);//changing the maximum age to 0 seconds  
	    response.addCookie(ck);//adding cookie in the response  
	    
	    Cookie ck2=new Cookie("username","");//deleting value of cookie  
	    ck.setMaxAge(0);//changing the maximum age to 0 seconds  
	    response.addCookie(ck2);//adding cookie in the response  
	    
	    Cookie ck3=new Cookie("loginTime","");//deleting value of cookie  
	    ck3.setMaxAge(0);//changing the maximum age to 0 seconds  
	    response.addCookie(ck3);//adding cookie in the response  
	    System.out.println("Logged out");
	    request.setAttribute("message", "Home Page, Logged Out");
	    RequestDispatcher requestDispatcher = request.getRequestDispatcher("welcome.jsp");
		requestDispatcher.forward(request, response); 
	}

}
