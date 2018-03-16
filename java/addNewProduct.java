

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.Session;

/**
 * Servlet implementation class addNewProduct
 */
@WebServlet("/addNewProduct")
public class addNewProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addNewProduct() {
        super();
        // TODO Auto-generated constructor stub
    }

	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String product_id = request.getParameter("product_id");
		String product_name = request.getParameter("product_name");
		String brand_name = request.getParameter("brand_name");
		String product_description = request.getParameter("product_description");
		String price_amt = request.getParameter("price_amt");
		String p_upccode = request.getParameter("p_upccode");
		String vendor_name = request.getParameter("vendor_name");
		String p_quantity = request.getParameter("p_quantity");
		Product product = new Product();
		product.setProduct_id(product_id);
		product.setProduct_name(product_name);
		product.setBrand_name(brand_name);
		product.setDescription(product_description);
		product.setPrice(price_amt);
		product.setUpc_code(p_upccode);
		product.setVendor_name(vendor_name);
		product.setQuantity(Integer.parseInt(p_quantity));
		
		String store_id = MySQLDataStoreUtilities.check_inventory((String) (request.getSession().getAttribute("currentUser")));
		System.out.println("Store id for Current user is : "+store_id);
		if(MySQLDataStoreUtilities.insertProduct(product,store_id)) {
			System.out.println(" successfully added new entry into inventory and products ");
			response.sendRedirect(request.getContextPath()+"/salesmanvalidlogin.html");
		}else {
			System.out.println("coulnd't add the new entry, some problem encountered");
			response.sendRedirect(request.getContextPath()+"/loginserverdown.html");
		}
	}

}
