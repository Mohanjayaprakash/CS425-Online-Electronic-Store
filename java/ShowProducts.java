

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ShowProducts
 */
@WebServlet("/ShowProducts")
public class ShowProducts extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowProducts() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("i'm in the doget method");
		HashMap<Integer, Product> product_map = MySQLDataStoreUtilities.getProduct();
		HashMap<String, Inventory> inventory_map = MySQLDataStoreUtilities.getInventory();
		HashMap<Integer, Store> store_map = MySQLDataStoreUtilities.getStore();
		PrintWriter out = response.getWriter();

   		out.println("<!doctype html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
		out.println("<title>All Products we offer at JC Penny</title>");    				
		out.println("</head>");
		out.println("<body background=\"https://i2.wp.com/livewallpaperhd.com/wp-content/uploads/2017/05/Plain-White-Background-Wallpaper-Hd.jpg?ssl\\u003d1\" style=\"color:#000099;font-family:courier;font-size:150%;font-weight:bold\">");
		
		out.println("<style>.button {");
		out.println("white-space:nowrap;");
		out.println("position: Absolute;");    
		out.println("Bottom: automatic;");    
		out.println("left: 50%;");
		out.println("margin-left: 500px;");
		out.println("background-color:#F7DC6F;");
		out.println("border: outline color: black;");
		out.println("color: black;");
		out.println("padding: 15px 32px;");
		out.println("text-align: center;");
		out.println("text-decoration: none;");
		out.println("font-size: 16px;");
		out.println("margin: 50px 300px;");
		out.println("cursor: pointer;}");
		out.println("test{");
		out.println("height:1000px;}");
		out.println("</style>");
		out.println("<form method=\"post\" action=\"Logout\">");
		
		out.println("<button style=\"white-space: nowrap;position: Absolute;left: 50%;margin-left: 500px;background-color:#F7DC6F;border: outline colour:black;color: Black;padding: 15px 32px;text-align: center;font-size: 16px;margin: 50px 500px;cursor: pointer\">Sign out</button>\n" + 
				"		</form>");
		
		out.println("<form action=\"AddToCart\"  method=\"post\">");
	
		
		out.println("<p align=\"center\" style=\"color:#660000;font-family:courier;font-size:200%;font-weight:bold\"><u>Products</u> <button class=\"button\" type=\"submit\">Add to Cart</button></p>");
		out.println("<ul>");
		//<input type ="checkbox" name="HP" value="HP Envy x360" style="color:#000099">Sony Playstation 4<br><br>
		
		for(int i=1; i<=product_map.size();i++) {
			Product p = product_map.get(i);
			out.println("<input type =\"checkbox\" name=\"addcart\" value=\""+p.getProduct_id()+"\" style=\"color:#000099\">"+p.getProduct_name()+" ");
			//ArrayList<String> al = new ArrayList<String>();
			out.println("<div class=\"shipping-input-fields\">");
			out.println("<select name=\"display_under\" style=\"width:250px; height:35px\" >");  
			out.println("<option value=\"\">----- Please Select----</option>"); 
			for(int j=1;j<=store_map.size();j++) {
				Store s = store_map.get(j);
				Inventory inv = inventory_map.get(p.getProduct_id()+"_"+s.getStore_id());
				
				
				
				
				if(inv != null && inv.getQuantity()>0) {
					out.println("<option value=\""+s.getStore_id()+"\">"+s.getCity()+" "+inv.getPrice()+"</option>");
					//al.add(s.getCity());
					System.out.println(i+") "+"product : "+p.getProduct_name()+p.getBrand_name()+p.getDescription()+","+"store : "+s.getStreet()+s.getCity()+
						"price : "+	inv.getPrice());
			
				
			    
					//out.println("<input type =\"checkbox\" name=\"HP\" value=\"HP Envy x360\" style=\"color:#000099\">"+p.getProduct_name()+"<br><br>");
					
				}
			}
			
			 
			//out.println("<option value=\"TV\">TV</option>"); 
			//out.println("<option value=\"Laptop\">Laptop</option>"); 
			//out.println("<option value=\"Tablets\">Tablets</option>");   
			out.println("</select>"); 
			out.println("</div>");
			out.println("<br>");
		}
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");
	}

	/**
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
