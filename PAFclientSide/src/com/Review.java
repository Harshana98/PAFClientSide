package com;

import java.sql.*;

public class Review
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
	
	
public String insertReview( String project_id, String admin_id, String review, String acceptance)
{
	String output = "";
	try
	{
		Connection con = connect(); 
		 if (con == null) 
		 {return "Error while connecting to the database for inserting."; } 
		 
		 
		 // create a prepared statement
		 String query = " insert into review (`review_id`, `project_id`, `admin_id`, `review`,`acceptance`)"+ " values (?, ?, ?, ?, ?)"; 
		 PreparedStatement preparedStmt = con.prepareStatement(query); 
		 
		 
		 // binding values 
		 preparedStmt.setInt(1, 0); 
		 preparedStmt.setInt(2, Integer.parseInt(project_id));
		 preparedStmt.setInt(3, Integer.parseInt(admin_id));
		 preparedStmt.setString(4, review);
		 preparedStmt.setString(5, acceptance);
		// execute the statement

		preparedStmt.execute();
		
		con.close();
		
		String newItems = readReviews();
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


public String readReviews()
{
		String output = "";
		try
		{
			Connection con = connect(); 
			 if (con == null) 
			 {return "Error while connecting to the database for reading."; } 
			 
			 
			 // Prepare the html table to be displayed
			 output = "<table border='1'><tr><th>Project id</th>" +
			 "<th>Admin id</th>"+
			 "<th>Review</th>"+
			 "<th>Acceptance</th>"+
			 "<th>Update</th><th>Remove</th></tr>"; 
			 
			 String query = "select * from review"; 
			 Statement stmt = con.createStatement(); 
			 ResultSet rs = stmt.executeQuery(query); 
			 
			 
			 // iterate through the rows in the result set
			 while (rs.next()) 
			 { 
			 String review_id = Integer.toString(rs.getInt("review_id"));
			 String projectID = Integer.toString(rs.getInt("project_id"));
			 String admin_id = Integer.toString(rs.getInt("admin_id"));
			 String review = rs.getString("review");
			 String acceptance = rs.getString("acceptance");
			 
			 
			 // Add into the html table
			 output += "<tr><td>"+ projectID + "</td>";
			 output += "<td>" + admin_id + "</td>";
			 output += "<td>" + review + "</td>";
			 output += "<td>" + acceptance + "</td>";
			 
			 //buttons
	            
			 output += "<td><input name='btnUpdate' type='button' value='Update' class=' btnUpdate btn btn-secondary' data-itemid='" + review_id + "'></td>"
	            		+ "<td><input name = 'btnRemove' type='button' value = 'Remove' "
	            		+ "class = 'btnRemove btn btn-danger' data-itemid='" + review_id + "'>"
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


public String updateReviews(String id, String project_id, String admin_id, String review, String acceptance)
{
		String output = "";
		try
		{
			Connection con = connect(); 
			 if (con == null) 
			 {return "Error while connecting to the database for updating."; } 


			 // create a prepared statement
			 String query = "UPDATE review SET project_id=?, admin_id=?, review=?,acceptance=? WHERE review_id=?"; 
			 PreparedStatement preparedStmt = con.prepareStatement(query); 


			 // binding values 
			 preparedStmt.setInt(1, Integer.parseInt(project_id));
			 preparedStmt.setInt(2, Integer.parseInt(admin_id));
			 preparedStmt.setString(3, review); 
			 preparedStmt.setString(4, acceptance); 
			 preparedStmt.setInt(5, Integer.parseInt(id));
				// execute the statement
				preparedStmt.execute();

				con.close();

				String newItems = readReviews();
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
	
	
public String deleteItem(String review_id)
{
	String output = "";
	try
	{
		Connection con = connect(); 
		 if (con == null) 
		 {return "Error while connecting to the database for deleting."; } 
		 
		 
		 // create a prepared statement
		 String query = "delete from review where review_id=?"; 
		 PreparedStatement preparedStmt = con.prepareStatement(query); 
		 
		 
		 // binding values
		 preparedStmt.setInt(1, Integer.parseInt(review_id)); 
			// execute the statement
			preparedStmt.execute();
			
			con.close();
			
			String newItems = readReviews();
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