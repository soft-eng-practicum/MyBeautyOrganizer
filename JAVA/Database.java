/*=============================================================================
 |   Assignment:  My Beauty Application
 |       Author:  Roland Abrahantes
 |       Grader:  Dr. Gunay ITEC 3860
 |
 |       Course:  ITEC 3860
 |   Instructor:  Dr. Gunay
 |     Due Date:  3/14/2016
 |
 |  Description:  Orginize beauty products in a database.
 |
 |     Language:  JAVA and SQL
 | Ex. Packages:  JAVAFX
 |                                
 *===========================================================================*/


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;


public class Database {
	   
	 Connection con = null;
     Statement stmt = null;
     Statement stmt2 = null;
     Statement stmt3 = null;
     ResultSet rs = null;
     Alert alert; 
     Alert alert1;
     Alert alert2;
     String createdOk = "no";

     String sql = "SELECT * FROM STUDENT";
     String sql2 = "INSERT INTO BOOKSTORE(bookstoreID, name, address, city, state)" + "VALUES(4000301,'Books for us','134 potomac', 'Atlanta', 'GA')";
     String sql3 = "INSERT INTO BOOKSTORE(bookstoreID, name, address, city, state)" + "VALUES(4000311,'Books for us','Something Street ', 'Atlanta', 'GA')";	
	
	public Database(){
		
		  
		  alert = new Alert(AlertType.INFORMATION);
		  alert.setTitle("Information Dialog");
		  alert.setHeaderText("Error Message");
		  alert.setContentText("The Username you entered is not correct or is in use by another user");
		  
		  alert1 = new Alert(AlertType.INFORMATION);
		  alert1.setTitle("Information Dialog");
		  alert1.setHeaderText("Error Message");
		  alert1.setContentText("The Username or Password fields can't be left empty.");
		  
		  alert2 = new Alert(AlertType.INFORMATION);
		  alert2.setTitle("Information Dialog");
		  alert2.setHeaderText("Error Message");
		  alert2.setContentText("You have to enter an username in order to create an account.");
		  
		  
		
		try {
            
			Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:DEVELOPER/developer@localhost");
            stmt = con.createStatement();
            stmt2 = con.createStatement();
            rs = stmt.executeQuery(sql);
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        }
	

	}

	public void createAccount(String Username, String Password,String Name, String lastName,String address, String age){
		
		String sqlInsert = "INSERT INTO UserAccount(Username, Password,Name, lastName,address, age)VALUES('" + Username + "','"
		+ Password + "','" + Name + "','"
		+ lastName + "','" + address + "','" + age + "')";
		
		
		if(Username.equalsIgnoreCase("")||Password.equalsIgnoreCase("")||Name.equalsIgnoreCase("")){
			alert2.showAndWait();
			setCreatedOk("NoAccountCreated");
		}else{

			try {
			
				
				Class.forName("oracle.jdbc.driver.OracleDriver");
	            con = DriverManager.getConnection("jdbc:oracle:thin:DEVELOPER/developer@localhost");
	            stmt = con.createStatement();
	        
	            rs = stmt.executeQuery(sqlInsert);
				
	            stmt.close();
	            con.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
				alert.showAndWait();
			} catch (ClassNotFoundException e){
				e.printStackTrace();
				alert.showAndWait();
			}
			finally{
				
			}
			setCreatedOk("AccountCreated");
		
		}
	
	 //   setCreatedOk("AccountCreated");
	}

	
	public boolean checkLogin(String Username, String Password){
			
		boolean exists = false;
	
		String sqlInsert = "SELECT Username, Password from UserAccount where Username = '" + Username + "'" + " AND password = '" + Password + "'";
		
		if(Username.equalsIgnoreCase("") || Password.equalsIgnoreCase("")){
			alert1.showAndWait();
		}else{
			try{
				
				Class.forName("oracle.jdbc.driver.OracleDriver");
	            con = DriverManager.getConnection("jdbc:oracle:thin:DEVELOPER/developer@localhost");
	            stmt = con.createStatement();
				rs = stmt.executeQuery(sqlInsert);
				
				while(rs.next()){
					exists = true;
				//	this.setUsername(Username);
				//	this.password = Password;
				}
				
				if(exists == false){
					alert.showAndWait();
				}
				
				
			}catch(SQLException e){
				e.printStackTrace();
			}catch(ClassNotFoundException e){
				e.printStackTrace();
			}finally{
				
			}
			
		}	
		
		return exists;
		
	}
	
	   public Customer customerData(String Username){
		
	
		Customer customer1 = new Customer();
		String sqlInsert = "SELECT * from UserAccount where Username = '" + Username + "'";// + " AND password = '" + this.password + "'";
		
          try{
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:DEVELOPER/developer@localhost");
            stmt = con.createStatement();
			rs = stmt.executeQuery(sqlInsert);

			while(rs.next()){
				
				String name = rs.getString(3);
			    customer1.setName(name);
			    String lastName = rs.getString(4);
			    customer1.setLastName(lastName);
			    String address = rs.getString(5);
			    customer1.setAddress(address);
			    String age = rs.getString(6);
			    customer1.setAge(age);
			}
			
	    }catch(SQLException e){
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}finally{
			
		}
		
		return customer1;
		
		   
	   }

		public String getCreatedOk() {
			return createdOk;
		}

		public void setCreatedOk(String createdOk) {
			this.createdOk = createdOk;
		}
	
		
		

}