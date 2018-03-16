

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class NewUser
 */
@WebServlet("/NewUser")
public class NewUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String Fname = request.getParameter("Fname");
		String Lname = request.getParameter("Lname");
		String EmailId = request.getParameter("EmailId");
		String password = request.getParameter("password");
		String street = request.getParameter("street");
		String city = request.getParameter("city");
		//String state = request.getParameter("state");
		String zipcode = request.getParameter("zipcode");
		String phonenumber = request.getParameter("phonenumber");
		String datejoined = request.getParameter("datejoined");
		String usertype = request.getParameter("usertype");
		//if (!Fname.equals("") && !Lname.equals("") && !EmailId.equals("") && !password.equals("") && !street.equals("") && !city.equals("") && 
			//	 !zipcode.equals("") && !phonenumber.equals("") && !datejoined.equals("") && !usertype.equals("")) {
			User user = new User();
			user.setCust_id(EmailId.split("@")[0]);
			user.setFirstName(Fname);
			user.setLastName(Lname);
			user.setEmail(EmailId);
			user.setPassword(password);
			user.setUserType(usertype);
			user.setStreet(street);
			user.setCity(city);
			//user.setState(state);
			user.setZipcode(zipcode);
			user.setPhoneNumber(phonenumber);
			user.setDateJoined(datejoined);
			HashMap<String, User> check = MySQLDataStoreUtilities.getUsers();
			if(check.get(user.getCust_id()) == null) {
				if(MySQLDataStoreUtilities.insertUser(user)) {
					System.out.println("Successfully logged in");
					response.sendRedirect(request.getContextPath()+"/validlogin.html");
				}
				else
					response.sendRedirect(request.getContextPath()+"/invalidlogin.html");
				
			} 
			else {
				System.out.println("User already exist");
			response.sendRedirect(request.getContextPath()+"/useralreadyregistered.html");
		}
		
	}

}
