

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
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
		
		try{
			
			String user_id_email = request.getParameter("user_id");
			String password = request.getParameter("password");			
			System.out.println("hello I found login .java file yipee");			
			/*if(user_id.equals("admin"))	{						
				
			    if(password.equals("admin")){
					HttpSession session=request.getSession();  
					session.setAttribute("currentUser",user_id);
					response.sendRedirect(request.getContextPath()+"/adminhomepage.html");
				}
				else{
					response.sendRedirect(request.getContextPath()+"/invalidlogin.html");
				}				
			}
			else{*/
				
					HashMap<String, User> userMap = MySQLDataStoreUtilities.getUsers();					
					if(userMap != null){
					  
						User user  = userMap.get(user_id_email.split("@")[0]);					
						if(user != null){				
							HttpSession session=request.getSession();  					
							if(user.getPassword().equals(password)){
								if(user.getUserType().equalsIgnoreCase("Customer")){
									session.setAttribute("currentUser",user.getCust_id());  
									session.setAttribute("userTypeInfo",user.getUserType());
									//response.sendRedirect(request.getContextPath()+"/validlogin.html");
									RequestDispatcher dispatcher = request.getRequestDispatcher("/ShowProducts");
									dispatcher.forward(request,response);
								}
								else if(user.getUserType().equalsIgnoreCase("Employee")){
									session.setAttribute("currentUser",user.getCust_id());  
									session.setAttribute("userTypeInfo",user.getUserType());
									response.sendRedirect(request.getContextPath()+"/salesmanvalidlogin.html");
								}
							}
							else{
								response.sendRedirect(request.getContextPath()+"/invalidlogin.html");
							}
						}
						else{
							response.sendRedirect(request.getContextPath()+"/invalidlogin.html");
						}
						
					}
					else{
						response.sendRedirect(request.getContextPath()+"/loginserverdown.html");
					}
		//	}			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
