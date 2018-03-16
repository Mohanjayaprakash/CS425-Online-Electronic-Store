

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AddToCart
 */
@WebServlet("/AddToCart")
public class AddToCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddToCart() {
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
		System.out.println("Inside the cart page");
		//System.out.println((String)(request.getParameter("P101")));
		String[] checker = request.getParameterValues("addcart");
		String[] store_location = request.getParameterValues("display_under");
		ArrayList<String> stores = new ArrayList<String>();
		if(checker != null) {
			for(String x:checker)
				System.out.println(x);
			for(int i=0;i<store_location.length;i++) {
				System.out.println(store_location[i]);
				if(store_location[i]!= null && store_location[i] != "")
					stores.add(store_location[i]);
			}
			if(stores.size() != 0) {
				/*for(int i=0;i<store_location.length;i++) {
					System.out.println(store_location[i]);
					if(store_location[i]!= null && store_location[i] != "")
						stores.add(store_location[i]); }*/
				
				
				int k = 0;
				while(k<stores.size()) {
					System.out.println(stores.get(k));
					k++;
				}
			
			PrintWriter out = response.getWriter();
			HashMap<String, Inventory> inventory_map = MySQLDataStoreUtilities.getInventory();
	   		out.println("<!doctype html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
			out.println("<title>All Products we offer at JC Penny</title>");    				
			out.println("</head>");
			out.println("<form method=\"post\" action=\"checkedproduct\">");
			out.println("<h3>Please click the button if you wish to checkout below items</h3>");
			HashMap<String, Product> product_map = MySQLDataStoreUtilities.getProducts();
			HttpSession session=request.getSession();
			String checkout = (String) request.getSession().getAttribute("currentUser");
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			String checkout_id = checkout+timestamp;
			session.setAttribute("checkoutid",checkout_id); 
			for(int j=0;j<stores.size();j++) {
				out.println("<h4>Product Name :"+product_map.get(checker[j]).getProduct_name()+", Price :"+inventory_map.get(checker[j]+"_"+stores.get(j)).getPrice()+"</h4>");
				MySQLDataStoreUtilities.update_checkout(checkout_id, checker[j], stores.get(j));
				 
				
				
			}
			out.println("carddetails:<input type=\"text\" name=\"carddetails\"><br><br>");
			
			out.println("<input type=\"submit\" value=\"checkout\">");
			out.println("");
			
			out.println("</form>");
			out.println("</body>");
			out.println("</html>");
			}
			else {
				System.out.println("its null");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/ShowProducts");
				dispatcher.forward(request,response);
			}
		}
		else {
			System.out.println("its null");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/ShowProducts");
			dispatcher.forward(request,response);
		}
	}

}
