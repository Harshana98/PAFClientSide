package com;

import java.sql.*;

public class Product
{ //A common method to connect to the DB
	
	private Connection connect()
	{
		Connection con = null;
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			
			//Provide the correct details: DBServer/DBName, username, password
			con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/paf","root", "");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return con;

	}
	
	
public String insertProduct(String review_id)
{
	String output = "";
	try
	{
		 Connection con = connect(); 
		 if (con == null) 
		 {return "Error while connecting to the database for inserting."; } 
		 
		 
		 // create a prepared statement
		 String query = " insert into products (`product_id`,`review_id`)"+ " values (?, ?)"; 
		 PreparedStatement preparedStmt = con.prepareStatement(query); 
		 
		 
		 // binding values 
		 preparedStmt.setInt(1, 0); 
		 preparedStmt.setInt(2, Integer.parseInt(review_id));
		// execute the statement

		preparedStmt.execute();
		
		con.close();
		
		String newItems = readProducts();
		output = "{\"status\":\"success\", \"data\": \"" +
		        newItems + "\"}";
		
	}
	catch (Exception e)
	{
		output = "{\"status\":\"error\", \"data\": \"Error while inserting the item.\"}";
			System.err.println(e.getMessage());
	}
		
	return output;
}


public String readProducts()
{
		String output = "";
		try
		{
			Connection con = connect(); 
			 if (con == null) 
			 {return "Error while connecting to the database for reading."; } 
			 
			 
			 // Prepare the html table to be displayed
			 output = "<table border='1'><tr><th>Product id</th><th>Review id</th><th>Update</th><th>Remove</th></tr>"; 
			 
			 String query = "select * from products"; 
			 Statement stmt = con.createStatement(); 
			 ResultSet rs = stmt.executeQuery(query); 
			 
			 
			 // iterate through the rows in the result set
			 while (rs.next()) 
			 { 
			 String product_id = Integer.toString(rs.getInt("product_id")); 
			 String review_id = rs.getString("review_id"); 
			 
			 
			 // Add into the html table
			 output += "<td>" + product_id + "</td>";
			 output += "<td>" + review_id + "</td>";
			 
			 //buttons
	            
			 output += "<td><input name='btnUpdate' type='button' value='Update' class=' btnUpdate btn btn-secondary' data-itemid='" + product_id + "'></td>"
	            		+ "<td><input name = 'btnRemove' type='button' value = 'Remove' "
	            		+ "class = 'btnRemove btn btn-danger' data-itemid='" + product_id + "'>"
	            		+"</td></tr>";
            		
		}
		
		con.close();
		
		// Complete the html table
		
		output += "</table>";
		}
		catch (Exception e)
		{
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}
		
		return output;
	}


	public String updateProducts(String id, String review_id)
	{
			String output = "";
			try
			{
				Connection con = connect(); 
				 if (con == null) 
				 {return "Error while connecting to the database for updating."; } 
				 
				 
				 // create a prepared statement
				 String query = "UPDATE products SET review_id=? WHERE product_id=?"; 
				 PreparedStatement preparedStmt = con.prepareStatement(query); 
				 
				 
				 // binding values
				 preparedStmt.setInt(1, Integer.parseInt(review_id)); 
				 preparedStmt.setInt(2, Integer.parseInt(id));
					// execute the statement
					preparedStmt.execute();
					
					con.close();
					
					String newItems = readProducts();
					output = "{\"status\":\"success\", \"data\": \"" +
					newItems + "\"}";
			}
			catch (Exception e)
			{
				output = "{\"status\":\"error\", \"data\": \"Error while updating the item.\"}";
				System.err.println(e.getMessage());
			}
			return output;
	}
	
	
public String deleteProduct(String product_id)
{
	String output = "";
	try
	{
		Connection con = connect(); 
		 if (con == null) 
		 {return "Error while connecting to the database for deleting."; } 
		 
		 
		 // create a prepared statement
		 String query = "delete from products where product_id=?"; 
		 PreparedStatement preparedStmt = con.prepareStatement(query); 
		 
		 
		 // binding values
		 preparedStmt.setInt(1, Integer.parseInt(product_id)); 
			// execute the statement
			preparedStmt.execute();
			
			con.close();
			
			String newItems = readProducts();
			output = "{\"status\":\"success\", \"data\": \"" +
			newItems + "\"}";
	}
	catch (Exception e)
	{
		output = "{\"status\":\"error\", \"data\": \"Error while deleting the item.\"}";
		System.err.println(e.getMessage());
	}
	
	return output;
	}

}