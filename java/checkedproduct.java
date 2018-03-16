

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class checkedproduct
 */
@WebServlet("/checkedproduct")
public class checkedproduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public checkedproduct() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("You have purchased all the items");
		String checkout_id = (String) request.getSession().getAttribute("checkoutid");
		System.out.println("checkout_id is from session attribute: "+checkout_id);
		HashMap<Integer, Inventory> users = MySQLDataStoreUtilities.get_checkout(checkout_id);
		
		for(int i=1;i<=users.size();i++)
			MySQLDataStoreUtilities.updateinventoryoncheckout(users.get(i).getStore_id(),users.get(i).getProd_id());
		System.out.println("reduced quantitiy by one");
		PrintWriter out = response.getWriter();

		out.println("<!doctype html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
		out.println("<title>All Products we offer at JC Penny</title>");    				
		out.println("</head>");
		out.println("<form method=\"post\" action=\"ShowProducts\">");
		out.println("<h3>Please click the button if you wish to purchase more product</h3>");
		out.println("<input type=\"submit\" value=\"Buy More\">");
		out.println("</form>");
		out.println("<form method=\"post\" action=\"Logout\">");
		
		out.println("<button style=\"white-space: nowrap;position: Absolute;left: 50%;margin-left: 500px;background-color:#F7DC6F;border: outline colour:black;color: Black;padding: 15px 32px;text-align: center;font-size: 16px;margin: 50px 500px;cursor: pointer\">Sign out</button>\n" + 
				"		</form>");
		
		
		out.println("</body>");
		out.println("</html>");
	}

}
