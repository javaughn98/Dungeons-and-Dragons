package database;
import java.util.ArrayList;
import java.sql.*;

public class SetUpDBase {
	
	private Connection conn = null;
	private Statement stmt = null;

	public SetUpDBase(){
		String name = DBDetails.username;
		String pass = DBDetails.password;
		
		runSript(name, pass);   //Use to build out the required tables
		// deleteT(name,pass);     //Use to delete the tables completely
	}

	public void deleteT(String n, String p){
		try{
			Class.forName(DBDetails.JDBC_DRIVER);
		    conn = DriverManager.getConnection(DBDetails.DB_URL,n,p);
			stmt = conn.createStatement();

			stmt.executeUpdate("DROP TABLE Monsters");

			conn.close();
		}
		catch (Exception e){
			System.out.println(e);
		}

	}
	
	public void runSript(String n, String p){
		try{
			Class.forName(DBDetails.JDBC_DRIVER);
		    conn = DriverManager.getConnection(DBDetails.DB_URL,n,p);
		    stmt = conn.createStatement();
		    
		    //add Courses table
	        String sql = "CREATE TABLE IF NOT EXISTS Monsters (\n"
	                + "	name text NOT NULL,\n"
	                + "	upper text,\n"
	                + "	lower text,\n"
	                + "	description text\n"
	                + ");";
	        
	      
			//System.out.println(sql); // echo the statement for debugging
			//System.out.println(sql2); // echo the statement for debugging
			
			stmt.executeUpdate(sql);
				
			stmt.close();
			conn.close();
		}
		catch (Exception e){
			System.out.println(e);
		}
	}
	
	public static void main (String[] args){
		SetUpDBase sInstance = new SetUpDBase();
	}

}
