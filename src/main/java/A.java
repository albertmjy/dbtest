/**
 * Created by albert on 2/25/15.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import java.sql.*;

public class A {
    static Connection conn;
    public static void main(String[] args) {

        String driver = "org.apache.derby.jdbc.EmbeddedDriver";
        String connectionURL = "jdbc:derby:myDatabase;create=true";
        String createString = "CREATE TABLE Employee (NAME VARCHAR(32) NOT NULL, ADDRESS VARCHAR(50) NOT NULL)";
        System.out.println("\n*************************");
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(connectionURL);
//            Statement stmt = conn.createStatement();
//            stmt.executeUpdate(createString);

            PreparedStatement psInsert = conn.prepareStatement("insert into Employee values (?,?)");

            psInsert.setString(1, "aaaaaaaaaaaaaa");
            psInsert.setString(2, "bbbbbbbbbbbbbb");

            psInsert.executeUpdate();

            Statement stmt2 = conn.createStatement();
            ResultSet rs = stmt2.executeQuery("select * from Employee");
            int num = 0;
            while (rs.next()) {
                System.out.println(++num + ": Name: " + rs.getString(1) + "\n Address" + rs.getString(2));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try
        {
            // the shutdown=true attribute shuts down Derby
            DriverManager.getConnection("jdbc:derby:;shutdown=true");

            // To shut down a specific database only, but keep the
            // engine running (for example for connecting to other
            // databases), specify a database in the connection URL:
            //DriverManager.getConnection("jdbc:derby:" + dbName + ";shutdown=true");
        }
        catch (SQLException se)
        {
            if (( (se.getErrorCode() == 50000)
                    && ("XJ015".equals(se.getSQLState()) ))) {
                // we got the expected exception
                System.out.println("Derby shut down normally");
                // Note that for single database shutdown, the expected
                // SQL state is "08006", and the error code is 45000.
            } else {
                // if the error code or SQLState is different, we have
                // an unexpected exception (shutdown failed)
                System.err.println("Derby did not shut down normally");
            }
        }
    }
}
