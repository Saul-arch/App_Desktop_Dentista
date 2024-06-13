package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import modelos.Consulta;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Vector;

public class ConsultasDent {
    JTextArea area = new JTextArea();
    JTextField id_consulta = new JTextField();
    JTextField txtFecha = new JTextField();
    JTextField txtHora = new JTextField();
    public Vector<Vector<Object>> dataConsultas = new Vector<Vector<Object>>();
    public JTable tblTablaVentas = new JTable();
    public Vector<String> columnasVentas = new Vector<String>();
    public static String Url = "http://localhost:8081/getConsultas";
    public static HttpClient cliente = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

    public static ObjectMapper mapper = new ObjectMapper();

    public JDialog dlgModRegistro = new JDialog();
    public JPanel pnlPrincipalVentas = new JPanel();
    public JPanel pnlContentBusCanc = new JPanel();
    public JPanel pnlAddVentaPrincipal = new JPanel();
    public Consultas consultasDentistas = new Consultas();
    public String selectVentas = "select * from ventas";
    public String precentacion;
    public String medida;
    public DefaultTableModel modelTablaNuevaVenta;
    public Statement st;
    public ConsultasDent(JFrame jfrPrincipal, Statement st){
        this.st = st;
        iniciarVista(jfrPrincipal);
        if(st == null){
            System.out.println("EL ST DE VENTAS SI ES NULL");
        }else{
            System.out.println("EL ST DE VENTAS NO ES NULL");
        }
    }

    private void iniciarVista(JFrame jfrPrincipal) {
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        Vector<Vector<Object>> dataRegistrosVentas = new Vector<Vector<Object>>();
        DefaultTableModel model;
        JScrollPane scpContenedorJtable = new JScrollPane();

        /* Componentes necesarios para la creacion de la pantalla de nueva venta*/
        JPanel pnlAddVentaIzquierda = new JPanel();
        JPanel pnlAddVentaDerecha = new JPanel();
        JPanel pnlGenerarRecibo = new JPanel();

        Vector<String> columnasNuevaVenta = new Vector<String>();
        JTable tblTablaNuevaVentas = new JTable();
        DefaultTableModel modelNuevaVenta;
        JScrollPane scpContenedorNuevaVentaJtable = new JScrollPane();

        JLabel lblTextProductos = new JLabel("PRODUCTOS");
        JLabel lblBuscarProducto = new JLabel("Buscar producto:");
        JLabel lblTextProducto = new JLabel("Producto:");
        JLabel lblPrecio = new JLabel("Precio:");
        JLabel lblCantidad = new JLabel("Cantidad:");
        JLabel lblClientes = new JLabel("Cliente:");
        JLabel lblUsuario = new JLabel("Administrador");
        JLabel lblFechaNuevaVenta = new JLabel("Fecha: 01/09/2023");
        JLabel lblHora = new JLabel("Hora: 8:34:54");
        JLabel lblCliente = new JLabel("Cliente: Sin cliente registrado");
        JLabel lblDescuento = new JLabel("Descuento por compra: Ninguno");
        JLabel lblTotal = new JLabel("TOTAL: ");
        JLabel lblSumTotal = new JLabel("$120.50");

        JTextField txtBusqueda = new JTextField("Buscar...");
        JTextField txtProducto = new JTextField("Sin producto seleccionado");
        JTextField txtPrecio = new JTextField("$ ");
        JTextField txtCantidad = new JTextField("1");

        JButton btnModificarVentaProd = new JButton("Modificar");
        JButton btnBorrarVentaProd = new JButton("Borrar");
        JButton btnOkVentaProd = new JButton("OK");
        JButton btnGenerar = new JButton("Generar recibo");
        JButton btnBuscarProduct = new JButton();

        JComboBox<String> cbbClientes = new JComboBox<String>();

        /*----------------------------------------------------------------------*/

        columnasVentas.add("Numero consulta");
        columnasVentas.add("Clave dentista");
        columnasVentas.add("Clave paciente");
        columnasVentas.add("Fecha");
        columnasVentas.add("Observaciones");
        columnasVentas.add("Hora");

        columnasNuevaVenta.add("Producto");
        columnasNuevaVenta.add("Presentacion");
        columnasNuevaVenta.add("Medida");
        columnasNuevaVenta.add("Cantidad");
        columnasNuevaVenta.add("Total");

        model = new DefaultTableModel(data, columnasVentas);
        tblTablaVentas.setModel(model);

        scpContenedorJtable.setSize(1260, 620);
        scpContenedorJtable.setLocation(20,115);
        scpContenedorJtable.setViewportView(tblTablaVentas);

        modelNuevaVenta = new DefaultTableModel(data, columnasNuevaVenta);
        tblTablaNuevaVentas.setModel(modelNuevaVenta);

        scpContenedorNuevaVentaJtable.setSize(750, 470);
        scpContenedorNuevaVentaJtable.setLocation(0,245);
        scpContenedorNuevaVentaJtable.setViewportView(tblTablaNuevaVentas);

        pnlPrincipalVentas.setSize(1300, 870);
        pnlPrincipalVentas.setLocation(209, 30);
        pnlPrincipalVentas.setLayout(null);
        pnlPrincipalVentas.setVisible(false);

        pnlAddVentaIzquierda.setSize(750, 870);
        pnlAddVentaIzquierda.setLocation(0, 0);
        pnlAddVentaIzquierda.setLayout(null);
        pnlAddVentaIzquierda.setBackground(Color.white);
        pnlAddVentaIzquierda.setVisible(true);

        pnlAddVentaDerecha.setSize(550, 870);
        pnlAddVentaDerecha.setLocation(750, 0);
        pnlAddVentaDerecha.setLayout(null);
        pnlAddVentaDerecha.setBackground(Color.decode("#88FBD9"));
        pnlAddVentaDerecha.setVisible(true);

        pnlGenerarRecibo.setSize(550, 70);
        pnlGenerarRecibo.setLocation(0, 735);
        pnlGenerarRecibo.setLayout(null);
        pnlGenerarRecibo.setBackground(Color.decode("#88FBD9"));
        Border bordeSuperior = BorderFactory.createMatteBorder(2, 0, 0, 0, Color.decode("#FAFAFA"));
        pnlGenerarRecibo.setBorder(bordeSuperior);
        pnlGenerarRecibo.setVisible(true);

        pnlAddVentaPrincipal.setSize(1300, 870);
        pnlAddVentaPrincipal.setLocation(209, 30);
        pnlAddVentaPrincipal.setLayout(null);
        pnlAddVentaPrincipal.setVisible(false);

        File rutaImgCancelSearchVenta = new File("C:\\Users\\pc\\IdeaProjects\\Sistema_ventas\\Imagenes\\cancelarSearchVentas.svg");
        File rutaImgSearchVenta = new File("C:\\Users\\pc\\IdeaProjects\\Sistema_ventas\\Imagenes\\search_venta.svg");
        File rutaSettingsVentas = new File("C:\\Users\\pc\\IdeaProjects\\Sistema_ventas\\Imagenes\\setting_ventas.svg");
        File rutaImgNotResult = new File("C:\\Users\\pc\\IdeaProjects\\Sistema_ventas\\Imagenes\\notResultVentas.svg");
        FlatSVGIcon imgSettingsVentas = new FlatSVGIcon(rutaSettingsVentas);
        FlatSVGIcon imgSearchVenta = new FlatSVGIcon(rutaImgSearchVenta);
        FlatSVGIcon imgCancelSearch = new FlatSVGIcon(rutaImgCancelSearchVenta);
        FlatSVGIcon imgNotResultVenta = new FlatSVGIcon(rutaImgNotResult);

        LocalDate fechaActual = LocalDate.now();
        int dia = fechaActual.getDayOfMonth();
        int mes = fechaActual.getMonthValue();
        int anio = fechaActual.getYear();

        JLabel lblFecha = new JLabel();
        JLabel lblImgSettings = new JLabel();
        JLabel lblImgSearch = new JLabel();
        JLabel lblImgCancelSearch = new JLabel();
        JLabel lblImgNotResult = new JLabel();
        JTextField txtBusquedaVenta = new JTextField();
        JLabel lblSinResultados = new JLabel("SIN RESULTADOS");
        JButton btnAgregarVenta = new JButton();
        JButton btnDetallesVenta = new JButton();
        JButton btnEditarVenta = new JButton();
        JButton btnBorrarConsulta = new JButton();

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

        txtBusquedaVenta.setSize(900, 40);
        txtBusquedaVenta.setLocation(20, 70);
        txtBusquedaVenta.setText("Buscar....");
        txtBusquedaVenta.setFont(new Font("Arial", Font.PLAIN, 14));
        txtBusquedaVenta.setForeground(Color.decode("#ACACAC"));
        txtBusquedaVenta.setBackground(Color.decode("#F5F5F5"));
        txtBusquedaVenta.add(pnlContentBusCanc);
        txtBusquedaVenta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {


            }
        });

        txtBusquedaVenta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                txtBusquedaVenta.setCaretPosition(0);
                txtBusquedaVenta.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        lblImgCancelSearch.setVisible(true);
                        txtBusquedaVenta.setText("");
                        txtBusquedaVenta.removeKeyListener(this); // Remueve el KeyListener después del primer evento.
                    }
                });
                txtBusquedaVenta.removeMouseListener(this);
            }
        });

        btnAgregarVenta.setSize(120, 40);
        btnAgregarVenta.setText("Agregar venta");
        btnAgregarVenta.setLocation(935, 70);
        btnAgregarVenta.setBackground(Color.decode("#64D3B2"));
        btnAgregarVenta.setForeground(Color.decode("#FFFFFF"));
        btnAgregarVenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String consultaRefreshClientes = "select nombre from clientes";
                pnlPrincipalVentas.setVisible(false);
                /*
                * cbbClientes.addItem("Cesar");
                cbbClientes.addItem("jjjhjhjkj");
                cbbClientes.addItem("jjjhjhjkj");
                cbbClientes.addItem("jjjhjhjkj");
                * */
                consultasDentistas.refreshComboBox(st, consultaRefreshClientes, "cbbNuevaVentaClientes", cbbClientes);
                pnlAddVentaPrincipal.setVisible(true);
            }
        });

        btnDetallesVenta.setSize(120, 40);
        btnDetallesVenta.setText("Detalles");
        btnDetallesVenta.setLocation(1080, 70);
        btnDetallesVenta.setBackground(Color.decode("#FFFFFF"));
        btnDetallesVenta.setForeground(Color.decode("#00000"));

        btnEditarVenta.setSize(120, 40);
        btnEditarVenta.setText("Editar");
        btnEditarVenta.setLocation(935, 740);
        btnEditarVenta.setBackground(Color.decode("#64D3B2"));
        btnEditarVenta.setForeground(Color.decode("#FFFFFF"));
        btnEditarVenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*Llamar a metodo y hacer visible el JDialog*/
                ModRegistro(jfrPrincipal);

                int x = jfrPrincipal.getX() + 410;
                int y = jfrPrincipal.getY() + 200;

                dlgModRegistro.setLocation(x, y);
                dlgModRegistro.setVisible(true);
            }
        });

        btnBorrarConsulta.setSize(120, 40);
        btnBorrarConsulta.setText("Borrar");
        btnBorrarConsulta.setLocation(1100, 740);
        btnBorrarConsulta.setBackground(Color.decode("#64D3B2"));
        btnBorrarConsulta.setForeground(Color.decode("#FFFFFF"));
        btnBorrarConsulta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int column = tblTablaVentas.getSelectedRow();

                int id = Integer.parseInt(tblTablaVentas.getValueAt(column, 0).toString());

                int respuesta = JOptionPane.showConfirmDialog(null, "Desea confirma borrar esta consulta?", "confirmacion de eliminacion", JOptionPane.YES_NO_OPTION);

                // Comprobar la respuesta del usuario
                if (respuesta == JOptionPane.YES_OPTION) {
                    if (consultasDentistas.deleteRecord(id)){
                        System.out.println("El registro se ha borrado correctamente");
                        refreshConsultasDentist();
                        consultasDentistas.refreshDatosApi(dataConsultas, tblTablaVentas, columnasVentas, "consulta_dentistas");


                    }else {

                        System.out.println("Ah ocurrido un error al intentar borrar la consulta");
                    }


                } else if (respuesta == JOptionPane.NO_OPTION) {
                    JOptionPane.showMessageDialog(null, "Cancelado");



                } else {
                    System.out.println("El usuario cerró el cuadro de diálogo");
                }

            }
        });

        lblImgNotResult.setSize(1200, 550);
        lblImgNotResult.setLocation(250,130);
        lblImgNotResult.setLayout(null);
        lblImgNotResult.setIcon(imgNotResultVenta);
        lblImgNotResult.setVisible(false);


        lblImgSettings.setSize(55, 65);
        lblImgSettings.setIcon(imgSettingsVentas);
        lblImgSettings.setLocation(1230, 10);

        lblImgSearch.setSize(40, 40);
        lblImgSearch.setLocation(45,10);
        lblImgSearch.setIcon(imgSearchVenta);
        lblImgSearch.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                scpContenedorJtable.setVisible(false);
                lblImgNotResult.setVisible(true);
                txtBusquedaVenta.setText("Buscar....");
                txtBusquedaVenta.setFocusable(false);
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
                txtBusquedaVenta.setText("Buscar....");
                txtBusquedaVenta.setCaretPosition(0);
                txtBusquedaVenta.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        lblImgCancelSearch.setVisible(true);
                        txtBusquedaVenta.setText("");
                        txtBusquedaVenta.removeKeyListener(this); // Remueve el KeyListener después del primer evento.
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

        lblTextProductos.setSize(150, 30);
        lblTextProductos.setFont(new Font("", Font.PLAIN, 16));
        lblTextProductos.setLocation(30, 30);
        lblTextProductos.setForeground(Color.decode("#06A2C4"));

        lblBuscarProducto.setSize(180, 20);
        lblBuscarProducto.setLocation(30, 70);

        lblTextProducto.setSize(100, 20);
        lblTextProducto.setLocation(30, 130);

        txtBusqueda.setSize(300, 30);
        txtBusqueda.setLocation(130, 65);

        txtProducto.setSize(150, 30);
        txtProducto.setLocation(100, 125);
        txtProducto.setEditable(false);

        lblPrecio.setSize(60, 20);
        lblPrecio.setLocation(260, 130);

        txtPrecio.setSize(80, 30);
        txtPrecio.setLocation(300, 125);
        txtPrecio.setEditable(false);

        lblCantidad.setSize(60, 20);
        lblCantidad.setLocation(400, 130);

        txtCantidad.setSize(70, 30);
        txtCantidad.setLocation(470, 125);

        lblClientes.setSize(70, 20);
        lblClientes.setLocation(30, 183);

        cbbClientes.setSize(180, 30);
        cbbClientes.setLocation(90, 180);

        btnModificarVentaProd.setSize(150, 40);
        btnModificarVentaProd.setLocation(30, 745);
        btnModificarVentaProd.setBackground(Color.decode("#F0DFA4"));

        btnBorrarVentaProd.setSize(150, 40);
        btnBorrarVentaProd.setLocation(200, 745);
        btnBorrarVentaProd.setBackground(Color.decode("#E57474"));

        btnOkVentaProd.setSize(150, 40);
        btnOkVentaProd.setLocation(400, 745);
        btnOkVentaProd.setBackground(Color.decode("#C7C7C7"));
        btnOkVentaProd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vector<Object> row = new Vector<Object>();
                row.add(txtProducto.getText());
                row.add(precentacion);
                row.add(consultasDentistas.medida);
                row.add(txtCantidad.getText());
                Float total = Integer.parseInt(txtCantidad.getText()) * Float.parseFloat(txtPrecio.getText());
                row.add(total);
                dataRegistrosVentas.add(row);

                modelTablaNuevaVenta = new DefaultTableModel(dataRegistrosVentas, columnasNuevaVenta);

                int rowCount = modelTablaNuevaVenta.getRowCount();
                System.out.println("Cantidad de fila "+rowCount);

                // Inicializa la variable para almacenar la suma
                float suma = 0;

                // Itera sobre todas las filas
                for (int i = 0; i < rowCount; i++) {
                    // Obtén el valor de la última columna en la fila actual
                    Object valor = modelTablaNuevaVenta.getValueAt(i, modelTablaNuevaVenta.getColumnCount() -1);
                    System.out.println("El valor"+ valor);
                    suma += ((Number) valor).doubleValue();

                    // Convierte el valor a un número (en este caso, asumimos que es un Double)
                }

                lblSumTotal.setText("$"+suma);

                tblTablaNuevaVentas.setModel(modelTablaNuevaVenta);
                tblTablaNuevaVentas.repaint();
            }
        });

        /*
        JLabel lblUsuario = new JLabel("Administrador");
        JLabel lblFechaNuevaVenta = new JLabel("Fecha: 01/09/2023");
        JLabel lblHora = new JLabel("Hora: 8:34:54");
        JLabel lblCliente = new JLabel("Cliente: Sin cliente registrado");
        JLabel lblDescuento = new JLabel("Descuento por compra: Ninguno");
        JLabel lblTotal = new JLabel("TOTAL: ");
         */

        lblUsuario.setSize(150, 20);
        lblUsuario.setLocation(400, 10);

        lblFechaNuevaVenta.setSize(180, 30);
        lblFechaNuevaVenta.setLocation(30, 50);
        lblFechaNuevaVenta.setFont(new Font("", Font.ROMAN_BASELINE, 15));
        lblFechaNuevaVenta.setForeground(Color.decode("#616161"));

        lblHora.setSize(120, 30);
        lblHora.setLocation(30, 90);
        lblHora.setFont(new Font("", Font.ROMAN_BASELINE, 15));
        lblHora.setForeground(Color.decode("#616161"));

        lblCliente.setSize(280, 30);
        lblCliente.setLocation(30, 130);
        lblCliente.setFont(new Font("", Font.ROMAN_BASELINE, 15));
        lblCliente.setForeground(Color.decode("#616161"));

        lblDescuento.setSize(250, 30);
        lblDescuento.setLocation(30, 170);
        lblDescuento.setFont(new Font("", Font.ROMAN_BASELINE, 15));
        lblDescuento.setForeground(Color.decode("#616161"));

        lblTotal.setSize(150, 30);
        lblTotal.setLocation(30, 695);
        lblTotal.setFont(new Font("", Font.PLAIN, 18));

        btnGenerar.setSize(150, 43);
        btnGenerar.setLocation(30, 10);
        btnGenerar.setBackground(Color.decode("#F28989"));
        btnGenerar.setForeground(Color.white);
        btnGenerar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consultasDentistas.agregarVenta(st, tblTablaNuevaVentas, modelTablaNuevaVenta, cbbClientes.getSelectedItem().toString());
                consultasDentistas.refresh(st, tblTablaVentas, columnasVentas, selectVentas, "ventas");
                //System.out.println("Dato de la tabla.."+tblTablaNuevaVentas.getValueAt(0, 0));
                //int rowCount = modelTablaNuevaVenta;.getRowCount().;
                //System.out.println("Cantidad de fila "+rowCount);


            }
        });

        lblSumTotal.setSize(150, 30);
        lblSumTotal.setLocation(450, 35);
        lblSumTotal.setFont(new Font("", Font.PLAIN, 20));

        btnBuscarProduct.setSize(50, 30);
        btnBuscarProduct.setLocation(440, 65);
        btnBuscarProduct.setText("Buscar");
        btnBuscarProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ConsultBuscarProd = "select codigo_barr, nombre, precio_unit, presentacion, unidad_medida from inventario where codigo_barr = "+txtBusqueda.getText();
                System.out.println(ConsultBuscarProd);
                precentacion = consultasDentistas.buscarProductoNuevaVenta(st, ConsultBuscarProd, txtProducto, txtPrecio, precentacion, medida);
            }
        });

        pnlContentBusCanc.add(lblImgSearch);
        pnlContentBusCanc.add(lblImgCancelSearch);

        pnlGenerarRecibo.add(btnGenerar);
        pnlGenerarRecibo.add(lblSumTotal);

        pnlAddVentaDerecha.add(lblUsuario);
        pnlAddVentaDerecha.add(lblFechaNuevaVenta);
        pnlAddVentaDerecha.add(lblHora);
        pnlAddVentaDerecha.add(lblCliente);
        pnlAddVentaDerecha.add(lblDescuento);
        pnlAddVentaDerecha.add(lblTotal);
        pnlAddVentaDerecha.add(pnlGenerarRecibo);
        pnlAddVentaDerecha.add(pnlGenerarRecibo);

        pnlAddVentaIzquierda.add(lblTextProductos);
        pnlAddVentaIzquierda.add(lblBuscarProducto);
        pnlAddVentaIzquierda.add(lblTextProducto);
        pnlAddVentaIzquierda.add(txtBusqueda);
        pnlAddVentaIzquierda.add(txtProducto);
        pnlAddVentaIzquierda.add(lblPrecio);
        pnlAddVentaIzquierda.add(txtPrecio);
        pnlAddVentaIzquierda.add(lblCantidad);
        pnlAddVentaIzquierda.add(txtCantidad);
        pnlAddVentaIzquierda.add(scpContenedorNuevaVentaJtable);
        pnlAddVentaIzquierda.add(btnModificarVentaProd);
        pnlAddVentaIzquierda.add(btnBorrarVentaProd);
        pnlAddVentaIzquierda.add(btnOkVentaProd);
        pnlAddVentaIzquierda.add(lblClientes);
        pnlAddVentaIzquierda.add(cbbClientes);
        pnlAddVentaIzquierda.add(btnBuscarProduct);

        pnlAddVentaPrincipal.add(pnlAddVentaIzquierda);
        pnlAddVentaPrincipal.add(pnlAddVentaDerecha);

        pnlPrincipalVentas.add(lblFecha);
        pnlPrincipalVentas.add(txtBusquedaVenta);
        pnlPrincipalVentas.add(btnAgregarVenta);
        pnlPrincipalVentas.add(btnDetallesVenta);
        pnlPrincipalVentas.add(btnEditarVenta);
        pnlPrincipalVentas.add(btnBorrarConsulta);
        pnlPrincipalVentas.add(lblImgSettings);
        pnlPrincipalVentas.add(lblImgNotResult);
        pnlPrincipalVentas.add(lblSinResultados);
        pnlPrincipalVentas.add(scpContenedorJtable);

        //Aqui debemos Extraer la informacion de la API  de Spring Boot (Ya que acabe quiatar comentario a la linea de abajo)

        refreshConsultasDentist();


        //consultasVentas.refresh(st, tblTablaVentas, columnasVentas, selectVentas, "ventas");

        jfrPrincipal.add(pnlPrincipalVentas);
        jfrPrincipal.add(pnlAddVentaPrincipal);
    }
    public void refreshConsultasDentist(){
        HttpRequest peticion = HttpRequest.newBuilder().GET().
                uri(URI.create(Url)) .build();

        try {
            HttpResponse<String> response = cliente.send(peticion, HttpResponse.BodyHandlers.ofString());

            java.util.List<Consulta> empleadoLista = getData(response.body(), new TypeReference<List<Consulta>>() {


            });

            dataConsultas.clear();


            for (Consulta consulta : empleadoLista) {
                Object datos[] = {consulta.getCon_clave(), consulta.getDen_clave(), consulta.getPac_clave()
                        , consulta.getFecha(), consulta.getObservaciones(), consulta.getHora(), consulta.getPendiente(), consulta.getServicio()
                };

                System.out.println("el servicio fue: "+consulta.getFecha());

                Vector<Object> row = new Vector<>();

                row.add(consulta.getCon_clave());
                row.add(consulta.getDen_clave());
                row.add(consulta.getPac_clave());
                row.add(consulta.getFecha());
                row.add(consulta.getObservaciones());
                row.add(consulta.getHora());

                dataConsultas.add(row);
            }
            //Aqui debi de meter los datos de la API a la Jtable
            consultasDentistas.refreshDatosApi(dataConsultas, tblTablaVentas, columnasVentas, "consulta_dentistas");

        }catch (Exception e){
            System.out.println("El error es e"+e);
        }

    }
    public static <T> T getData(String json, TypeReference<T> referencia){

        try {
            return mapper.readValue(json, referencia);

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
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
        File rutaImgPrincipalEditVenta = new File("C:\\Users\\pc\\Downloads\\demo\\Groggy\\imagenes_dent\\edit_dlg.svg");
        FlatSVGIcon imgPrincipal = new FlatSVGIcon(rutaImgPrincipalEditVenta);
        JLabel lblImgPrincipal = new JLabel();
        File rutaImgHecho = new File("C:\\Users\\pc\\Downloads\\demo\\Groggy\\imagenes_dent\\updateconsultimg.svg");
        FlatSVGIcon imgHecho = new FlatSVGIcon(rutaImgHecho);

        JTextField txtCodPaciente = new JTextField();
        JLabel lblVenta = new JLabel();
        JComboBox<String> jccVenta = new JComboBox<>();
        JLabel lblCantidad = new JLabel();
        JComboBox<String> jccCantidad = new JComboBox<>();
        JLabel lblFecha = new JLabel();
        JLabel lblHora = new JLabel();
        JLabel lblVendedor = new JLabel();
        JComboBox<String> jccDentistas = new JComboBox<>();
        JLabel lblServicio = new JLabel();
        JComboBox<String> jccServicio = new JComboBox<>();
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

        lblImgPrincipal.setSize(480, 400);
        lblImgPrincipal.setIcon(imgPrincipal);
        lblImgPrincipal.setOpaque(true);
        lblImgPrincipal.setLocation(25,70);

        pnlprimeraSeccion.add(lblImgPrincipal);

        actualizarId_Consulta();


        txtCodPaciente.setSize(170, 30);
        txtCodPaciente.setLocation(430, 0);
        txtCodPaciente.setText("ID Paciente:      "+getIdPaciente());
        txtCodPaciente.setEditable(false);
        txtCodPaciente.setBorder(null);
        txtCodPaciente.setBackground(Color.decode("#F7F7F7"));
        txtCodPaciente.setBorder(new EmptyBorder(0,15, 0, 0));

        lblFecha.setSize(65, 30);
        lblFecha.setLocation(290, 120);
        lblFecha.setText("Fecha: ");
        lblFecha.setOpaque(true);
        lblFecha.setBorder(new EmptyBorder(0,15,0,0));
        lblFecha.setBackground(Color.decode("#FFFFFF"));

        txtFecha.setSize(120, 30);
        txtFecha.setLocation(355, 120);
        txtFecha.setText("  "+getFechaConsul());
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
        txtHora.setText("  "+getHoraConsult());
        txtHora.setBorder(null);
        txtHora.setBorder(bordeIzquierdoJlabel);

        lblVendedor.setText("Dentista: ");
        lblVendedor.setSize(100, 30);
        lblVendedor.setLocation(290, 190);
        lblVendedor.setOpaque(true);
        lblVendedor.setBackground(Color.decode("#FFFFFF"));
        lblVendedor.setBorder(new EmptyBorder(0,15,0,0));

        jccDentistas.setSize(150, 30);
        jccDentistas.setLocation(390, 190);
        jccDentistas.setBorder(bordeIzquierdoJlabel);
        consultasDentistas.refresCbbDentistas(jccDentistas);
        jccDentistas.setFocusable(false);

        lblServicio.setSize(100, 30);
        lblServicio.setLocation(290, 60);
        lblServicio.setText("Servicio: ");
        lblServicio.setOpaque(true);
        lblServicio.setBackground(Color.decode("#FFFFFF"));
        lblServicio.setBorder(new EmptyBorder(0,15,0,0));

        jccServicio.setSize(150, 30);
        jccServicio.setLocation(390, 60);
        jccServicio.setBorder(bordeIzquierdoJlabel);

        consultasDentistas.refresCbbServicios(jccServicio);

        area.setSize(550, 250);
        area.setLocation(290, 235);
        area.setText(" "+getObservaciones());


        btnHecho.setSize(250, 40);
        btnHecho.setLocation(450, 500);
        btnHecho.setText("HECHO");
        btnHecho.setForeground(Color.decode("#FFFFFFF"));
        btnHecho.setBackground(Color.decode("#0984E3"));
        btnHecho.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{

                    int respuesta = JOptionPane.showConfirmDialog(null, "ESTA SEGURO DE MODIFICAR LA CONSULTA?", "Confirmación", JOptionPane.YES_NO_OPTION);

                    // Comprobar la respuesta del usuario
                    if (respuesta == JOptionPane.YES_OPTION) {
                        //con_clave
                        int id_consultaa = Integer.parseInt(separarNumeros(id_consulta.getText()));
                        //den_clave
                        String dent = jccDentistas.getSelectedItem().toString();
                        int denClave = Integer.parseInt(consultasDentistas.getIdDentistaNombre(dent));
                        //pac_clave
                        int pac_clave = Integer.parseInt(separarNumeros(txtCodPaciente.getText()));
                        //fecha
                        String fecha = txtFecha.getText();
                        //observaciones
                        String observaciones = area.getText();
                        //hora
                        String hora = txtHora.getText();
                        //pendiente es constante de 1
                        //id_servicio
                        String serv = jccServicio.getSelectedItem().toString();
                        int denServ = Integer.parseInt(consultasDentistas.getIdServ(serv));

                        Consulta c = new Consulta(id_consultaa, denClave, pac_clave, fecha, observaciones, hora, "1", denServ);

                        consultasDentistas.actualizarConsulta(c);

                        refreshConsultasDentist();
                        //consultasDentistas.refreshDatosApi(dataConsultas, tblTablaVentas, columnasVentas, "consulta_dentistas");

                        dlgModRegistro.dispose();
                        JOptionPane.showMessageDialog(pnlPrincipalVentas, "        Tu registro ha sido actualizado con éxito.\n Recuerda que ahora tus datos dentales están \n             actualizados en nuestro sistema.","Hecho",JOptionPane.INFORMATION_MESSAGE , imgHecho);

                    } else if (respuesta == JOptionPane.NO_OPTION) {

                        System.out.println("El vato cancelo la actualizacion");

                    } else {
                        System.out.println("El usuario cerró el cuadro de diálogo");
                    }



                }catch (Exception ext){
                    System.out.println(ext);
                }
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

        consultasDentistas.refreshServicios();


        dlgModRegistro.setSize(900, 600);
        dlgModRegistro.setVisible(false);
        dlgModRegistro.setTitle("Modificacion de venta");
        dlgModRegistro.add(pnlprimeraSeccion);
        dlgModRegistro.add(id_consulta);
        dlgModRegistro.add(txtCodPaciente);
        dlgModRegistro.add(lblVenta);
        dlgModRegistro.add(jccVenta);
        dlgModRegistro.add(lblCantidad);
        dlgModRegistro.add(jccCantidad);
        dlgModRegistro.add(lblFecha);
        dlgModRegistro.add(txtFecha);
        dlgModRegistro.add(lblHora);
        dlgModRegistro.add(txtHora);
        dlgModRegistro.add(lblVendedor);
        dlgModRegistro.add(jccDentistas);
        dlgModRegistro.add(lblServicio);
        dlgModRegistro.add(jccServicio);
        dlgModRegistro.add(area);
        dlgModRegistro.add(btnHecho);
        dlgModRegistro.setBackground(Color.white);
        dlgModRegistro.setLayout(null);
        dlgModRegistro.setModal(true);
        dlgModRegistro.repaint();
        System.out.println("VOLVIO A ENTRAR A LA FUNCION");
    }

    private void actualizarId_Consulta() {

        id_consulta.removeAll();
        id_consulta.setSize(130, 30);
        id_consulta.setLocation(290, 0);
        id_consulta.setText("");
        id_consulta.setText("No.Consulta:      "+getIdConsulta());
        id_consulta.setEditable(false);
        id_consulta.setBorder(null);
        id_consulta.setBackground(Color.decode("#F7F7F7"));
        id_consulta.setBorder(new EmptyBorder(0,15, 0, 0));
        id_consulta.repaint();



    }

    private String separarNumeros(String idConsulta) {

        String numbers = "";

        for (int i = 0; i < idConsulta.length(); i++) {
            char caracter = idConsulta.charAt(i);

            // Verificar si el caracter es un dígito
            if (Character.isDigit(caracter)) {
                numbers = numbers+caracter;
            }
        }

        System.out.println("La cadena con numeros encontrados son: "+numbers);

        return numbers;

    }

    private String getObservaciones() {
        int column = tblTablaVentas.getSelectedRow();
        int id = Integer.parseInt(tblTablaVentas.getValueAt(column, 0).toString());

        String observaciones = consultasDentistas.getRegistroConsulta(id);

        return observaciones;
    }

    private String getHoraConsult() {
        int column = tblTablaVentas.getSelectedRow();

        return tblTablaVentas.getValueAt(column, 5).toString();
    }

    private String getFechaConsul() {
        int column = tblTablaVentas.getSelectedRow();

        return tblTablaVentas.getValueAt(column, 3).toString();
    }

    private int getIdPaciente() {
        int column = tblTablaVentas.getSelectedRow();
        int id = Integer.parseInt(tblTablaVentas.getValueAt(column, 2).toString());

        return id;
    }

    private int getIdConsulta() {
        int column = tblTablaVentas.getSelectedRow();
        int id = Integer.parseInt(tblTablaVentas.getValueAt(column, 0).toString());

        System.out.println("El ide de la consulta es: "+id);
        return id;
    }
}

