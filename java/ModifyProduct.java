

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ModifyProduct
 */
@WebServlet("/ModifyProduct")
public class ModifyProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyProduct() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String product_id = request.getParameter("product_id");
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		String new_price = request.getParameter("new_price");
		System.out.println("Current user is "+(String) request.getSession().getAttribute("currentUser"));
		String store_id = MySQLDataStoreUtilities.check_inventory((String) (request.getSession().getAttribute("currentUser")));
		System.out.println("Store id for current user is "+store_id);
		boolean bool = MySQLDataStoreUtilities.updateInventory(store_id, product_id, quantity, new_price);
		if(bool) {
			System.out.println("inventory table is updated");
			response.sendRedirect(request.getContextPath()+"/salesmanvalidlogin.html");
		}
		else {
			System.out.println("Oops some problem, couldn't update the table entry");
			response.sendRedirect(request.getContextPath()+"/loginserverdown.html");
		}
	}

}
