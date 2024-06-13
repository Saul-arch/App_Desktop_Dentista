package org.example;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.Statement;
import java.util.Vector;

public class Pacientes {
    Vector<String> columnasClientes = new Vector<String>();
    JTable tblTabla = new JTable();
    public JDialog dlgAddCliente = new JDialog();
    JPanel pnlPrincipalClientes = new JPanel();
    JFrame jfrPrincipal = new JFrame();
    public Consultas consultasClientes = new Consultas();
    public Statement st;
    public String consultaClientes = "select * from clientes";
    public Pacientes(JFrame framePrincipal, Statement st){
        this.st = st;
        iniciarVista(framePrincipal);
        this.jfrPrincipal = framePrincipal;
    }

    private void iniciarVista(JFrame framePrincipal) {
        JPanel rankEmpleados = new JPanel();
        JPanel ventas = new JPanel();

        rankEmpleados.setSize(600, 300);
        rankEmpleados.setLocation(15, 20);
        rankEmpleados.setLayout(null);
        rankEmpleados.setBackground(Color.white);

        ventas.setSize(622, 300);
        ventas.setLocation(650, 20);
        ventas.setLayout(null);
        ventas.setBackground(Color.white);

        /*Necasario para la grafica de barras*/
        DefaultCategoryDataset datasetBarras = new DefaultCategoryDataset();
        datasetBarras.addValue(1150, "Martin", "");
        datasetBarras.addValue(1000, "Sergio", "");
        datasetBarras.addValue(500, "Saul", "");
        datasetBarras.addValue(1350, "Perez", "");
        datasetBarras.addValue(700, "Teodoro", "");
        datasetBarras.addValue(300, "Daniel", "");
        datasetBarras.addValue(853, "Juan", "");

        JFreeChart barChart = ChartFactory.createBarChart(
                "",
                "",
                "",
                datasetBarras,
                PlotOrientation.VERTICAL,
                true, // No incluir leyenda para eliminar los recuadros de colores
                true,
                false
        );

        CategoryPlot plotB = barChart.getCategoryPlot();
        plotB.setBackgroundPaint(Color.white);
        plotB.setOutlineVisible(false);

        BarRenderer renderB = (BarRenderer) plotB.getRenderer();
        renderB.setSeriesPaint(0, Color.decode("#64D3B2"));
        renderB.setSeriesPaint(1, Color.decode("#EDA6A6"));
        renderB.setSeriesPaint(2, Color.decode("#99A6EC"));
        renderB.setSeriesPaint(3, Color.decode("#D9D9D9"));
        renderB.setSeriesPaint(4, Color.decode("#F0DFA4"));
        renderB.setSeriesPaint(5, Color.decode("#C781BC"));
        renderB.setSeriesPaint(6, Color.decode("#06A2C4"));

        ChartPanel chartPanelBarras = new ChartPanel(barChart);
        chartPanelBarras.setSize(480, 250);
        chartPanelBarras.setLocation(0, 40);

        DefaultPieDataset<String> datasetPastel = new DefaultPieDataset<String>();
        datasetPastel.setValue("Martin", 1);
        datasetPastel.setValue("Sergio", 10);
        datasetPastel.setValue("Saul", 3);
        datasetPastel.setValue("Perez", 20);
        datasetPastel.setValue("Teodoro", 17);
        datasetPastel.setValue("Daniel", 25);
        datasetPastel.setValue("Juan", 21);

        JFreeChart pieChart = ChartFactory.createPieChart(
                "", // Título del gráfico
                datasetPastel,
                false, // Incluir leyenda
                true,
                true);

        var plot = (PiePlot) pieChart.getPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setLabelGenerator(null);
        plot.setOutlineVisible(false);

        ChartPanel chartPanelPastel = new ChartPanel(pieChart);
        chartPanelPastel.setSize(310, 210);
        chartPanelPastel.setLocation(260, 20);

        rellenarTarjetaRankEmpleados(rankEmpleados, ventas, chartPanelBarras, chartPanelPastel);

        tblTabla.setBorder(null);
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        DefaultTableModel model;
        JScrollPane scpContenedorJtable = new JScrollPane();

        columnasClientes.add("No.Cliente");
        columnasClientes.add("Nombre");
        columnasClientes.add("Apellido P");
        columnasClientes.add("Apellido M");
        columnasClientes.add("Sexo");
        columnasClientes.add("Fecha de registro");

        model = new DefaultTableModel(data, columnasClientes);
        tblTabla.setModel(model);

        scpContenedorJtable.setSize(1260, 620);
        scpContenedorJtable.setLocation(15,330);
        scpContenedorJtable.setViewportView(tblTabla);
        scpContenedorJtable.setBorder(null);

        pnlPrincipalClientes.setSize(1300, 870);
        pnlPrincipalClientes.setLocation(209, 30);
        pnlPrincipalClientes.setLayout(null);
        //pnlPrincipalClientes.setBackground(Color.ORANGE);
        pnlPrincipalClientes.add(ventas);
        pnlPrincipalClientes.add(rankEmpleados);
        pnlPrincipalClientes.add(scpContenedorJtable);
        pnlPrincipalClientes.setVisible(false);

        consultasClientes.refresh(st, tblTabla, columnasClientes, consultaClientes, "clientes");

        framePrincipal.add(pnlPrincipalClientes);//Quitar para ver vista pacientes
    }
    public void rellenarTarjetaRankEmpleados(JPanel rankEmpleados,JPanel pnlVentas, ChartPanel graf, ChartPanel graf_past) {

        JButton btnEliminarEmpleado = new JButton("Eliminar Paciente");
        JButton btndetallesVentas = new JButton("Ver mas detalles");

        JLabel lblTitleRanking = new JLabel("Pacientes con mas visitas");
        JLabel lblTitleVentasProductos = new JLabel("Detalles");
        JLabel lblAgregar = new JLabel("AGREGAR");

        JLabel lblNombreTrab = new JLabel("Nombre: Carlos Ramirez");
        JLabel lblFechaRegistro = new JLabel("Fecha registro: 2023/11/05");
        JLabel lblUltimaVenta = new JLabel("Ultima Visita: 2023/11/05");
        //JLabel lblMediaVenta = new JLabel("Media de venta: $67.50");

        File rutaImgSaberMas = new File("C:\\Users\\pc\\IdeaProjects\\Sistema_ventas\\Imagenes\\sabermasrank.svg");
        FlatSVGIcon imgSaberMas = new FlatSVGIcon(rutaImgSaberMas);

        JLabel lblImgSaberMasBarras = new JLabel();
        JLabel lblImgSaberMastel = new JLabel();

        lblImgSaberMasBarras.setIcon(imgSaberMas);
        lblImgSaberMasBarras.setSize(35, 35);
        lblImgSaberMasBarras.setLocation(570,10);

        lblImgSaberMastel.setIcon(imgSaberMas);
        lblImgSaberMastel.setSize(35, 35);
        lblImgSaberMastel.setLocation(570, 10);

        lblTitleRanking.setSize(160, 20);
        lblTitleRanking.setLocation(10,10);

        lblTitleVentasProductos.setSize(130, 20);
        lblTitleVentasProductos.setLocation(10,10);

        lblAgregar.setSize(80, 20);
        lblAgregar.setLocation(490, 6);
        lblAgregar.setForeground(Color.decode("#444444"));
        lblAgregar.setFont(new Font("Arial", Font.PLAIN, 10));
        lblAgregar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AddCliente();

                int x = jfrPrincipal.getX() + 410;
                int y = jfrPrincipal.getY() + 150;

                dlgAddCliente.setLocation(x, y);
                dlgAddCliente.setVisible(true);

            }
        });

        lblNombreTrab.setSize(150, 20);
        lblNombreTrab.setLocation(10, 67);
        lblNombreTrab.setFont(new Font("arial", Font.PLAIN, 12));
        lblNombreTrab.setForeground(Color.decode("#4E4E4E"));
        lblFechaRegistro.setSize(150,20);
        lblFechaRegistro.setLocation(10, 89);
        lblFechaRegistro.setFont(new Font("arial", Font.PLAIN, 12));
        lblFechaRegistro.setForeground(Color.decode("#4E4E4E"));
        lblUltimaVenta.setSize(150, 20);
        lblUltimaVenta.setLocation(10, 109);
        lblUltimaVenta.setFont(new Font("arial", Font.PLAIN, 12));
        lblUltimaVenta.setForeground(Color.decode("#4E4E4E"));
        /*lblMediaVenta.setSize(150, 20);
        lblMediaVenta.setLocation(10, 129);
        lblMediaVenta.setFont(new Font("arial", Font.PLAIN, 12));
        lblMediaVenta.setForeground(Color.decode("#4E4E4E"));*/

        btnEliminarEmpleado.setSize(150, 40);
        btnEliminarEmpleado.setLocation(10,250);
        btnEliminarEmpleado.setForeground(Color.white);
        btnEliminarEmpleado.setBackground(Color.decode("#64D3B2"));

        btndetallesVentas.setSize(150, 40);
        btndetallesVentas.setLocation(180,250);
        btndetallesVentas.setForeground(Color.white);
        btndetallesVentas.setBackground(Color.decode("#81DBED"));
        btndetallesVentas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (st == null){
                    System.out.println("El st es nullo");
                }else{
                    System.out.println("El ST no es NULlllL");
                }
                consultasClientes.refresh(st, tblTabla, columnasClientes, consultaClientes, "clientes");
            }
        });

        rankEmpleados.add(lblImgSaberMasBarras);
        rankEmpleados.add(lblTitleRanking);
        rankEmpleados.add(lblAgregar);
        rankEmpleados.add(graf);

        pnlVentas.add(graf_past);
        pnlVentas.add(lblImgSaberMastel);
        pnlVentas.add(lblTitleVentasProductos);
        pnlVentas.add(lblNombreTrab);
        pnlVentas.add(lblFechaRegistro);
        pnlVentas.add(lblUltimaVenta);
        //pnlVentas.add(lblMediaVenta);
        pnlVentas.add(btnEliminarEmpleado);
        pnlVentas.add(btndetallesVentas);


        jfrPrincipal.setLayout(null);
        jfrPrincipal.repaint();

    }
    private void AddCliente(){
        File rutaImgPrincipalEditVenta = new File("C:\\Users\\pc\\IdeaProjects\\Sistema_ventas\\Imagenes\\addCliente.svg");
        FlatSVGIcon imgPrincipal = new FlatSVGIcon(rutaImgPrincipalEditVenta);
        JLabel lblImgPrincipal = new JLabel();
        File rutaImgHecho = new File("C:\\Users\\pc\\IdeaProjects\\Sistema_ventas\\Imagenes\\hechoventas.svg");
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

        System.out.println();

        JPanel pnlprimeraSeccion = new JPanel();
        pnlprimeraSeccion.setLayout(null);
        pnlprimeraSeccion.setSize(280, 600);
        pnlprimeraSeccion.setBorder(bordeIzquierdo);
        pnlprimeraSeccion.setLocation(0,0);

        lblImgPrincipal.setSize(260, 300);
        lblImgPrincipal.setIcon(imgPrincipal);
        lblImgPrincipal.setOpaque(true);
        lblImgPrincipal.setLocation(-10,100);

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
                dlgAddCliente.dispose();
                JOptionPane.showMessageDialog(pnlPrincipalClientes, "        El producto se ha modificado correctamente.\n Recuerda que ahora la informacion del producto ha \n               sido actualizada en el sistema.","Hecho",JOptionPane.INFORMATION_MESSAGE , imgHecho);

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

        dlgAddCliente.setSize(900, 600);
        dlgAddCliente.setVisible(false);
        dlgAddCliente.setTitle("Modificacion de venta");
        dlgAddCliente.add(pnlprimeraSeccion);
        dlgAddCliente.add(txtId);
        dlgAddCliente.add(txtCodigo);
        dlgAddCliente.add(lblVenta);
        dlgAddCliente.add(jccVenta);
        dlgAddCliente.add(lblCantidad);
        dlgAddCliente.add(jccCantidad);
        dlgAddCliente.add(lblFecha);
        dlgAddCliente.add(txtFecha);
        dlgAddCliente.add(lblHora);
        dlgAddCliente.add(txtHora);
        dlgAddCliente.add(lblVendedor);
        dlgAddCliente.add(jccVendedor);
        dlgAddCliente.add(lblCliente);
        dlgAddCliente.add(jccCliente);
        dlgAddCliente.add(txtUnitario);
        dlgAddCliente.add(txtPrecioTotal);
        dlgAddCliente.add(btnHecho);
        dlgAddCliente.setBackground(Color.white);
        dlgAddCliente.setLayout(null);
        dlgAddCliente.setModal(true);
    }

}
