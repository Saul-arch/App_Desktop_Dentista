package org.example;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.jfree.chart.*;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.DefaultXYDataset;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dashboard {
    JPanel pnlDashboard;
    public static java.util.List<String> listId = new ArrayList<>();
    public static List<String> listObject = new ArrayList<>();
    public JFrame jfrPrincipal;
    JPanel pnlActiRecientes = new JPanel();
    public static JPanel contentPanel = new JPanel();
    public JScrollPane tblTablaActivity = new JScrollPane();
    JPanel pnlNotas  = new JPanel();
    JPanel pnlHederNotas;
    JPanel pnlBodyNotas;
    JLabel lblAddNota;
    JScrollPane scrNotas = new JScrollPane();
    JTextArea txtNotaArea;
    JPanel pnlBusqCancel;
    JPanel pnlNotResults;
    public Statement st;

    public Dashboard(JFrame jfPrincipal, Statement st){
        this.jfrPrincipal = jfPrincipal;
        this.st = st;
        initDashboard();
    }

    private void initDashboard() {
        pnlDashboard = new JPanel();
        JPanel rankEmpleados = new JPanel();
        JPanel ventas = new JPanel();
        JPanel pnlCrecimiento = new JPanel();
        JLabel lblIngresosBruto = new JLabel("INGRESOS BRUTO");
        JLabel lblViewIngresosBruto = new JLabel("$4563.50");
        JLabel lblIngresosNeto = new JLabel("INGRESOS NETO");
        JLabel lblViewIngresosNeto = new JLabel("$3640.50");
        JLabel lblPedidas = new JLabel("PERDIDAS");
        JLabel lblViewPedidas = new JLabel("$503.50");


        File rutaIconoMoreOptions = new File("C:\\Users\\pc\\IdeaProjects\\Sistema_ventas\\Imagenes\\moreopcions.svg");
        FlatSVGIcon imgMoreOptions = new FlatSVGIcon(rutaIconoMoreOptions);
        JLabel lblActiRes = new JLabel();
        JLabel agregarReciente = new JLabel();

        lblActiRes.setText("ACTIVIDADES RECIENTES");
        lblActiRes.setSize(180, 20);
        lblActiRes.setLocation(10, 10);
        lblActiRes.setForeground(Color.decode("#696969"));
        lblActiRes.setFont(new Font("", Font.PLAIN, 10));

        agregarReciente.setSize(50, 50);
        agregarReciente.setLocation(900, 7);
        agregarReciente.setIcon(imgMoreOptions);

        lblIngresosBruto.setSize(150, 25);
        lblIngresosBruto.setLocation(30,20);
        lblIngresosBruto.setFont(new Font("arial", Font.PLAIN, 15));
        lblIngresosBruto.setForeground(Color.decode("#3F3F3F"));

        lblIngresosNeto.setSize(150, 25);
        lblIngresosNeto.setLocation(300, 20);
        lblIngresosNeto.setFont(new Font("arial", Font.PLAIN, 15));
        lblIngresosNeto.setForeground(Color.decode("#3F3F3F"));

        lblPedidas.setSize(150, 25);
        lblPedidas.setLocation(570, 20);
        lblPedidas.setFont(new Font("arial", Font.PLAIN, 15));
        lblPedidas.setForeground(Color.decode("#3F3F3F"));

        lblViewIngresosBruto.setSize(150, 30);
        lblViewIngresosBruto.setLocation(30, 50);
        lblViewIngresosBruto.setFont(new Font("arial", Font.PLAIN, 30));

        lblViewIngresosNeto.setSize(150, 30);
        lblViewIngresosNeto.setLocation(300, 50);
        lblViewIngresosNeto.setFont(new Font("arial", Font.PLAIN, 30));

        lblViewPedidas.setSize(150, 30);
        lblViewPedidas.setLocation(570, 50);
        lblViewPedidas.setFont(new Font("arial", Font.PLAIN, 30));

        rankEmpleados.setSize(440, 260);
        rankEmpleados.setLocation(30, 100);
        rankEmpleados.setLayout(null);
        rankEmpleados.setBackground(Color.white);

        ventas.setSize(440, 260);
        ventas.setLocation(530, 100);
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
        chartPanelBarras.setSize(380, 210);
        chartPanelBarras.setLocation(0, 40);

        /*------------------------------------*/

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
        chartPanelPastel.setSize(290, 190);
        chartPanelPastel.setLocation(180, 20);

        contentPanel.setSize(938, 200);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.white);

        tblTablaActivity.setSize(938, 340);
        tblTablaActivity.setLocation(0,35);
        tblTablaActivity.setBorder(new EmptyBorder(0, 0, 0, 0));
        tblTablaActivity.setBackground(Color.white);
        tblTablaActivity.setViewportView(contentPanel);


        pnlActiRecientes.setSize(938, 220);
        pnlActiRecientes.setLocation(30, 580);
        pnlActiRecientes.setLayout(null);
        pnlActiRecientes.setBackground(Color.white);
        pnlActiRecientes.add(lblActiRes);
        pnlActiRecientes.add(agregarReciente);
        pnlActiRecientes.add(tblTablaActivity, BorderLayout.CENTER);


        rellenarTarjetaRankEmpleados(rankEmpleados, ventas, chartPanelBarras, chartPanelPastel);
        rellenarTarjetaCrecimiento(pnlCrecimiento);

        pnlDashboard.setSize(1300, 870);
        pnlDashboard.setLocation(209, 30);
        pnlDashboard.setLayout(null);
        pnlDashboard.setVisible(true);
        pnlDashboard.add(lblIngresosBruto);
        pnlDashboard.add(lblIngresosNeto);
        pnlDashboard.add(lblPedidas);
        pnlDashboard.add(lblViewIngresosBruto);
        pnlDashboard.add(lblViewIngresosNeto);
        pnlDashboard.add(lblViewPedidas);
        pnlDashboard.add(rankEmpleados);
        pnlDashboard.add(ventas);
        pnlDashboard.add(pnlCrecimiento);
        pnlDashboard.add(pnlActiRecientes);
        pnlDashboard.add(pnlNotas);
        pnlDashboard.repaint();

        jfrPrincipal.add(pnlDashboard); //QUITAR PARA VER PANEL PRINCIPAL
        jfrPrincipal.repaint();
        initNotas();
        AgregarCard();
        refreshNotas(pnlBodyNotas);

    }

    private void initNotas() {
        JLabel TitleNotas = new JLabel();
        JTextField txtBuscarNota = new JTextField();

        TitleNotas.setText("Notas rapidas");
        TitleNotas.setLocation(10, 33);
        TitleNotas.setSize(110, 15);
        TitleNotas.setFont(new Font("Arial", Font.CENTER_BASELINE, 16));
        TitleNotas.setForeground(Color.decode("#455a64"));

        JLabel lblBuscar = new JLabel();
        JLabel lblCancelar = new JLabel();
        File rutaImgBuscar = new File("C:\\Users\\pc\\IdeaProjects\\Sistema_ventas\\Imagenes\\searchNota.svg");
        FlatSVGIcon imgBuscar = new FlatSVGIcon(rutaImgBuscar);

        lblBuscar.setSize(13, 35);
        lblBuscar.setLocation(30, 5);
        lblBuscar.setIcon(imgBuscar);
        lblBuscar.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                scrNotas.setVisible(false);
                pnlNotResults.setVisible(true);
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

        File rutaImgCancel = new File("C:\\Users\\pc\\IdeaProjects\\Sistema_ventas\\Imagenes\\cancelsearch.svg");
        FlatSVGIcon imgCancel = new FlatSVGIcon(rutaImgCancel);
        lblCancelar.setSize(12, 35);
        lblCancelar.setLocation(0,5);
        lblCancelar.setIcon(imgCancel);

        pnlBusqCancel = new JPanel();
        pnlBusqCancel.setBackground(Color.white);
        pnlBusqCancel.setSize(55, 25);
        pnlBusqCancel.setLocation(200, 2);
        pnlBusqCancel.setLayout(null);
        pnlBusqCancel.add(lblBuscar);
        pnlBusqCancel.add(lblCancelar);

        txtBuscarNota.setSize(263, 30);
        txtBuscarNota.setText("Buscar...");
        txtBuscarNota.setFont(new Font("Arial", Font.PLAIN, 13));
        txtBuscarNota.setForeground(Color.decode("#455a64"));
        txtBuscarNota.setLocation(8, 60);
        txtBuscarNota.add(pnlBusqCancel);

        File rutaPlus = new File("C:\\Users\\pc\\IdeaProjects\\Prueba_Pur\\Imagenes\\plus.svg");
        FlatSVGIcon imgAgregarNota = new FlatSVGIcon(rutaPlus);

        JLabel lblPlus = new JLabel();
        lblPlus.setSize(50, 50);
        lblPlus.setLocation(150,620);
        lblPlus.setIcon(imgAgregarNota);
        lblPlus.setLayout(null);
        lblPlus.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                //NuevaNota();

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
        File rutaImgSettings = new File("C:\\Users\\pc\\IdeaProjects\\Sistema_ventas\\Imagenes\\addnota.svg");
        FlatSVGIcon imgSettings = new FlatSVGIcon(rutaImgSettings);

        lblAddNota = new JLabel();
        lblAddNota.setSize(50, 50);
        lblAddNota.setLocation(235, 10);
        lblAddNota.setIcon(imgSettings);
        lblAddNota.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Agregar Nota");
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
        JLabel lblImgNotResult = new JLabel();
        File rutaImgNotResult = new File("C:\\Users\\pc\\IdeaProjects\\Prueba_Pur\\Imagenes\\no-results.svg");
        FlatSVGIcon img404 = new FlatSVGIcon(rutaImgNotResult);
        JLabel lblnoResult = new JLabel();

        lblImgNotResult.setSize(250, 200);
        lblImgNotResult.setLocation(0,0);
        lblImgNotResult.setIcon(img404);

        lblnoResult.setText("Sin resultados");
        lblnoResult.setSize(150, 20);
        lblnoResult.setLocation(20, 20);
        lblnoResult.setFont(new Font("Arial", Font.CENTER_BASELINE, 15));
        lblnoResult.setForeground(Color.decode("#919C9F"));

        pnlNotResults = new JPanel();
        pnlNotResults.setSize(210, 400);
        pnlNotResults.setLocation(0, 225);
        pnlNotResults.setBackground(Color.decode("#eeeeee"));
        pnlNotResults.add(lblImgNotResult);
        pnlNotResults.add(lblnoResult);
        pnlNotResults.setVisible(false);

        pnlHederNotas = new JPanel();
        pnlHederNotas.setSize(280, 100);
        pnlHederNotas.setLocation(0,0);
        pnlHederNotas.setLayout(null);
        pnlHederNotas.add(TitleNotas);
        pnlHederNotas.add(txtBuscarNota);
        pnlHederNotas.add(lblAddNota);
        pnlHederNotas.setBackground(Color.white);

        pnlBodyNotas = new JPanel();
        pnlBodyNotas.setSize(280, 680);
        pnlBodyNotas.setBorder(BorderFactory.createEmptyBorder());
        pnlBodyNotas.setLayout(new BoxLayout(pnlBodyNotas, BoxLayout.Y_AXIS));
        pnlBodyNotas.setBackground(Color.white);

        scrNotas.setSize(280, 680);
        scrNotas.setLocation(0, 100);
        scrNotas.setBorder(BorderFactory.createEmptyBorder());
        scrNotas.setViewportView(pnlBodyNotas);

        pnlNotas.setSize(280, 780);
        pnlNotas.setLocation(985, 20);
        pnlNotas.setLayout(null);
        pnlNotas.setBorder(BorderFactory.createEmptyBorder());
        pnlNotas.add(pnlHederNotas);
        pnlNotas.add(lblPlus);
        pnlNotas.add(scrNotas);
        pnlNotas.add(pnlNotResults);
        //pnlNotas.setBackground(Color.decode("#eeeeee"));
        pnlNotas.setBackground(Color.PINK);

    }

    private void rellenarTarjetaCrecimiento(JPanel pnlCrecimiento) {
        JButton btnCursos = new JButton("Saber mas sobre finanzas");
        JLabel lbltext1 = new JLabel("Ventas tienden a la alza con  respecto al dia de ayer.");
        JLabel lblText2 = new JLabel("Ventas representan un 2% a la alza.");

        DefaultXYDataset dataset = new DefaultXYDataset();

        double[][] data2 = new double[][] {
                {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18 ,19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43}, // Valores x del polígono 1
                {1, 5, 2, 4, 2, 2, 2, 10, 8, 9, 10, 5, 3, 8, 9, 12, 17, 10, 4, 10, 8, 15 , 9, 7, 10, 21, 43, 12, 43, 56, 32, 2, 32, 12, 12, 24, 53, 45, 32, 43, 12, 45, 23}  // Valores y del polígono 1
        };

        dataset.addSeries("Ventas", data2);


        // Crear el gráfico de polígonos
        JFreeChart chart = ChartFactory.createXYLineChart(
                "", // Título del gráfico
                "", // Etiqueta del eje x
                "", // Etiqueta del eje y
                dataset, // Datos
                PlotOrientation.VERTICAL, // Orientación del gráfico
                false, // Incluir leyenda
                false, // Incluir tooltips
                false // Incluir URLs
        );

        TextTitle tituloPoligonos = chart.getTitle();
        tituloPoligonos.setFont(new Font("Arial", Font.PLAIN, 15));
        tituloPoligonos.setPaint(Color.decode("#4db6ac"));
        chart.setBackgroundPaint(Color.white);

        XYPlot plot = (XYPlot) chart.getPlot();
        XYItemRenderer renderer = plot.getRenderer();
        Stroke stroke = new BasicStroke(2.0f);
        plot.setOutlineVisible(false);
        plot.setDomainGridlinesVisible(false);
        plot.setDomainGridlinesVisible(false);

        plot.getRangeAxis().setTickLabelsVisible(false); // Eje Y
        plot.getDomainAxis().setTickLabelsVisible(false); // Eje X

        plot.setBackgroundPaint(Color.white);

        renderer.setSeriesPaint(0, Color.decode("#bbdefb"));
        renderer.setSeriesPaint(1, Color.decode("#ffccbc"));
        renderer.setSeriesStroke(0, stroke);
        renderer.setSeriesStroke(1, stroke);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setSize(450, 150);
        chartPanel.setLocation(350, 40);
        chartPanel.addChartMouseListener(new ChartMouseListener() {
            @Override
            public void chartMouseClicked(ChartMouseEvent event) {
                System.out.println("Picaste al grafico");
            }

            @Override
            public void chartMouseMoved(ChartMouseEvent event) {

            }
        });




        lbltext1.setSize(301, 25);
        lbltext1.setLocation(15, 10);
        lblText2.setSize(250, 25);
        lblText2.setLocation(15, 40);

        btnCursos.setSize(180, 50);
        btnCursos.setBackground(Color.decode("#FFBE99"));
        btnCursos.setForeground(Color.white);
        btnCursos.setLocation(15, 130);

        pnlCrecimiento.setSize(938, 200);
        pnlCrecimiento.setLocation(30, 370);
        pnlCrecimiento.setBackground(Color.white);
        pnlCrecimiento.setLayout(null);
        pnlCrecimiento.add(lbltext1);
        pnlCrecimiento.add(lblText2);
        pnlCrecimiento.add(btnCursos);
        pnlCrecimiento.add(chartPanel);
        pnlCrecimiento.repaint();

        System.out.println("LLEGO");

    }

    public void rellenarTarjetaRankEmpleados(JPanel rankEmpleados,JPanel pnlVentas, ChartPanel graf, ChartPanel graf_past) {

        JButton btnEliminarEmpleado = new JButton("Eliminar empleado");
        JButton btndetallesVentas = new JButton("Ver mas detalles");

        JLabel lblTitleRanking = new JLabel("Ranking de empleados");
        JLabel lblTitleVentasProductos = new JLabel("Ventas");

        JLabel lblNombreTrab = new JLabel("Nombre: Carlos Ramirez");
        JLabel lblFechaRegistro = new JLabel("Fecha registro: 2023/11/05");
        JLabel lblUltimaVenta = new JLabel("Ultima venta: 2023/11/05");
        JLabel lblMediaVenta = new JLabel("Media de venta: $67.50");

        File rutaImgSaberMas = new File("C:\\Users\\pc\\IdeaProjects\\Sistema_ventas\\Imagenes\\sabermasrank.svg");
        FlatSVGIcon imgSaberMas = new FlatSVGIcon(rutaImgSaberMas);

        JLabel lblImgSaberMasBarras = new JLabel();
        JLabel lblImgSaberMastel = new JLabel();

        lblImgSaberMasBarras.setIcon(imgSaberMas);
        lblImgSaberMasBarras.setSize(35, 35);
        lblImgSaberMasBarras.setLocation(420,5);

        lblImgSaberMastel.setIcon(imgSaberMas);
        lblImgSaberMastel.setSize(35, 35);
        lblImgSaberMastel.setLocation(420, 5);

        lblTitleRanking.setSize(130, 20);
        lblTitleRanking.setLocation(10,20);

        lblTitleVentasProductos.setSize(130, 20);
        lblTitleVentasProductos.setLocation(10,20);

        lblNombreTrab.setSize(150, 20);
        lblNombreTrab.setLocation(10, 65);
        lblNombreTrab.setFont(new Font("arial", Font.PLAIN, 10));
        lblNombreTrab.setForeground(Color.decode("#4E4E4E"));
        lblFechaRegistro.setSize(150,20);
        lblFechaRegistro.setLocation(10, 87);
        lblFechaRegistro.setFont(new Font("arial", Font.PLAIN, 10));
        lblFechaRegistro.setForeground(Color.decode("#4E4E4E"));
        lblUltimaVenta.setSize(150, 20);
        lblUltimaVenta.setLocation(10, 107);
        lblUltimaVenta.setFont(new Font("arial", Font.PLAIN, 10));
        lblUltimaVenta.setForeground(Color.decode("#4E4E4E"));
        lblMediaVenta.setSize(150, 20);
        lblMediaVenta.setLocation(10, 127);
        lblMediaVenta.setFont(new Font("arial", Font.PLAIN, 10));
        lblMediaVenta.setForeground(Color.decode("#4E4E4E"));

        btnEliminarEmpleado.setSize(150, 40);
        btnEliminarEmpleado.setLocation(10,210);
        btnEliminarEmpleado.setForeground(Color.white);
        btnEliminarEmpleado.setBackground(Color.decode("#64D3B2"));

        btndetallesVentas.setSize(150, 40);
        btndetallesVentas.setLocation(180,210);
        btndetallesVentas.setForeground(Color.white);
        btndetallesVentas.setBackground(Color.decode("#81DBED"));

        rankEmpleados.add(lblImgSaberMasBarras);
        rankEmpleados.add(lblTitleRanking);
        rankEmpleados.add(graf);

        pnlVentas.add(graf_past);
        pnlVentas.add(lblImgSaberMastel);
        pnlVentas.add(lblTitleVentasProductos);
        pnlVentas.add(lblNombreTrab);
        pnlVentas.add(lblFechaRegistro);
        pnlVentas.add(lblUltimaVenta);
        pnlVentas.add(lblMediaVenta);
        pnlVentas.add(btnEliminarEmpleado);
        pnlVentas.add(btndetallesVentas);

        jfrPrincipal.setLayout(null);
        jfrPrincipal.repaint();

    }
    private static void AgregarCard() {

        //Te quedaste en que hivas a hallar la forma de obetener el id y el tipo de objeto
        // para asi concatenarlo en un String y agregar ese estring talvez a la listaNombres

        // cdfvdfvdfvdfvdfvdfvdfvdf
        //consultInit.saveListRecent(st, listId, listObject);
        listObject.add("Venta");
        listObject.add("Compra");
        listObject.add("Venta");
        listObject.add("Venta");
        listObject.add("Compra");

        listId.add("23");
        listId.add("45");
        listId.add("67");
        listId.add("78");
        listId.add("89");

        contentPanel.removeAll();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        Border bordeActRecet = BorderFactory.createMatteBorder(0, 10, 0, 10, Color.decode("#FAFAFA"));

        for (int i = 0; i < listId.size(); i++) {
            String idExtra = listId.get(i);
            String tipoObjectExtra = listObject.get(i);

            JPanel namePanel = new JPanel();
            namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS)); // Usar BoxLayout para el namePanel
            namePanel.setBackground(Color.PINK);
            namePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
            namePanel.setBorder(bordeActRecet);
            JLabel lbltext = new JLabel("Agregaste la "+tipoObjectExtra+ " "+idExtra);

            // Panel para el JLabel con FlowLayout alineado a la izquierda
            JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 13, 7));
            labelPanel.setBackground(Color.decode("#eceff1"));
            labelPanel.add(lbltext);
            namePanel.add(labelPanel);

            // Panel para los botones con FlowLayout alineado a la derecha
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 7));
            buttonPanel.setBackground(Color.decode("#eceff1"));
            File rutaImgMoreInfoRes = new File("C:\\Users\\pc\\IdeaProjects\\Sistema_ventas\\Imagenes\\more_info_res.svg");
            File rutaImgOcultar = new File("C:\\Users\\pc\\IdeaProjects\\Sistema_ventas\\Imagenes\\ocultar.svg");

            FlatSVGIcon imgOcular = new FlatSVGIcon(rutaImgOcultar);
            FlatSVGIcon imgMoreInfo = new FlatSVGIcon(rutaImgMoreInfoRes);

            // JButton btnBorrar = new JButton("Borrar");
            JButton btnOcultar = new JButton();
            JButton MoreInfo = new JButton();


            btnOcultar.setIcon(imgOcular);
            btnOcultar.setBorder(null);
            btnOcultar.setSize(10, 10);
            btnOcultar.setContentAreaFilled(false);
            btnOcultar.setOpaque(false);
            btnOcultar.requestFocus(null);

            MoreInfo.setIcon(imgMoreInfo);
            MoreInfo.setBorder(null);
            MoreInfo.setContentAreaFilled(false);
            MoreInfo.setOpaque(false);
            MoreInfo.setSize(10,10);
            MoreInfo.requestFocus(null);

            //buttonPanel.add(btnBorrar);
            buttonPanel.add(MoreInfo);
            buttonPanel.add(btnOcultar);
            namePanel.add(buttonPanel);

            MoreInfo.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //consultInit.infoRecent();

                }
            });

            btnOcultar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Obtener el nombre asociado al botón "Borrar"
                    String nameToDelete = lbltext.getText();
                    // Llamar al método para borrar el nombre
                    VerNombreBorrar(nameToDelete);
                }

                private void VerNombreBorrar(String nameToDelete) {
                    System.out.println("Vas a borrar: "+nameToDelete);
                }
            });

            contentPanel.add(namePanel);
            contentPanel.add(Box.createVerticalStrut(10));
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    //////// BORRAR EL METODO refreshNotas ESE DEBE DE IR EN UNA CLASE PARA CONSULTAS ///////////
    public void refreshNotas(JPanel pnlNotas){
        List<String> ListColors = new ArrayList<>();
        // Obtener la fecha actual
        LocalDate currentDate = LocalDate.now();

        // Definir el formato deseado para día en texto y mes en texto
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM");
        String formattedDate = currentDate.format(formatter);

        ListColors.add("#FADBD8");
        ListColors.add("#EBDEF0");
        ListColors.add("#D4E6F1");
        ListColors.add("#D6EAF8");
        ListColors.add("#D1F2EB");
        ListColors.add("#FCF3CF");
        ListColors.add("#F6DDCC");

        //String consulta = "select * from notas";
        List<String> Notas = new ArrayList<>();
        List<String> fechasNotas = new ArrayList<>();

        pnlNotas.setBorder(BorderFactory.createEmptyBorder());

        Notas.add("Este código supone que hay instrucciones y funciones disponibles en el entorno de ejecución para cargar valores en registros, multiplicar y mostrar resultados. Además, dependiendo del lenguaje o entorno específico, es posible que las instrucciones y sintaxis varíen.");
        Notas.add("Este código supone que hay instrucciones y funciones disponibles en el entorno de ejecución para cargar valores en registros, multiplicar y mostrar resultados. Además, dependiendo del lenguaje o entorno específico, es posible que las instrucciones y sintaxis varíen.");
        Notas.add("Este código supone que hay instrucciones y funciones disponibles en el entorno de ejecución para cargar valores en registros, multiplicar y mostrar resultados. Además, dependiendo del lenguaje o entorno específico, es posible que las instrucciones y sintaxis varíen.");
        Notas.add("Este código supone que hay instrucciones y funciones disponibles en el entorno de ejecución para cargar valores en registros, multiplicar y mostrar resultados. Además, dependiendo del lenguaje o entorno específico, es posible que las instrucciones y sintaxis varíen.");
        Notas.add("Este código supone que hay instrucciones y funciones disponibles en el entorno de ejecución para cargar valores en registros, multiplicar y mostrar resultados. Además, dependiendo del lenguaje o entorno específico, es posible que las instrucciones y sintaxis varíen.");
        Notas.add("Este código supone que hay instrucciones y funciones disponibles en el entorno de ejecución para cargar valores en registros, multiplicar y mostrar resultados. Además, dependiendo del lenguaje o entorno específico, es posible que las instrucciones y sintaxis varíen.");


        fechasNotas.add("2000-08-21");
        fechasNotas.add("2023-12-01");
        fechasNotas.add("2022-02-12");
        fechasNotas.add("2022-04-13");
        fechasNotas.add("2023-12-23");
        //Lo de abajo lo unico que hace es rellenar las listas de las notas, arriba las relleno manualmente
        /*try {
            rs = st.executeQuery(consulta);

            while (rs.next()){

                Notas.add(rs.getString("nota"));
                fechasNotas.add(rs.getString("fecha"));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/

        Border bordeIzquierdo = BorderFactory.createMatteBorder(0, 10, 0, 10, Color.decode("#FAFAFA"));

        pnlNotas.removeAll();
        pnlNotas.setLayout(new BoxLayout(pnlNotas, BoxLayout.Y_AXIS));

        for (int i = 0; i < Notas.size(); i++) {

            String randomColor = randomColor(ListColors);
            Border border = BorderFactory.createMatteBorder(0, 110, 0, 0, pnlNotas.getBackground());

            String nameExtrai = Notas.get(i);

            JPanel namePanel = new JPanel();

            namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS)); // Usar BoxLayout para el namePanel
            namePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
            namePanel.setBorder(bordeIzquierdo);

            JTextArea textArea = new JTextArea();
            JLabel lblFecha = new JLabel();

            textArea.setLineWrap(true); // Hacer que el texto se ajuste automáticamente en líneas
            textArea.setWrapStyleWord(true); // Hacer que el texto se ajuste respetando las palabras completas
            textArea.setEditable(false); // Deshabilitar edición del JTextArea
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(180, 100)); // Tamaño fijo del JScrollPane, altura de 100 píxeles

            String texto = nameExtrai;

            if (texto.length() > 150) {
                // Truncar el texto y agregar los tres puntos suspensivos "..." al final
                texto = texto.substring(0, 150) + "...";
            }

            textArea.setText(texto);// Actualizar el contenido del JTextArea con el texto del nombre
            lblFecha.setText(formattedDate);
            lblFecha.setBackground(Color.decode(randomColor));
            lblFecha.setOpaque(true);
            lblFecha.setBorder(border);
            lblFecha.setSize(210, 20);
            lblFecha.setForeground(Color.decode("#B2BABB"));

            textArea.setBackground(Color.decode(randomColor));
            scrollPane.setBorder(BorderFactory.createEmptyBorder());

            //namePanel.add(lblFecha);
            namePanel.add(scrollPane);

            pnlNotas.add(namePanel);
            pnlNotas.add(Box.createVerticalStrut(10));
        }

        pnlNotas.revalidate();
        pnlNotas.repaint();

    }

    private String randomColor(List<String> ListColors) {
        Random random = new Random();
        int randomNumber = random.nextInt(7);

        return ListColors.get(randomNumber);

    }


}
