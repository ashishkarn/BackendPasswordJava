package Main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Instant;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
/*
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
*/
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class AuthInfo {
	//static ArrayList<String> loggedUsers = new  ArrayList<String>();
	static Hashtable<Integer, String> logTokens = new Hashtable<Integer, String>();
	
	public static boolean checkUserExists(String username, String email) {
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/test","root","5123");  
			
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery("select * from users");  
			
			while(rs.next()){
				if(username.equals(rs.getString(1))) {
					System.out.println("Username exists");
					return true;
				}
				if(email.equals(rs.getString(4))){
					System.out.println("Email exists");
					return true;
				}
			}
			con.close();  
		}catch(Exception e){ System.out.println(e);}
		System.out.println("User doesnt exist");
		return false;
	}
	
	public static void createuser(String name, String username, String password, String email) {
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/test","root","5123");  
			
			String s="INSERT INTO users (username, pass, fname, email, regDate, lastOnline) "
					+ "VALUES (?,?,?,?,?,?);";
			
			PreparedStatement stmt=con.prepareStatement(s);  
			stmt.setString(1,username);
			String hashedpassword=hashPassword(password);
			stmt.setString(2,hashedpassword);
			stmt.setString(3,name);
			stmt.setString(4,email);
			stmt.setInt(5,(int)Instant.now().getEpochSecond());
			stmt.setInt(6,0);
			//String s1=","+regDate+""+,%s)"
			stmt.executeUpdate();  
			System.out.println("User added");
			con.close();  
		}catch(Exception e){ System.out.println(e);}
	}
	
	private static String hashPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

	public static boolean isLogged(HttpServletRequest request) {
		Cookie ck[]=request.getCookies(); 
		String username="";
		int tokenValue=-1;
		if(ck!=null) {
			for(int i=0; i<ck.length;i++) {
				if(ck[i].getName().equals("token")) {
					tokenValue=Integer.parseInt(ck[i].getValue());
					//System.out.println("User already logged:"+tokenValue);
				}else if(ck[i].getName().equals("username")) {
					username=ck[i].getValue();
				}
			}
			for (Map.Entry<Integer, String> e : logTokens.entrySet()) { 
	            if(e.getKey()==tokenValue && e.getValue().equals(username)) {}
	            return true;
	    	} 
		}
		return false;
	}

	public static boolean authUser(String username, String password) {
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/test","root","5123");  
					
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM USERS WHERE username=\""+username+"\";");
			if(rs.next()) {
				if(BCrypt.checkpw(password, rs.getString(2))) {
					return true;
				}
			}
			con.close();  
		}catch(Exception e){ System.out.println(e);}
		return false;
	}

	public static int generateRandomToken() {
		Random rand = new Random();
		int token = rand.nextInt(10000000);
		for(Map.Entry user:logTokens.entrySet()){  
			if(token==(Integer)user.getKey()) {
				return generateRandomToken();
			}
		}  
		return token;
	}
	
	public static int getCookieToken(HttpServletRequest request) {
		Cookie ck[]=request.getCookies(); 
		if(ck!=null) {
			for(int i=0; i<ck.length;i++) {
				if(ck[i].getName().equals("token")) {
					return Integer.parseInt(ck[i].getValue());
				}
			}
		}
		return -1;
	}
	
	public static String getCookieUsername(HttpServletRequest request) {
		Cookie ck[]=request.getCookies(); 
		if(ck!=null) {
			for(int i=0; i<ck.length;i++) {
				if(ck[i].getName().equals("username")) {
					return ck[i].getValue();
				}
			}
		}
		return null;
	}
	
	public static int getCookieLoginTime(HttpServletRequest request) {
		Cookie ck[]=request.getCookies(); 
		if(ck!=null) {
			for(int i=0; i<ck.length;i++) {
				if(ck[i].getName().equals("loginTime")) {
					return Integer.parseInt(ck[i].getValue());
				}
			}
		}
		return -1;
	}

	public static String getEmailByUsername(String username) {
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/test","root","5123");  
			
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery("SELECT * FROM USERS WHERE username=\""+username+"\";");  
				
				while(rs.next()){
					return rs.getString(4);
				}
				con.close();  
			}catch(Exception e){ System.out.println(e);}
			return null;
		}

	public static int getRegDateByUsername(String username) {
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/test","root","5123");  
			
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery("SELECT * FROM USERS WHERE username=\""+username+"\";");  
				
				while(rs.next()){
					return rs.getInt(5);
				}
				con.close();  
			}catch(Exception e){ System.out.println(e);}
			return -1;
	}

	public static int getLastOnlineByUsername(String username) {
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/test","root","5123");  
			
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery("SELECT * FROM USERS WHERE username=\""+username+"\";");  
				
				while(rs.next()){
					return rs.getInt(6);
				}
				con.close();  
			}catch(Exception e){ System.out.println(e);}
			return -1;
	}

	public static void updateLastOnline(HttpServletRequest request) {
		String username=getCookieUsername(request);
		int lastOnlineCookie=Integer.parseInt(getLastOnlineByCookie(request));
		
		//updatedb
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/test","root","5123");  
			
			PreparedStatement stmt=con.prepareStatement("update users set lastOnline=? where username=?");  
			stmt.setInt(1, lastOnlineCookie);
			stmt.setString(2, username);
			stmt.executeUpdate();  
			con.close();  
		}catch(Exception e){ System.out.println(e);}
	}

	private static String getLastOnlineByCookie(HttpServletRequest request) {
		Cookie ck[]=request.getCookies(); 
		if(ck!=null) {
			for(int i=0; i<ck.length;i++) {
				if(ck[i].getName().equals("loginTime")) {
					return ck[i].getValue();
				}
			}
		}
		return null;
	}

	public static void updatePassword(HttpServletRequest request, String password) {
		String username=getCookieUsername(request);
		
		//updatedb
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/test","root","5123");  
			
			PreparedStatement stmt=con.prepareStatement("update users set pass=? where username=?");  
			password=hashPassword(password);
			stmt.setString(1, password);
			stmt.setString(2, username);
			stmt.executeUpdate();  
			con.close();  
		}catch(Exception e){ System.out.println(e);}
	}

	public static boolean emailExists(String email) {
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/test","root","5123");  
			
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery("SELECT * FROM USERS WHERE email=\""+email+"\";");  
				while(rs.next()){
					return true;
				}
				con.close();  
			}catch(Exception e){ System.out.println(e);}
		return false;
	}

	public static void generateAndUpdatePassword(String email) {
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/test","root","5123");  
			
			PreparedStatement stmt=con.prepareStatement("update users set pass=? where email=?");
			String password=generatePassword(20);
			//System.out.println(password);
			String hashpassword=hashPassword(password);
			stmt.setString(1, hashpassword);
			stmt.setString(2, email);
			stmt.executeUpdate();  
			con.close(); 
			sendToEmail(password,email);
		}catch(Exception e){ System.out.println(e);}
		
	}
	
	private static void sendToEmail(String password, String email) {
		/*Properties properties= new Properties();
		properties.put("mail.smtp.auth","true");
		properties.put("mail.smtp.starttls.enable","true");
		properties.put("mail.smtp.host","smtp.gmail.com");
		properties.put("mail.smtp.port","587");
		
		String gEmail=GData.gEmail;
		String gPass=GData.gPass;
		
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(gEmail,gPass);
			}
		});
		
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(gEmail));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
			message.setSubject("Password for ecj");
			message.setText(password);
			Transport.send(message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		try {
			//System.out.println("erasddror");
			Runtime.getRuntime().exec("python a.py "+email+" "+password);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("error");
			e.printStackTrace();
		}
	}

	public static String generatePassword(int n) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "0123456789"
                                    + "abcdefghijklmnopqrstuvxyz"; 
  
        StringBuilder sb = new StringBuilder(n); 
        for (int i = 0; i < n; i++) {    
            int index 
                = (int)(AlphaNumericString.length() 
                        * Math.random()); 
            
            sb.append(AlphaNumericString 
                          .charAt(index)); 
        } 
        return sb.toString(); 
	}
		
}
	

