package Main;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/signup")
public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public SignUp() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//validate userinfo, if correct format
		//then check database, else send signuppage.jsp with args
		//corroborate with database user info, if userfound send signuppage.jsp with args
		//create new entry in database and send signuppage with success with link to /loginpage.jsp
		String name= request.getParameter("name");
		String username= request.getParameter("username");
		String password= request.getParameter("password");
		String email= request.getParameter("email");
		
		if(!validateInput(name, password, username, email)) {
			//wrong input
			String attribute="Username must be lowercase including numbers and contain 5 - 12 characters, Email must be a valid address, e.g. me@mydomain.com, Password Minimum eight characters, at least one letter and one number.";
			request.setAttribute("message",attribute);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("SignUpPage.jsp");
			requestDispatcher.forward(request, response); 
			return;
		}
		if(AuthInfo.checkUserExists(username, email)) {
			request.setAttribute("message","User already exists, please choose another username or email");
		}else{
			AuthInfo.createuser(name, username, password, email);
			//user created successfully
			request.setAttribute("message","User-created");
		}

		RequestDispatcher requestDispatcher = request.getRequestDispatcher("SignUpPage.jsp");
		requestDispatcher.forward(request, response); 
	}
	
	public boolean validateInput(String name, String pass, String username, String email) {
        String uRegex = "^[A-Za-z0-9]+(?:[ _-][A-Za-z0-9]+)*$";
        String eRegex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
        String pRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        
        Pattern uPattern = Pattern.compile(uRegex); 
        Pattern ePattern = Pattern.compile(eRegex); 
        Pattern pPattern = Pattern.compile(pRegex); 
        
        Matcher uMatch = uPattern.matcher(username); 
        Matcher eMatch = ePattern.matcher(email); 
        Matcher pMatch = pPattern.matcher(pass); 
        
		if(uMatch.matches()) {
			System.out.println("username good");
			if(eMatch.matches()) {
				System.out.println("email good");
				if(pMatch.matches()) {
					System.out.println("pass good");
					return true;
				}
			}
		}
		return false;
	}
}
