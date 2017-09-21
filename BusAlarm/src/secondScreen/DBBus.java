package secondScreen;

import java.sql.DriverManager; //JDBC����̹� Ŭ����
import java.sql.ResultSet; //DB�� ����� ��Ÿ���� Ŭ����
import java.sql.SQLException; //DB���� Ŭ����
import java.sql.Connection; //DB�� �����ϱ� ���� Ŭ����
import java.sql.Statement; //������ ���� �� ��� ���� Ŭ����

public class DBBus {
	Connection conn = null; //DB�� �����ϱ� ���� conn��ü ����
	Statement stmt = null; //����� ��Ʈ�� �������� �������� �����ִ� stmt��ü ����
	ResultSet rs = null; //������ ���� �� ����� �������ִ� rs��ü ����
	
	String driverName = "com.mysql.jdbc.Driver"; //DB����̹� �˻�
	String url = "jdbc:mysql://localhost:3306/dbBus"; 
	String user = "dbuser";
	String password = "dbpass";
	
	public DBBus(){
		try {
			Class.forName(driverName); //�ε�
			conn = DriverManager.getConnection(url, user, password); //����
			System.out.println("DB Connection OK");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("DB Driver Error!");
		} catch(SQLException se) {
			se.printStackTrace();
			System.out.println("DB Connection Error!");
		}
		
		try {
			stmt = conn.createStatement(); //���� ������ ���� stmt����
			rs = stmt.executeQuery("SELECT * FROM dbbus.bus_info"); //���� ���� stmt��ü�� �̿��Ͽ� ���� ���� �� rs�� ����� ����

			if (stmt.execute("SELECT * FROM dbbus.bus_info")) {
				rs = stmt.getResultSet(); //����� ������
			}
		} catch(Exception ex) {
			// handle the error
			System.out.println("getresult error");
		}

		try{
			while (rs.next()) { //rs�� ����� �����͵��� �о� ������� ������ ����
				int bus_num = rs.getInt("bus_num");
				boolean low_floor_bus = rs.getBoolean("low_floor_bus"); 
				int seat_num = rs.getInt("seat_num");
				System.out.println(bus_num + " " + low_floor_bus + " " +seat_num); //���� ���
			} 
		}catch(Exception ex) {
			// handle the error
			System.out.println("print error");
		}
		
	} //DBBus

	public static void main(String[] args) {
		DBBus dbbus = new DBBus();
		
	}
}
