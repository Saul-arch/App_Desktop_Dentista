package org.example;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static JFrame framePrincipal;
    public static JFrame framePrincipalLogin;
    public static void main(String[] args) throws SQLException {
        FlatLightLaf.setup();
        framePrincipal = new JFrame();
        Scanner entrada = new Scanner(System.in);
        JFrame framePrincipalSplash = new JFrame();
        framePrincipalLogin = new JFrame();
        JPanel pnlContentSplash = new JPanel();
        Splash hilo1;
        JLabel lblImgSplash = new JLabel();
        File rutaSplash = new File("C:\\Users\\pc\\Downloads\\demo\\Groggy\\imagenes_dent\\splashinit.svg");
        FlatSVGIcon imgSplash = new FlatSVGIcon(rutaSplash);

        lblImgSplash.setSize(1500, 800);
        lblImgSplash.setLocation(0,0);
        lblImgSplash.setLayout(null);
        lblImgSplash.setIcon(imgSplash);
        lblImgSplash.setVisible(true);

        pnlContentSplash.setSize(1512, 882);
        pnlContentSplash.setLocation(0,0);
        pnlContentSplash.setLayout(null);
        pnlContentSplash.add(lblImgSplash);
        pnlContentSplash.setBackground(Color.white);

        framePrincipal.setSize(1512, 882);
        framePrincipal.setLocationRelativeTo(null);
        framePrincipal.setTitle("Sistema de gestion de consultas dentales");
        framePrincipal.setResizable(false);
        framePrincipal.setLayout(null);
        framePrincipal.setBackground(Color.decode("#F5F5F5"));
        framePrincipal.setVisible(false);
        framePrincipal.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        framePrincipalSplash.setSize(1512, 882);
        framePrincipalSplash.setLocationRelativeTo(null);
        framePrincipalSplash.setTitle("Sistema de gestion de consultas dentales");
        framePrincipalSplash.setResizable(false);
        framePrincipalSplash.setLayout(null);
        framePrincipalSplash.setBackground(Color.decode("#F5F5F5"));
        framePrincipalSplash.add(pnlContentSplash);
        framePrincipalSplash.setVisible(true);
        framePrincipalSplash.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Login login = new Login(framePrincipalLogin);

        //hilo1 = new Splash(framePrincipal, login.st);

        if (login.st == null){
            System.out.println("El st es null00o");
        }else{
            System.out.println("El ST no es NULL");
        }

        /*hilo1.start();
        while (hilo1.senal){
            System.out.println("");
            if (hilo1.senal){

            }else{

            }
        }*/

        framePrincipalSplash.dispose();
        framePrincipalLogin.setVisible(true);

    }
}