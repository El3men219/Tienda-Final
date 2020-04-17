
package Libre;


import java.sql.*;

/**
 *
 * @author score
 */

public class Conexion {
    
    private static Connection conn;
    private static final String driver ="com.mysql.jdbc.Driver";
    private static final String user = "root";
    private static final String password= "";
    private static final String url="jdbc:mysql://localhost:3306/taller2.1";

    public Conexion() {
        conn = null;
        try {
            
             Class.forName(driver);
             conn = DriverManager.getConnection(url, user, password);
             if(conn !=null)
             {
                 System.out.println("Conexion exitosa");
             }
        }catch (ClassNotFoundException | SQLException e)
        {
            System.out.println("Conexin fallida"+e);
        }    
    }
    
    public Connection getConnection(){
        return conn;
} 
   
    public void desconectar(){
      conn = null;
    if(conn ==null)
             {
                 System.out.println("Conexion Terminada");
            }           
    }
       
}