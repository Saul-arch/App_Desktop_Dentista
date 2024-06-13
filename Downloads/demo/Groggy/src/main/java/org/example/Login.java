package org.example;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.example.Main.framePrincipal;

public class Login {
    public Statement st;
    public Login(JFrame jfrLogin){
        initComponent(jfrLogin);
    }

    private void initComponent(JFrame jfrLogin) {
        JPanel pnlPanelPrincipalLogin = new JPanel();
        JPanel pnlPanelIzquierdo = new JPanel();
        JPanel pnlPanelDerecho = new JPanel();

        JTextPane lblSlogan = new JTextPane();
        JTextPane lblObjetivo = new JTextPane();
        File rutaImgLogo = new File("C:\\Users\\pc\\Downloads\\demo\\Groggy\\Imagenes\\logologin.svg");
        FlatSVGIcon imgLogo = new FlatSVGIcon(rutaImgLogo);
        JLabel lblImgLogo = new JLabel();
        Border bordeIzquierdo = BorderFactory.createMatteBorder(0, 0, 0, 2, Color.decode("#FAFAFA"));

        JLabel lblTitleLogin = new JLabel();
        JLabel lblBienvenida = new JLabel();
        JTextPane lblInformacion = new JTextPane();
        JLabel lblUsuario = new JLabel();
        JLabel Password = new JLabel();
        JTextField txtUsuario = new JTextField();
        JPasswordField jpassPassword = new JPasswordField();
        JButton btnIngresar = new JButton();
        JButton btnCodigo = new JButton();

        lblTitleLogin.setSize(150, 50);
        lblTitleLogin.setText("LOG IN");
        lblTitleLogin.setLocation(70, 50);
        lblTitleLogin.setVisible(true);
        lblTitleLogin.setFont(new Font("", Font.PLAIN, 30));

        lblBienvenida.setSize(150, 40);
        lblBienvenida.setText("Bienvenido!!");
        lblBienvenida.setLocation(330, 190);
        lblBienvenida.setVisible(true);
        lblBienvenida.setFont(new Font("", Font.PLAIN, 21));

        lblInformacion.setSize(350, 80);
        lblInformacion.setText("Inicie sesion ingresando la informacion a \n                    continuacion");
        lblInformacion.setLocation(250, 280);
        lblInformacion.setVisible(true);
        lblInformacion.setForeground(Color.decode("#A6A6A6"));
        lblInformacion.setFont(new Font("", Font.PLAIN, 16));

        lblUsuario.setSize(60, 20);
        lblUsuario.setText("Usuario: ");
        lblUsuario.setFont(new Font("", Font.PLAIN, 13));
        lblUsuario.setLocation(100, 458);

        txtUsuario.setSize(450, 42);
        txtUsuario.setLocation(160, 450);

        Password.setSize(100, 20);
        Password.setText("Password: ");
        Password.setFont(new Font("", Font.PLAIN, 13));
        Password.setLocation(93, 558);

        jpassPassword.setSize(450, 42);
        jpassPassword.setLocation(160, 550);

        btnIngresar.setSize(200, 45);
        btnIngresar.setLocation(153, 700);
        btnIngresar.setText("INGRESAR");
        btnIngresar.setBackground(Color.decode("#5FC48D"));
        btnIngresar.setForeground(Color.white);
        btnIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = txtUsuario.getText();
                char[] password = jpassPassword.getPassword();

                String contrasena = new String(password);
                //Quitar los String y volver a poner las variables
                Conexion login = new Conexion("saul_administrador_24", "administrador24");
                try {
                    st = login.cn.createStatement();
                    ResultSet rs = st.executeQuery("select * from clientes");
                    while (rs.next()){
                        System.out.println(rs.getString("cod_cliente"));
                    }

                    Splash hilo1 = new Splash(framePrincipal, st);

                    hilo1.start();
                    hilo1.start();
                    /*
                    while (hilo1.senal){
                        if (hilo1.senal){
                            System.out.println("cargando pantalla");

                        }else{
                            System.out.println("LISTO");
                        }
                    }*/

                }catch(Exception e1){

                }

            }
        });

        btnCodigo.setSize(200, 45);
        btnCodigo.setLocation(400, 700);
        btnCodigo.setText("INGRESAR CON CODIGO");
        btnCodigo.setBackground(Color.decode("#5FC48D"));
        btnCodigo.setForeground(Color.white);

        lblImgLogo.setSize(1000, 700);
        lblImgLogo.setIcon(imgLogo);
        lblImgLogo.setLocation(150,-70);
        lblImgLogo.setVisible(true);

        lblSlogan.setSize(350, 80);
        lblSlogan.setText("Atendiendo a tus sonrisas, \n     una consulta a la vez");
        lblSlogan.setLocation(236, 550);
        lblSlogan.setLayout(new FlowLayout());
        lblSlogan.setEditable(false);
        lblSlogan.setBackground(Color.white);
        lblSlogan.setFont(new Font("Arial", Font.BOLD, 20));

        lblObjetivo.setSize(400, 200);
        lblObjetivo.setText("   ''En cada desafio encontramos oportunidad. Con \n determinacion y unidad, podemos alcanzar grandes \n    logros. Sigamos trabajando juntos hacia el exito \n                Nuestro potencial es ilimitado!!''.");
        lblObjetivo.setLocation(200, 650);
        lblObjetivo.setEditable(false);
        lblObjetivo.setBackground(Color.white);
        lblObjetivo.setFont(new Font("Arial", Font.PLAIN, 16));

        pnlPanelIzquierdo.setSize(755, 882);
        pnlPanelIzquierdo.setLayout(null);
        pnlPanelIzquierdo.setVisible(true);
        pnlPanelIzquierdo.setBorder(bordeIzquierdo);
        pnlPanelIzquierdo.setBackground(Color.white);
        pnlPanelIzquierdo.add(lblSlogan);
        pnlPanelIzquierdo.add(lblObjetivo);
        pnlPanelIzquierdo.add(lblImgLogo);

        pnlPanelDerecho.setSize(752, 882);
        pnlPanelDerecho.setLayout(null);
        pnlPanelDerecho.setLocation(755, 0);
        pnlPanelDerecho.setBackground(Color.white);
        pnlPanelDerecho.add(lblTitleLogin);
        pnlPanelDerecho.add(lblBienvenida);
        pnlPanelDerecho.add(lblInformacion);
        pnlPanelDerecho.add(lblUsuario);
        pnlPanelDerecho.add(Password);
        pnlPanelDerecho.add(txtUsuario);
        pnlPanelDerecho.add(jpassPassword);
        pnlPanelDerecho.add(btnIngresar);
        pnlPanelDerecho.add(btnCodigo);
        pnlPanelDerecho.setVisible(true);


        pnlPanelPrincipalLogin.setSize(1512, 882);
        pnlPanelPrincipalLogin.setLayout(null);
        pnlPanelPrincipalLogin.setBackground(Color.white);
        pnlPanelPrincipalLogin.setVisible(true);
        pnlPanelPrincipalLogin.add(pnlPanelIzquierdo);
        pnlPanelPrincipalLogin.add(pnlPanelDerecho);
        pnlPanelPrincipalLogin.setVisible(true);

        jfrLogin.setSize(1512, 882);
        jfrLogin.setLocationRelativeTo(null);
        jfrLogin.setResizable(false);
        jfrLogin.setLayout(null);
        jfrLogin.setUndecorated(true);
        jfrLogin.setBackground(Color.white);
        jfrLogin.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jfrLogin.add(pnlPanelPrincipalLogin);
        jfrLogin.setVisible(false);

    }
}
