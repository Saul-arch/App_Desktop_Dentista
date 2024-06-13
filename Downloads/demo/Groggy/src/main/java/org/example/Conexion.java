package org.example;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;

import static org.example.Main.framePrincipal;
import static org.example.Main.framePrincipalLogin;

public class Conexion {
    Connection cn;
    String Driver = "oracle.jdbc.driver.OracleDriver";
    String url = "jdbc:oracle:thin:@localhost:1521:XE";
    static boolean conexion_exits = false;
    public Conexion(){

    }

    public Conexion(String usuario, String contrasena){
        try {
            Class.forName(Driver);
            cn = DriverManager.getConnection(url, usuario, contrasena);
            System.out.println("Se conecto bien a la base de datos");
            JOptionPane.showMessageDialog(null, "Se conecto a la base de datos");
            conexion_exits = true;
            framePrincipalLogin.setVisible(false);
            framePrincipal.setVisible(true);
        }catch(Exception e){
            System.out.println("Ocurrio un error al conectar la base de datos"+e);
            System.out.println(usuario);
            System.out.println(contrasena);
            JOptionPane.showMessageDialog(null, "NO Se conecto a la base de datos");

        }
    }
    public static Boolean conexion_exitosa(){
        return conexion_exits;
    }

}
