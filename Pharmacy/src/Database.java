/**
 * 
 */


import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 * @author j0y57/Qxx
 *Peter was about to drown due to lack of faith but when he stretched his hand 
 *to the Master he was rescued and i am sure he stunky leg danced on top of 
 *the sea while his fellow disciples watched on the boat. the same can be done to
 *you just stretch your hand before you drown into hell, u cannot save yourself no
 *matter how good you are, trust in Jesus to save you and that my friend is a good
 *start.
 */

public class Database extends JFrame {
	
    private final String username = "root";
    private final String pas = "";
    private final String Database = "jdbc:mysql://localhost:3306/pharmacy";
    private Connection Conn;

	/**
	 * @return the conn
	 */
	public final Connection getConn() {
		return Conn;
	}

	private static final long serialVersionUID = 7139138664340406067L;

	public Database() {
		
		init();
	}
	
	private void init() {
		try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            //Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        	JOptionPane.showMessageDialog(getParent(), " problem connecting Database");
        }
		
		try {
        
            Conn = (Connection) DriverManager.getConnection(Database, username, pas);
    		
    		String outbreak_table = "create table if not exists outbreak("
    				+ "disease varchar(100) "
    				+ "not null,region varchar(100) not null, severe enum('high', "
    				+ "'medium','low') default 'low')";
    		
                    try (Statement st = Conn.createStatement()) {
                        st.executeUpdate(outbreak_table);
                        
                        String users = "create table if not exists users(email varchar(100) "
                                + "not null,passwords varchar(100) not null)";
                        st.executeUpdate(users);
                    }            

        } catch (SQLException ex) {

    		JOptionPane.showMessageDialog(getParent(), " unable to create "
    				+ "database and"
    				+ "tables successfully");
        }
	}
	
	public void signup(String email, String pass, JTextArea error) {
		try {
			String sql = "insert into users(email,passwords) values(?,?)";
			int count = login(pass, email, error);
			
			if (count == 1) {
				error.setText("this account already exit"
						+ " please check your password and "
						+ "email and register!!!!");
			} else {					
                            try (PreparedStatement pstmt = (PreparedStatement) Conn.prepareStatement(sql)) {
                                pstmt.setString(1, email);
                                pstmt.setString(2, pass);
                                pstmt.executeUpdate();
                                
                                error.setText("user created successfully. "
                                        + "proceed to the login tab to  login");
                            }
				Conn.close();
			}
			
		} catch (HeadlessException | SQLException e) {
			// TODO Auto-generated catch block
			error.setText(e.getLocalizedMessage());
		}
	}
	
	public void insertDisease(String disease, String region, JTextArea message) {
		String sql = "insert into outbreak(disease, region) values(?,?)";
		try {
                    try (PreparedStatement pstmt = (PreparedStatement) Conn.prepareStatement(sql)) {
                        pstmt.setString(1, disease);
                        pstmt.setString(2, region);
                        pstmt.executeUpdate();
                        
                        message.setText("record inserted successfully");
                    }
			Conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			message.setText(e.getLocalizedMessage());
		}
	}
	
	public void deleteDisease(int delete, JTextArea error) {
		String sql = "delete from outbreak where disease_id=?";		
		try {
                    try (PreparedStatement stmt = Conn.prepareStatement(sql)) {
                        stmt.setInt (1, delete);
                        
                        stmt.executeUpdate();
                    }
			close();
			error.setText("record deleted");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			error.setText(e.getLocalizedMessage());
		}
	}
	
	public int login(String password, String email, JTextArea error) throws SQLException {
	
			String sql = "select count(*) as count from users where email=?"
					+ " and passwords=?";
			PreparedStatement stmt = Conn.prepareStatement(sql);
			
			stmt.setString(1, email);
			stmt.setString(2, password);
			
                        int count;
            try (ResultSet rs = stmt.executeQuery()) {
                count = 0;
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
			return count;			
	}
	
	public void close() {
		try {
			Conn.close();
		} catch (SQLException e) {
                    // TODO Auto-generated catch block

		}
	}
}
