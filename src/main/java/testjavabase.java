import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class testjavabase {
	public static void main(String[] args) {
		
	String url = "jdbc:mysql://localhost:3306/travel_agency?autoReconnect=true&useSSL=false";
	String username = "root";
	String password = "root";

	System.out.println("Connecting database...");
	
	System.out.println("Loading driver...");

	try {
	    Class.forName("com.mysql.jdbc.Driver");
	    System.out.println("Driver loaded!");
	} catch (ClassNotFoundException e) {
	    throw new IllegalStateException("Cannot find the driver in the classpath!", e);
	}
	
	try (Connection connection = DriverManager.getConnection(url, username, password)) {
	    System.out.println("Database connected!");
	} catch (SQLException e) {
	    throw new IllegalStateException("Cannot connect the database!", e);
	}
}
}