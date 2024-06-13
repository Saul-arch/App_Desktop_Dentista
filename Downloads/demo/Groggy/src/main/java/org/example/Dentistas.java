package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Statement;
import java.util.Vector;

public class Dentistas {
    JFrame framePrincipal;
    JPanel pnlPrincipalInventario;
    JPanel pnlTablaProductos;
    JPanel pnlDetallesProductos;
    public JDialog dlgAgregarInventario = new JDialog();
    public JPanel pnlDatosProduct = new JPanel();
    public JPanel pnlListproduct = new JPanel();
    Consultas consultasInventario = new Consultas();
    public String selectInventario = "select * from inventario";
    public Statement st;

    public Dentistas(JFrame framePrincipal, Statement st){
        this.framePrincipal = framePrincipal;
        this.st = st;
        iniciarVista();
    }

    private void iniciarVista() {
        pnlPrincipalInventario = new JPanel();
        pnlTablaProductos = new JPanel();
        pnlDetallesProductos = new JPanel();
        JLabel lblDetalles = new JLabel("Detalles");
        JLabel lblProveedor = new JLabel();
        JLabel lblProveedorNombre = new JLabel();
        JLabel lblEmpresa = new JLabel();
        JLabel lblEmpresaNombre = new JLabel();
        JLabel lblFechaConvenio = new JLabel();
        JLabel lblFechaConvenioNombre = new JLabel();
        JLabel lblGananciasUnit = new JLabel();
        JLabel lblGananciasUnitNombre = new JLabel();
        JLabel lblGananciasTotales = new JLabel();
        JLabel lblGananciasTotalesNombre = new JLabel();
        JButton btnAgregar = new JButton();
        JButton btnVerProveedor = new JButton();
        JButton btnActualizarPrecio = new JButton();

        JLabel lblimgpdf = new JLabel();
        File rutaImgPdf = new File("C:\\Users\\pc\\IdeaProjects\\Sistema_ventas\\Imagenes\\pdf-_icon.svg");
        FlatSVGIcon icon_pdf = new FlatSVGIcon(rutaImgPdf);

        JTable tblTabla = new JTable();
        Vector<String> columnasInventario = new Vector<String>();
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        DefaultTableModel model;
        JScrollPane scpContenedorJtable = new JScrollPane();

        columnasInventario.add("Codigo");
        columnasInventario.add("proveedor");
        columnasInventario.add("producto");
        columnasInventario.add("Unidad de medida");
        columnasInventario.add("Precio unit");
        columnasInventario.add("Presentacion");
        columnasInventario.add("Stock");

        model = new DefaultTableModel(data, columnasInventario);
        tblTabla.setModel(model);

        scpContenedorJtable.setSize(800, 795);
        scpContenedorJtable.setLocation(0,4);
        scpContenedorJtable.setViewportView(tblTabla);

        pnlTablaProductos.setSize(800, 800);
        pnlTablaProductos.setLocation(0, 0);
        pnlTablaProductos.setVisible(true);
        pnlTablaProductos.setLayout(null);
        pnlTablaProductos.add(scpContenedorJtable);

        btnAgregar.setText("Agregar");
        btnAgregar.setForeground(Color.white);
        btnAgregar.setBackground(Color.decode("#F28989"));
        btnAgregar.setSize(130, 40);
        btnAgregar.setLocation(10, 740);
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               //Implementar la siguiente vista
                pnlPrincipalInventario.setVisible(false);
                dlgAgregarInventario.setVisible(true);
                
            }
        });

        btnVerProveedor.setText("Ver proveedor");
        btnVerProveedor.setForeground(Color.white);
        btnVerProveedor.setBackground(Color.decode("#06A2C4"));
        btnVerProveedor.setSize(130, 40);
        btnVerProveedor.setLocation(170, 740);

        btnActualizarPrecio.setText("Actualizar precio");
        btnActualizarPrecio.setForeground(Color.white);
        btnActualizarPrecio.setBackground(Color.decode("#64D3B2"));
        btnActualizarPrecio.setSize(130, 40);
        btnActualizarPrecio.setLocation(330, 740);

        lblDetalles.setSize(130, 30);
        lblDetalles.setLocation(200, 20);
        lblDetalles.setForeground(Color.GRAY);
        lblDetalles.setFont(new Font("Arial", Font.PLAIN, 20));

        lblProveedor.setText("Proveedor: ");
        lblProveedor.setSize(130, 20);
        lblProveedor.setLocation(10, 110);
        lblProveedor.setFont(new Font("", Font.PLAIN, 14));

        lblProveedorNombre.setText("Efren Garcia Reyes");
        lblProveedorNombre.setSize(130, 20);
        lblProveedorNombre.setLocation(90, 110);
        lblProveedorNombre.setFont(new Font("", Font.PLAIN, 14));
        lblProveedorNombre.setForeground(Color.decode("#6E6E6E"));

        lblEmpresa.setText("Empresa: ");
        lblEmpresa.setSize(100, 20);
        lblEmpresa.setLocation(10, 150);
        lblEmpresa.setFont(new Font("", Font.PLAIN, 14));

        lblEmpresaNombre.setText("Coca cola");
        lblEmpresaNombre.setSize(150, 20);
        lblEmpresaNombre.setLocation(80, 150);
        lblEmpresaNombre.setFont(new Font("", Font.PLAIN, 14));
        lblEmpresaNombre.setForeground(Color.decode("#6E6E6E"));

        lblFechaConvenio.setText("Fecha de convenio: ");
        lblFechaConvenio.setSize(150, 20);
        lblFechaConvenio.setLocation(10, 190);
        lblFechaConvenio.setFont(new Font("", Font.PLAIN, 14));

        lblFechaConvenioNombre.setText("2020-08-24");
        lblFechaConvenioNombre.setSize(150, 20);
        lblFechaConvenioNombre.setLocation(140, 190);
        lblFechaConvenioNombre.setFont(new Font("", Font.PLAIN, 14));
        lblFechaConvenioNombre.setForeground(Color.decode("#6E6E6E"));

        lblGananciasUnit.setText("Ganancia por unidad: ");
        lblGananciasUnit.setSize(150, 20);
        lblGananciasUnit.setLocation(10, 230);
        lblGananciasUnit.setFont(new Font("", Font.PLAIN, 14));

        lblGananciasUnitNombre.setText("$5.50");
        lblGananciasUnitNombre.setSize(150, 20);
        lblGananciasUnitNombre.setLocation(155, 230);
        lblGananciasUnitNombre.setFont(new Font("", Font.PLAIN, 14));
        lblGananciasUnitNombre.setForeground(Color.decode("#6E6E6E"));

        lblGananciasTotales.setText("Ganancias totales: ");
        lblGananciasTotales.setSize(150, 20);
        lblGananciasTotales.setLocation(10, 270);
        lblGananciasTotales.setFont(new Font("", Font.PLAIN, 14));

        lblGananciasTotalesNombre.setText("$321.50");
        lblGananciasTotalesNombre.setSize(150, 20);
        lblGananciasTotalesNombre.setLocation(140, 270);
        lblGananciasTotalesNombre.setFont(new Font("", Font.PLAIN, 14));
        lblGananciasTotalesNombre.setForeground(Color.decode("#6E6E6E"));

        lblimgpdf.setSize(20, 20);
        lblimgpdf.setLocation(435, 0);
        lblimgpdf.setIcon(icon_pdf);

        pnlDetallesProductos.setSize(460, 790);
        pnlDetallesProductos.setLocation(812, 10);
        pnlDetallesProductos.setBackground(Color.white);
        pnlDetallesProductos.add(lblDetalles);
        pnlDetallesProductos.add(lblProveedor);
        pnlDetallesProductos.add(lblProveedorNombre);
        pnlDetallesProductos.add(lblEmpresa);
        pnlDetallesProductos.add(lblEmpresaNombre);
        pnlDetallesProductos.add(lblFechaConvenio);
        pnlDetallesProductos.add(lblFechaConvenioNombre);
        pnlDetallesProductos.add(lblGananciasUnit);
        pnlDetallesProductos.add(lblGananciasUnitNombre);
        pnlDetallesProductos.add(lblGananciasTotales);
        pnlDetallesProductos.add(lblGananciasTotalesNombre);
        pnlDetallesProductos.add(btnAgregar);
        pnlDetallesProductos.add(btnVerProveedor);
        pnlDetallesProductos.add(btnActualizarPrecio);
        pnlDetallesProductos.add(lblimgpdf);

        pnlDetallesProductos.setLayout(null);
        pnlDetallesProductos.setVisible(true);

        pnlPrincipalInventario.setSize(1300, 870);
        pnlPrincipalInventario.setLocation(209, 30);
        pnlPrincipalInventario.setLayout(null);
        pnlPrincipalInventario.add(pnlTablaProductos);
        pnlPrincipalInventario.setBackground(Color.white);
        pnlPrincipalInventario.add(pnlDetallesProductos);
        pnlPrincipalInventario.setVisible(false);

        //Extraigo los datos de la API de Spring Boot(descomentar la ultima linea al final)
        /*try {
            String apiUrl = "https://api.example.com/persons"; // Reemplaza con la URL de tu API
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }*/

        //consultasInventario.refresh(st, tblTabla, columnasInventario, selectInventario, "inventario");

        //framePrincipal.add(pnlPrincipalInventario); //QUITAR PARA VER VISTA DE DENTISTAS

        initVistaNuevoInventario(pnlPrincipalInventario);

    }
    // Método para convertir la respuesta JSON en una lista de objetos Dentista


    private void initVistaNuevoInventario(JPanel pnlPrincipalInventario) {
        dlgAgregarInventario.setSize(1000, 800);
        dlgAgregarInventario.setBackground(Color.white);
        dlgAgregarInventario.setLocation(0, 30);
        dlgAgregarInventario.setVisible(false);

    }
    // Clase Dentista para representar los datos obtenidos de la API
    private static class Dentista {
        private int denClave;
        private String nombre;
        private String app;
        private String apm;
        private String rfc;
        private String curp;
        private int celular;

        public Dentista(int denClave, String nombre, String app, String apm, String rfc, String curp, int celular) {
            this.denClave = denClave;
            this.nombre = nombre;
            this.app = app;
            this.apm = apm;
            this.rfc = rfc;
            this.curp = curp;
            this.celular = celular;
        }

        public int getDenClave() {
            return denClave;
        }

        public String getNombre() {
            return nombre;
        }

        public String getApp() {
            return app;
        }

        public String getApm() {
            return apm;
        }

        public String getRfc() {
            return rfc;
        }

        public String getCurp() {
            return curp;
        }

        public int getCelular() {
            return celular;
        }
    }

}
