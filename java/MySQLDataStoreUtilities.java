import java.sql.*;  
import java.util.*;
import com.mysql.*;

public class MySQLDataStoreUtilities {
	
	public static Connection conn = null;
	
	public static int getConnection(){
		try{			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/retail","root","Bantai@93");
			return 1;
		}
		catch(Exception e){
			return 0;
		}
	}
	
	public static boolean insertUser(User user){
		boolean bool = false;
		try{
			if(getConnection() == 1){
				String insertUser = "INSERT INTO CUSTOMER(cust_id,firstName,lastName,email,password,street,city,zipcode,phonenumber,Datejoined,usertype) "+ "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
				PreparedStatement pst = conn.prepareStatement(insertUser);
				pst.setString(1,user.getCust_id());
				pst.setString(2,user.getFirstName());
				pst.setString(3,user.getLastName());
				pst.setString(4,user.getEmail());
				pst.setString(5,user.getPassword());
				pst.setString(6,user.getStreet());
				pst.setString(7,user.getCity());
				pst.setString(8,user.getZipcode());
				pst.setString(9,user.getPhoneNumber());
				pst.setString(10,user.getDateJoined());
				pst.setString(11,user.getUserType());
				pst.executeUpdate();	

				pst.close();
				conn.close();	
				bool = true;
			}		
		}
		catch(Exception e){
			e.printStackTrace();
		}	
		return bool;
	}
	
		public static HashMap<String, User> getUsers(){
		try{
			if(getConnection() == 1){
				HashMap<String, User> users=new HashMap<String, User>();
				String selectUserQuery ="select * from customer";
				PreparedStatement pst = conn.prepareStatement(selectUserQuery);
				ResultSet rs = pst.executeQuery();
				
				while(rs.next()){
					User user = new User();	
					user.setCust_id(rs.getString("cust_id"));				
					user.setFirstName(rs.getString("firstName"));
					user.setLastName(rs.getString("lastName"));
					user.setEmail(rs.getString("email"));
					user.setPassword(rs.getString("password"));
					user.setUserType(rs.getString("userType"));
					/*user.setStreet(rs.getString("street"));
					user.setCity(rs.getString("city"));
					user.setState(rs.getString("state"));
					user.setZipcode(rs.getString("zipcode"));
					user.setPhoneNumber(rs.getString("phonenumber"));
					user.setDateJoined(rs.getString("datejoined"));*/
					
					users.put(user.getCust_id(),user);
				}
				
				pst.close();
				conn.close();
				return users;
				
			}
			else{
				return null;
			}			
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}		
	}
		
		
		public static String check_inventory(String currentuser) {
			System.out.println("current user inside the check_inventory"+currentuser);
			String store_id = null;
			try {
				if(getConnection() == 1) {
					System.out.println("inside the check_inventory getconnection method");
					String getstoreid = "select store_id from worksfor where emp_id='"+currentuser+"'";
					PreparedStatement pst = conn.prepareStatement(getstoreid);
					ResultSet result = pst.executeQuery();	
					result.next();
					store_id = result.getString("store_id");
					System.out.println("store_id inside check_inventory is count 1 "+store_id);
					pst.close();
					conn.close();
					System.out.println("store_id inside check_inventory is count2 "+store_id);
					return store_id;
				}
			}
			catch(Exception e){
				e.printStackTrace();
				System.out.println("error is :"+e);
			}
			return store_id;
		}
		
		public static int check_inventory_product_quantity(String prod_id, String store_id) {
			int quantity = -1;
			try {
				if(getConnection() == 1) {
					String check_inventory = "select sum(quantity) as sum from inventory where store_id='"+store_id+"'";
					
					PreparedStatement pst = conn.prepareStatement(check_inventory);
					ResultSet result = pst.executeQuery();	
					result.next();
					quantity = result.getInt("sum");
					System.out.println("sum of quantity in the inventory is :"+quantity);
					pst.close();
					conn.close();
					return quantity;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return quantity;
		}
		
		public static int get_store_product_quantity(String store_id) {
			int store_quantity = -1;
			try {
				if(getConnection() == 1) {
					String check_inventory = "select storage_capacity from store where store_id = '"+store_id+"'";
					PreparedStatement pst = conn.prepareStatement(check_inventory);
					ResultSet result = pst.executeQuery();
					result.next();
					store_quantity = result.getInt("storage_capacity");
					System.out.println("Max store quantity is :"+store_quantity);
					pst.close();
					conn.close();
					return store_quantity;
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			return store_quantity;
		}
		
	public static boolean insertProduct(Product product,String store_id){
			boolean bool = false;
			try{
				HashMap<String, Product> prod = getProducts();
				System.out.println("no of products returned"+prod.size());
				int quantity = check_inventory_product_quantity(product.getProduct_id(),store_id);
				int store_quantity = get_store_product_quantity(store_id);
				if(getConnection() == 1){
					
					//int quantity = check_inventory_product_quantity(product.getProduct_id(),store_id);
					//int store_quantity = get_store_product_quantity(store_id);
					System.out.println("inventory_quantity:"+quantity+"  , store_quantity: "+store_quantity);
					if((quantity+product.getQuantity())<=store_quantity) {
						if(prod.get(product.getProduct_id()) == null) {
							String insertProducts = "INSERT INTO product(ProductID,UPC,BrandName,VendorName,ProductName,Description) "+ "VALUES (?,?,?,?,?,?)";
							PreparedStatement pst = conn.prepareStatement(insertProducts);
							pst.setString(1,product.getProduct_id());
							pst.setString(2,product.getUpc_code());
							pst.setString(3,product.getBrand_name());
							pst.setString(4,product.getVendor_name());
							pst.setString(5,product.getProduct_name());
							pst.setString(6,product.getDescription());
							pst.executeUpdate();	
						}
						///Update the inventory table..////////////
						
						String update_inventory = "insert into inventory(prod_id,store_id,quantity,price)"+"values (?,?,?,?)";
						PreparedStatement pst = conn.prepareStatement(update_inventory);
						
						pst.setString(1, product.getProduct_id());
						pst.setString(2, store_id);
						pst.setInt(3, product.getQuantity());
						pst.setString(4, product.getPrice());
						pst.executeUpdate();	
						
						pst.close();
						conn.close();	
						bool = true;	
					}
					else {
						System.out.println("quantity exceeded ");
						bool = false;
					}
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}	
			return bool;
			
		}
	
	
		public static boolean deleteInventory(String store_id, String prod_id) {
			boolean bool = false;
			try{
				if(getConnection() == 1){
					
					String delete_stat = "delete from inventory where store_id = '"+store_id+"' and prod_id ='"+prod_id+"'";
					PreparedStatement pst = conn.prepareStatement(delete_stat);
					pst.executeUpdate();
					pst.close();
					conn.close();
					bool = true;
				}
				else{
					
				}			
			}
			catch(Exception e){
				e.printStackTrace();
			}	
			return bool;
		}
		
		public static boolean updateInventory(String store_id, String prod_id,int quantity,String new_price) {
			boolean bool = false;
			try{
				int inventory_quantity = check_inventory_product_quantity(prod_id,store_id);
				int store_quantity = get_store_product_quantity(store_id);
				if(getConnection() == 1){
					
					//int inventory_quantity = check_inventory_product_quantity(prod_id,store_id);
					//int store_quantity = get_store_product_quantity(store_id);
					System.out.println("inventory_quantity:"+inventory_quantity+"  , store_quantity: "+store_quantity);
					if((quantity+inventory_quantity)<=store_quantity) {
						String modify_inventory ="update inventory set price = '"+new_price+"', quantity = '"+quantity+"' where store_id ='"+store_id+"' and prod_id ='"+prod_id+"'" ;
						PreparedStatement pst = conn.prepareStatement(modify_inventory);
						pst.executeUpdate();
						pst.close();
						conn.close();
						bool = true;
					}
					else {
						bool = false;
						System.out.println("quantity exceeded");
					}
					
					
				}
				else{
					bool = false;
					System.out.println("Connection problem");
				}			
			}
			catch(Exception e){
				e.printStackTrace();
				
			}	
			return bool;
		}
		
		
		public static HashMap<String, Product> getProducts(){
			try{
				if(getConnection() == 1){
					HashMap<String, Product> users=new HashMap<String, Product>();
					String selectUserQuery ="select * from Product";
					PreparedStatement pst = conn.prepareStatement(selectUserQuery);
					ResultSet rs = pst.executeQuery();
					
					while(rs.next()){
						Product prod = new Product();	
						prod.setProduct_id(rs.getString("ProductID"));				
						prod.setBrand_name(rs.getString("BrandName"));
						prod.setDescription(rs.getString("Description"));
						prod.setProduct_name(rs.getString("ProductName"));
						prod.setVendor_name(rs.getString("VendorName"));
						prod.setUpc_code(rs.getString("UPC"));
						users.put(prod.getProduct_id(),prod);
					}
					System.out.println("no of products returned in get product function"+users.size());
					pst.close();
					conn.close();
					return users;
					
				}
				else{
					return null;
				}			
			}
			catch(Exception e){
				e.printStackTrace();
				System.out.println(e);
				return null;
			}		
		}
	
		public static HashMap<String, Inventory> getInventory(){
			try{
				if(getConnection() == 1){
					HashMap<String, Inventory> users=new HashMap<String, Inventory>();
					String selectUserQuery ="select * from Inventory";
					PreparedStatement pst = conn.prepareStatement(selectUserQuery);
					ResultSet rs = pst.executeQuery();
					
					while(rs.next()){
						Inventory user = new Inventory();	
						user.setProd_id(rs.getString("prod_id"));				
						user.setStore_id(rs.getString("store_id"));
						user.setQuantity(Integer.parseInt(rs.getString("quantity")));
						user.setPrice(rs.getString("price"));
						users.put(user.getProd_id()+"_"+user.getStore_id(),user);
					}
					
					pst.close();
					conn.close();
					return users;
					
				}
				else{
					return null;
				}			
			}
			catch(Exception e){
				e.printStackTrace();
				return null;
			}		
		}	
		
		public static HashMap<Integer, Store> getStore(){
			try{
				if(getConnection() == 1){
					HashMap<Integer, Store> users=new HashMap<Integer, Store>();
					String selectUserQuery ="select * from Store";
					PreparedStatement pst = conn.prepareStatement(selectUserQuery);
					ResultSet rs = pst.executeQuery();
					int count = 1;
					while(rs.next()){
						Store st = new Store();	
						st.setStore_id(rs.getString("store_id"));			
						st.setStreet(rs.getString("street"));
						st.setCity(rs.getString("city"));
						st.setPhonenumber(rs.getString("phonenumber"));
						st.setStorage_capacity(rs.getInt("storage_capacity"));
						users.put(count,st);
						count++;
					}
					System.out.println("no of products returned in get products function"+users.size());
					pst.close();
					conn.close();
					return users;
					
				}
				else{
					return null;
				}			
			}
			catch(Exception e){
				e.printStackTrace();
				System.out.println(e);
				return null;
			}		
		}
		
		public static HashMap<Integer, Product> getProduct(){
			try{
				if(getConnection() == 1){
					HashMap<Integer, Product> users=new HashMap<Integer, Product>();
					String selectUserQuery ="select * from Product";
					PreparedStatement pst = conn.prepareStatement(selectUserQuery);
					ResultSet rs = pst.executeQuery();
					int count = 1;
					while(rs.next()){
						Product prod = new Product();	
						prod.setProduct_id(rs.getString("ProductID"));				
						prod.setBrand_name(rs.getString("BrandName"));
						prod.setDescription(rs.getString("Description"));
						prod.setProduct_name(rs.getString("ProductName"));
						prod.setVendor_name(rs.getString("VendorName"));
						prod.setUpc_code(rs.getString("UPC"));
						users.put(count,prod);
						count++;
					}
					System.out.println("no of products returned in get product function"+users.size());
					pst.close();
					conn.close();
					return users;
					
				}
				else{
					return null;
				}			
			}
			catch(Exception e){
				e.printStackTrace();
				System.out.println(e);
				return null;
			}		
		}
		public static boolean update_checkout(String checkout_id,String prod_id,String store_id) {
			boolean bool = false;
			try{
				if(getConnection() == 1){
					String insertcheck = "INSERT INTO checkout(checkout_id,prod_id,store_id,quantity) "+ "VALUES (?,?,?,?)";
					PreparedStatement pst = conn.prepareStatement(insertcheck);
					pst.setString(1,checkout_id);
					pst.setString(2,prod_id);
					pst.setString(3,store_id);
					pst.setString(4, "1");
					pst.executeUpdate();	

					pst.close();
					conn.close();	
					bool = true;
				}		
			}
			catch(Exception e){
				e.printStackTrace();
			}	
			return bool;
		}
		
		public static boolean updateinventoryoncheckout(String store_id, String prod_id) {
			boolean bool = false;
			try{
				
				if(getConnection() == 1){
					
						String modify_inventory ="update inventory set quantity = quantity-1 where store_id ='"+store_id+"' and prod_id ='"+prod_id+"'";
						PreparedStatement pst = conn.prepareStatement(modify_inventory);
						pst.executeUpdate();
						pst.close();
						conn.close();
						bool = true;
					}
	
				else{
					bool = false;
					System.out.println("Connection problem");
				}			
			}
			catch(Exception e){
				e.printStackTrace();
				
			}	
			return bool;
		}
		public static HashMap<Integer, Inventory> get_checkout(String checkout_id) {
			
			try{
				if(getConnection() == 1){
					HashMap<Integer, Inventory> users=new HashMap<Integer, Inventory>();
					String insertcheck = "select * from checkout where checkout_id='"+checkout_id+"'";
					
					PreparedStatement pst = conn.prepareStatement(insertcheck);
					ResultSet rs = pst.executeQuery();
					int count = 1;
					while(rs.next()){
						Inventory st = new Inventory();	
						st.setStore_id(rs.getString("store_id"));			
						st.setProd_id(rs.getString("prod_id"));
						users.put(count,st);
						count++;
					}
					System.out.println("no of products returned in get checkout cart function"+users.size());
					pst.close();
					conn.close();
					return users;
				}		
			}
			catch(Exception e){
				e.printStackTrace();
			}	
			return null;
		}
		
		
		
 }