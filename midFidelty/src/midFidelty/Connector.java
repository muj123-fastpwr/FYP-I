package midFidelty;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;


public class Connector {
	private Connection cn;
	
	public Connector(){
		cn=null;
	}

	public Connection connectDataBase(){
		String constr="jdbc:mysql://localhost:3306/peranaldb";
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("well done till here");
			cn=DriverManager.getConnection(constr,"root","");
			JOptionPane.showMessageDialog(null, "successfully connected to database\n");
			}
		
			catch (ClassNotFoundException e) {
				JOptionPane.showMessageDialog(null, "Unable to connect database\n"+e);
			}
			 catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Unable to connect database\n"+e);
			}
		
		return cn;
	}
	
	
}
