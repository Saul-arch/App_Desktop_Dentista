package org.example;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Vector;

public class Compras {
    public JDialog dlgModRegistro = new JDialog();
    public JPanel pnlPrincipalCompras = new JPanel();
    public JPanel pnlContentBusCanc = new JPanel();
    public Statement st;
    public Compras(JFrame framePrincipal, Statement st){
        this.st = st;
        iniciarVista(framePrincipal);
    }
    private void iniciarVista(JFrame jfrPrincipal) {
        JTable tblTabla = new JTable();
        Vector<String> columnasCompras = new Vector<String>();
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        DefaultTableModel model;
        JScrollPane scpContenedorJtable = new JScrollPane();

        columnasCompras.add("Clave dentista");
        columnasCompras.add("Clave paciente");
        columnasCompras.add("Fecha");
        columnasCompras.add("Observaciones");
        columnasCompras.add("Hora");

        model = new DefaultTableModel(data, columnasCompras);
        tblTabla.setModel(model);

        scpContenedorJtable.setSize(1260, 620);
        scpContenedorJtable.setLocation(20,115);
        scpContenedorJtable.setViewportView(tblTabla);


        pnlPrincipalCompras.setSize(1300, 870);
        pnlPrincipalCompras.setLocation(209, 30);
        pnlPrincipalCompras.setLayout(null);
        pnlPrincipalCompras.setVisible(false);

        File rutaImgCancelSearchCompra = new File("C:\\Users\\pc\\IdeaProjects\\Sistema_ventas\\Imagenes\\cancelarSearchVentas.svg");
        File rutaImgSearchCompra = new File("C:\\Users\\pc\\IdeaProjects\\Sistema_ventas\\Imagenes\\search_venta.svg");
        File rutaSettingsCompra = new File("C:\\Users\\pc\\IdeaProjects\\Sistema_ventas\\Imagenes\\setting_ventas.svg");
        File rutaImgNotResult = new File("C:\\Users\\pc\\IdeaProjects\\Sistema_ventas\\Imagenes\\notresultcompra.svg");
        FlatSVGIcon imgSettingsCompra = new FlatSVGIcon(rutaSettingsCompra);
        FlatSVGIcon imgSearchCompra = new FlatSVGIcon(rutaImgSearchCompra);
        FlatSVGIcon imgCancelSearch = new FlatSVGIcon(rutaImgCancelSearchCompra);
        FlatSVGIcon imgNotResultCompra = new FlatSVGIcon(rutaImgNotResult);

        LocalDate fechaActual = LocalDate.now();
        int dia = fechaActual.getDayOfMonth();
        int mes = fechaActual.getMonthValue();
        int anio = fechaActual.getYear();

        JLabel lblFecha = new JLabel();
        JLabel lblImgSettings = new JLabel();
        JLabel lblImgSearch = new JLabel();
        JLabel lblImgCancelSearch = new JLabel();
        JLabel lblImgNotResult = new JLabel();
        JTextField txtBusquedaCompra = new JTextField();
        JLabel lblSinResultados = new JLabel("SIN RESULTADOS");
        JButton btnAgregarCompra = new JButton();
        JButton btnDetallesCompra = new JButton();
        JButton btnEditarCompra = new JButton();
        JButton btnBorrarCompra = new JButton();

        String fechaActualMostrar = concaFecha(dia, mes, anio);

        pnlContentBusCanc.setSize(79, 36);
        //pnlContentBusCanc.setBackground(Color.PINK);
        pnlContentBusCanc.setLocation(820, 2);
        pnlContentBusCanc.setVisible(true);
        pnlContentBusCanc.setLayout(null);

        lblFecha.setText(fechaActualMostrar);
        lblFecha.setLocation(20,30);
        lblFecha.setSize(340, 30);
        lblFecha.setFont(new Font("Arial", Font.PLAIN, 22));
        lblFecha.setForeground(Color.decode("#06A2C4"));

        txtBusquedaCompra.setSize(900, 40);
        txtBusquedaCompra.setLocation(20, 70);
        txtBusquedaCompra.setText("Buscar....");
        txtBusquedaCompra.setFont(new Font("Arial", Font.PLAIN, 14));
        txtBusquedaCompra.setForeground(Color.decode("#ACACAC"));
        txtBusquedaCompra.setBackground(Color.decode("#F5F5F5"));
        txtBusquedaCompra.add(pnlContentBusCanc);
        txtBusquedaCompra.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {


            }
        });

        txtBusquedaCompra.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                txtBusquedaCompra.setCaretPosition(0);
                txtBusquedaCompra.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        lblImgCancelSearch.setVisible(true);
                        txtBusquedaCompra.setText("");
                        txtBusquedaCompra.removeKeyListener(this); // Remueve el KeyListener después del primer evento.
                    }
                });
                txtBusquedaCompra.removeMouseListener(this);
            }
        });

        btnAgregarCompra.setSize(120, 40);
        btnAgregarCompra.setText("Agregar venta");
        btnAgregarCompra.setLocation(935, 70);
        btnAgregarCompra.setBackground(Color.decode("#64D3B2"));
        btnAgregarCompra.setForeground(Color.decode("#FFFFFF"));

        btnDetallesCompra.setSize(120, 40);
        btnDetallesCompra.setText("Detalles");
        btnDetallesCompra.setLocation(1080, 70);
        btnDetallesCompra.setBackground(Color.decode("#FFFFFF"));
        btnDetallesCompra.setForeground(Color.decode("#00000"));

        btnEditarCompra.setSize(120, 40);
        btnEditarCompra.setText("Editar");
        btnEditarCompra.setLocation(935, 740);
        btnEditarCompra.setBackground(Color.decode("#64D3B2"));
        btnEditarCompra.setForeground(Color.decode("#FFFFFF"));
        btnEditarCompra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*Llamar a metodo y hacer visible el JDialog*/
                ModRegistro(jfrPrincipal);

                int x = jfrPrincipal.getX() + 410;
                int y = jfrPrincipal.getY() + 200;

                dlgModRegistro.setLocation(x, y);
                dlgModRegistro.setVisible(true);
                System.out.println("pic");
            }
        });

        btnBorrarCompra.setSize(120, 40);
        btnBorrarCompra.setText("Borrar");
        btnBorrarCompra.setLocation(1100, 740);
        btnBorrarCompra.setBackground(Color.decode("#64D3B2"));
        btnBorrarCompra.setForeground(Color.decode("#FFFFFF"));

        lblImgNotResult.setSize(1200, 550);
        lblImgNotResult.setLocation(250,130);
        lblImgNotResult.setLayout(null);
        lblImgNotResult.setIcon(imgNotResultCompra);
        lblImgNotResult.setVisible(false);


        lblImgSettings.setSize(55, 65);
        lblImgSettings.setIcon(imgSettingsCompra);
        lblImgSettings.setLocation(1230, 10);

        lblImgSearch.setSize(40, 40);
        lblImgSearch.setLocation(45,10);
        lblImgSearch.setIcon(imgSearchCompra);
        lblImgSearch.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                scpContenedorJtable.setVisible(false);
                lblImgNotResult.setVisible(true);
                txtBusquedaCompra.setText("Buscar....");
                txtBusquedaCompra.setFocusable(false);
                lblSinResultados.setVisible(true);
            }
        });

        lblSinResultados.setSize(300, 30);
        lblSinResultados.setFont(new Font("Arial", Font.PLAIN, 20));
        lblSinResultados.setForeground(Color.decode("#5F5F5F"));
        lblSinResultados.setLocation(580, 600);
        lblSinResultados.setVisible(false);

        lblImgCancelSearch.setSize(15, 55);
        lblImgCancelSearch.setLocation(15,3);
        lblImgCancelSearch.setIcon(imgCancelSearch);
        lblImgCancelSearch.setVisible(false);
        lblImgCancelSearch.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("e siginifica : "+e.getButton());
                System.out.println("e siginifica : "+e.paramString());
                txtBusquedaCompra.setText("Buscar....");
                txtBusquedaCompra.setCaretPosition(0);
                txtBusquedaCompra.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        lblImgCancelSearch.setVisible(true);
                        txtBusquedaCompra.setText("");
                        txtBusquedaCompra.removeKeyListener(this); // Remueve el KeyListener después del primer evento.
                    }
                });
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        pnlContentBusCanc.add(lblImgSearch);
        pnlContentBusCanc.add(lblImgCancelSearch);

        pnlPrincipalCompras.add(lblFecha);
        pnlPrincipalCompras.add(txtBusquedaCompra);
        pnlPrincipalCompras.add(btnAgregarCompra);
        pnlPrincipalCompras.add(btnDetallesCompra);
        pnlPrincipalCompras.add(btnEditarCompra);
        pnlPrincipalCompras.add(btnBorrarCompra);
        pnlPrincipalCompras.add(lblImgSettings);
        pnlPrincipalCompras.add(lblImgNotResult);
        pnlPrincipalCompras.add(lblSinResultados);
        pnlPrincipalCompras.add(scpContenedorJtable);
        jfrPrincipal.add(pnlPrincipalCompras);
    }

    private String concaFecha(int dia, int mes, int anio) {
        String mesTexto = "";
        String[] meses = new String[12];
        int[] mesesNumericos = new int[12];
        meses[0] = "Enero";
        meses[1] = "Febrero";
        meses[2] = "Marzo";
        meses[3] = "Abril";
        meses[4] = "Mayo";
        meses[5] = "Junio";
        meses[6] = "Julio";
        meses[7] = "Agosto";
        meses[8] = "Septiembre";
        meses[9] = "Octubre";
        meses[10] = "Noviembre";
        meses[11] = "Diciembre";

        mesesNumericos[0] = 0;
        mesesNumericos[1] = 1;
        mesesNumericos[2] = 2;
        mesesNumericos[3] = 3;
        mesesNumericos[4] = 4;
        mesesNumericos[5] = 5;
        mesesNumericos[6] = 6;
        mesesNumericos[7] = 7;
        mesesNumericos[8] = 8;
        mesesNumericos[9] = 9;
        mesesNumericos[10] = 10;
        mesesNumericos[11] = 11;

        for (int i = 0; i <= 11; i++){
            if(mes == mesesNumericos[i]){
                int indicador = mesesNumericos[i];
                mesTexto = meses[indicador-1];
                break;
            }

        }


        String Fecha = "Hoy es "+ dia + " de "+mesTexto + " del "+anio;
        return Fecha;
    }
    private void ModRegistro(JFrame jfrPrincipal){
        File rutaImgPrincipalEditCompra = new File("C:\\Users\\pc\\IdeaProjects\\Sistema_ventas\\Imagenes\\imgModCompra.svg");
        FlatSVGIcon imgPrincipal = new FlatSVGIcon(rutaImgPrincipalEditCompra);
        JLabel lblImgPrincipal = new JLabel();
        File rutaImgHecho = new File("C:\\Users\\pc\\IdeaProjects\\Sistema_ventas\\Imagenes\\hechocompra.svg");
        FlatSVGIcon imgHecho = new FlatSVGIcon(rutaImgHecho);

        JTextField txtId = new JTextField();
        JTextField txtCodigo = new JTextField();
        JLabel lblVenta = new JLabel();
        JComboBox<String> jccVenta = new JComboBox<>();
        JLabel lblCantidad = new JLabel();
        JComboBox<String> jccCantidad = new JComboBox<>();
        JLabel lblFecha = new JLabel();
        JTextField txtFecha = new JTextField();
        JLabel lblHora = new JLabel();
        JTextField txtHora = new JTextField();
        JLabel lblVendedor = new JLabel();
        JComboBox<String> jccVendedor = new JComboBox<>();
        JLabel lblCliente = new JLabel();
        JComboBox<String> jccCliente = new JComboBox<>();
        JTextField txtUnitario = new JTextField();
        JTextField txtPrecioTotal = new JTextField();
        JButton btnHecho = new JButton();
        JLabel lblnameModificacion = new JLabel();
        JLabel lblAyuda = new JLabel();

        // Crea un borde que afecta solo al lado izquierdo
        Border bordeIzquierdo = BorderFactory.createMatteBorder(0, 0, 0, 1, Color.decode("#FAFAFA"));
        Border bordeIzquierdoJlabel = BorderFactory.createMatteBorder(0, 1, 0, 0, Color.decode("#C6C5C5"));

        JPanel pnlprimeraSeccion = new JPanel();
        pnlprimeraSeccion.setLayout(null);
        pnlprimeraSeccion.setSize(280, 600);
        pnlprimeraSeccion.setBorder(bordeIzquierdo);
        pnlprimeraSeccion.setLocation(0,0);

        lblImgPrincipal.setSize(380, 300);
        lblImgPrincipal.setIcon(imgPrincipal);
        lblImgPrincipal.setOpaque(true);
        lblImgPrincipal.setLocation(-150,100);

        pnlprimeraSeccion.add(lblImgPrincipal);

        txtId.setSize(100, 30);
        txtId.setLocation(290, 0);
        txtId.setText("ID:        321");
        txtId.setEditable(false);
        txtId.setBorder(null);
        txtId.setBackground(Color.decode("#F7F7F7"));
        txtId.setBorder(new EmptyBorder(0,15, 0, 0));

        txtCodigo.setSize(170, 30);
        txtCodigo.setLocation(430, 0);
        txtCodigo.setText("Codigo:       3474372721");
        txtCodigo.setEditable(false);
        txtCodigo.setBorder(null);
        txtCodigo.setBackground(Color.decode("#F7F7F7"));
        txtCodigo.setBorder(new EmptyBorder(0,15, 0, 0));

        lblVenta.setSize(70, 30);
        lblVenta.setLocation(290, 60);
        lblVenta.setText("Venta: ");
        lblVenta.setOpaque(true);
        lblVenta.setBorder(new EmptyBorder(0, 15, 0, 0));
        lblVenta.setBackground(Color.decode("#FFFFFF"));

        lblCantidad.setSize(75, 30);
        lblCantidad.setLocation(530, 60);
        lblCantidad.setText("Cantidad");
        lblCantidad.setOpaque(true);
        lblCantidad.setBorder(new EmptyBorder(0, 15, 0, 0));
        lblCantidad.setBackground(Color.decode("#FFFFFF"));

        lblFecha.setSize(65, 30);
        lblFecha.setLocation(290, 120);
        lblFecha.setText("Fecha: ");
        lblFecha.setOpaque(true);
        lblFecha.setBorder(new EmptyBorder(0,15,0,0));
        lblFecha.setBackground(Color.decode("#FFFFFF"));

        txtFecha.setSize(120, 30);
        txtFecha.setLocation(355, 120);
        txtFecha.setText("  2000/08/24");
        txtFecha.setBorder(null);
        txtFecha.setBorder(bordeIzquierdoJlabel);

        lblHora.setSize(70, 30);
        lblHora.setLocation(495, 120);
        lblHora.setText("Hora: ");
        lblHora.setOpaque(true);
        lblHora.setBorder(new EmptyBorder(0,15,0,0));
        lblHora.setBackground(Color.decode("#FFFFFFF"));

        txtHora.setSize(140, 30);
        txtHora.setLocation(565, 120);
        txtHora.setText(" 13:26:05 Hrs");
        txtHora.setBorder(null);
        txtHora.setBorder(bordeIzquierdoJlabel);

        lblVendedor.setText("Vendedor: ");
        lblVendedor.setSize(100, 30);
        lblVendedor.setLocation(290, 190);
        lblVendedor.setOpaque(true);
        lblVendedor.setBackground(Color.decode("#FFFFFF"));
        lblVendedor.setBorder(new EmptyBorder(0,15,0,0));

        jccVendedor.setSize(150, 30);
        jccVendedor.setLocation(390, 190);
        jccVendedor.addItem("Juan Perez Torres");
        jccVendedor.addItem("Carlos");
        jccVendedor.addItem("Lupe");
        jccVendedor.addItem("Carla");
        jccVendedor.addItem("Tomas");
        jccVendedor.addItem("Gilberto");
        jccVendedor.setBorder(bordeIzquierdoJlabel);
        jccVendedor.setFocusable(false);

        jccVenta.setSize(150, 30);
        jccVenta.setLocation(360, 60);
        jccVenta.addItem("Papas adobadas");
        jccVenta.addItem("Doritos");
        jccVenta.addItem("Chetos");
        jccVenta.addItem("Rufles");
        jccVenta.addItem("Toreadas");
        jccVenta.setOpaque(true);
        jccVenta.setFocusable(false);
        jccVenta.setBorder(null);
        jccVenta.setBorder(bordeIzquierdoJlabel);
        jccVenta.setBackground(Color.decode("#FFFFFF"));

        jccCantidad.setSize(100,30);
        jccCantidad.setLocation(605, 60);
        jccCantidad.addItem("1");
        jccCantidad.addItem("2");
        jccCantidad.addItem("3");
        jccCantidad.addItem("4");
        jccCantidad.addItem("5");
        jccCantidad.addItem("6");
        jccCantidad.setBorder(null);
        jccCantidad.setBorder(bordeIzquierdoJlabel);
        jccCantidad.setFocusable(false);

        lblCliente.setSize(100, 30);
        lblCliente.setLocation(290, 260);
        lblCliente.setText("Cliente: ");
        lblCliente.setOpaque(true);
        lblCliente.setBackground(Color.decode("#FFFFFF"));
        lblCliente.setBorder(new EmptyBorder(0,15,0,0));

        jccCliente.setSize(150, 30);
        jccCliente.setLocation(390, 260);
        jccCliente.setBorder(bordeIzquierdoJlabel);
        jccCliente.addItem("Carlos Perez Trujillo");
        jccCliente.addItem("Andres Aguilar Perez");
        jccCliente.addItem("Tomas Grutierrez Gomez");

        txtUnitario.setText("Precio unitario:    $19.50");
        txtUnitario.setSize(150, 30);
        txtUnitario.setLocation(290, 340);
        txtUnitario.setEditable(false);
        txtUnitario.setBorder(new EmptyBorder(0,15,0,0));
        txtUnitario.setBackground(Color.decode("#F7F7F7"));

        txtPrecioTotal.setText("Precio Total:   $36.50");
        txtPrecioTotal.setSize(150, 30);
        txtPrecioTotal.setLocation(290, 410);
        txtPrecioTotal.setEditable(false);
        txtPrecioTotal.setBorder(new EmptyBorder(0,15,0,0));
        txtPrecioTotal.setBackground(Color.decode("#F7F7F7"));

        btnHecho.setSize(250, 40);
        btnHecho.setLocation(450, 500);
        btnHecho.setText("HECHO");
        btnHecho.setForeground(Color.decode("#FFFFFFF"));
        btnHecho.setBackground(Color.decode("#0984E3"));
        btnHecho.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dlgModRegistro.dispose();
                JOptionPane.showMessageDialog(pnlPrincipalCompras, "        El producto se ha modificado correctamente.\n Recuerda que ahora la informacion del producto ha \n               sido actualizada en el sistema.","Hecho",JOptionPane.INFORMATION_MESSAGE , imgHecho);

            }
        });

        lblnameModificacion.setSize(200, 25);
        lblnameModificacion.setLocation(15,15);
        lblnameModificacion.setText("Modificacion de registro 321");

        lblAyuda.setSize(60, 20);
        lblAyuda.setLocation(220, 530);
        lblAyuda.setText("Ayuda");
        lblAyuda.setFont(new Font("Arial", Font.PLAIN, 11));
        lblAyuda.setForeground(Color.decode("#9D9D9D"));
        lblAyuda.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                lblAyuda.setForeground(Color.decode("#0984E3"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lblAyuda.setForeground(Color.decode("#9D9D9D"));
            }
        });
        pnlprimeraSeccion.add(lblnameModificacion);
        pnlprimeraSeccion.add(lblAyuda);

        dlgModRegistro.setSize(900, 600);
        dlgModRegistro.setVisible(false);
        dlgModRegistro.setTitle("Modificacion de venta");
        dlgModRegistro.add(pnlprimeraSeccion);
        dlgModRegistro.add(txtId);
        dlgModRegistro.add(txtCodigo);
        dlgModRegistro.add(lblVenta);
        dlgModRegistro.add(jccVenta);
        dlgModRegistro.add(lblCantidad);
        dlgModRegistro.add(jccCantidad);
        dlgModRegistro.add(lblFecha);
        dlgModRegistro.add(txtFecha);
        dlgModRegistro.add(lblHora);
        dlgModRegistro.add(txtHora);
        dlgModRegistro.add(lblVendedor);
        dlgModRegistro.add(jccVendedor);
        dlgModRegistro.add(lblCliente);
        dlgModRegistro.add(jccCliente);
        dlgModRegistro.add(txtUnitario);
        dlgModRegistro.add(txtPrecioTotal);
        dlgModRegistro.add(btnHecho);
        dlgModRegistro.setBackground(Color.white);
        dlgModRegistro.setLayout(null);
        dlgModRegistro.setModal(true);
    }
}
