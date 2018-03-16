

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DeleteProduct
 */
@WebServlet("/DeleteProduct")
public class DeleteProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteProduct() {
        super();
        // TODO Auto-generated constructor stub
    }



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String product_id = request.getParameter("product_id");
		
		String store_id = MySQLDataStoreUtilities.check_inventory((String) request.getSession().getAttribute("currentUser"));
		boolean bool = MySQLDataStoreUtilities.deleteInventory(store_id, product_id);
		if(bool) {
			System.out.println("deleted");
			response.sendRedirect(request.getContextPath()+"/salesmanvalidlogin.html");
		}
		else {
			System.out.println("some problem, couldn't be deleted");
			response.sendRedirect(request.getContextPath()+"/loginserverdown.html");
		}
	}

}
