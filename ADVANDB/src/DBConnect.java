import java.sql.Date;
import java.sql.DriverManager;



import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

public class DBConnect {

	private java.sql.Connection con;
	private ResultSet rs;
	
	public DBConnect(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_hpq","root","");

			
			con.createStatement();
			
		}catch(Exception ex){
			System.out.println("Error: " +ex);
		}
	}
	
	//get next available productID
	
	public int executeQuery(String query)
	{
		Integer resultCount = 1;
		try{
			  PreparedStatement preparedStatement = (PreparedStatement) con.prepareStatement(query);

			rs = preparedStatement.executeQuery();
			
			while(rs.next())
			{
				resultCount = rs.getInt("count(*)");
				System.out.println("Database accessed with query: " + query);
			}
			

		}catch(Exception ex){
			System.out.println(ex);
		}
		
		return resultCount;
	}
	
}
		  