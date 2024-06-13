package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import modelos.Consulta;
import modelos.Dentistas;
import modelos.Servicios;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Consultas {
    public ResultSet rs;
    public String medida;

    public static String Url = "http://localhost:8081/getConsultas";
    public static String UrlServicios = "http://localhost:8081/getServicios";
    public static String UrlDentistas = "http://localhost:8081/getDentistas";
    public static String UrlGetR = "http://localhost:8081/getConsultasR/";
    public static HttpClient cliente = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

    public static ObjectMapper mapper = new ObjectMapper();


    public void refreshDatosApi(Vector<Vector<Object>> oj, JTable list, Vector<String> columnas, String tipo_tabla){

        if (tipo_tabla.equals("consulta_dentistas")){

            list.removeAll();

            Vector<Vector<Object>> data = oj;
            DefaultTableModel model;

            model = new DefaultTableModel(data, columnas);
            list.setModel(model);
            list.repaint();
            System.out.println("Refresco");
        }

    }
    public  boolean deleteRecord(int id) {
        try {
            // URL del endpoint de tu API para borrar un registro
            URL url = new URL("http://localhost:8081/deleteConsulta/" + id);
            ///deleteConsulta/{id_consulta}
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Método DELETE
            connection.setRequestMethod("DELETE");

            // Verificación del código de respuesta
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Lectura de la respuesta
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                System.out.println(response.toString());
                return true;
            } else {
                System.out.println("Error al borrar el registro. Código de respuesta: " + responseCode);
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public void refresh(Statement st, JTable lista, Vector<String> columnas, String consulta, String tipo_tabla){
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        DefaultTableModel model;

        try{

            rs = st.executeQuery(consulta);

            if (tipo_tabla.equals("clientes")){
                while (rs.next()){
                    Vector<Object> row = new Vector<Object>();
                    // Vector<Object> columnNames = new Vector<Object>();
                    row.add(rs.getString("cod_cliente"));
                    row.add(rs.getString("nombre"));
                    row.add(rs.getString("apellido_p"));
                    row.add(rs.getString("apellido_m"));
                    row.add(rs.getString("sexo"));
                    row.add(rs.getString("fecha_regi"));
                    data.add(row);


                }
            }else if (tipo_tabla.equals("inventario")){
                while (rs.next()){
                    Vector<Object> row = new Vector<Object>();
                    // Vector<Object> columnNames = new Vector<Object>();
                    row.add(rs.getString("codigo_barr"));
                    row.add(rs.getString("cod_proveedor"));
                    row.add(rs.getString("nombre"));
                    row.add(rs.getString("unidad_medida"));
                    row.add(rs.getString("precio_unit"));
                    row.add(rs.getString("presentacion"));
                    row.add(rs.getString("stock"));
                    data.add(row);


                }
            }else if (tipo_tabla.equals("ventas")){
                System.out.println("Entro a la opcion de ventas");

                while (rs.next()){
                    Vector<Object> row = new Vector<Object>();
                    // Vector<Object> columnNames = new Vector<Object>();
                    row.add(rs.getString("codigo_barr"));
                    row.add(rs.getString("cod_cliente"));
                    row.add(rs.getString("cod_factura"));
                    row.add(rs.getString("nombre_prod"));
                    row.add(rs.getString("nombre_vend"));
                    row.add(rs.getString("cantidad"));
                    row.add(rs.getString("precio_unit"));
                    row.add(rs.getString("precio_total"));
                    data.add(row);

                    System.out.println(rs.getString("codigo_barr"));

                }
            }
            model = new DefaultTableModel(data, columnas);
            lista.setModel(model);
            lista.repaint();
            System.out.println("Refresco");

        }catch(Exception e){
            System.out.println("aqui trono"+e);
        }

    }
    public String buscarProductoNuevaVenta(Statement st, String Consulta, JTextField txtProducto, JTextField txtPrecio, String presentacion, String medida){
        try{
            rs = st.executeQuery(Consulta);
            rs.next();
            txtProducto.setText(rs.getString("nombre"));
            txtPrecio.setText(rs.getString("precio_unit"));
            presentacion = rs.getString("presentacion");
            medida = rs.getString("unidad_medida");
            this.medida = medida;

        }catch (Exception e){
            System.out.println(e);
        }
        return presentacion;
    }

    public void agregarVenta(Statement st, JTable tblTablaNuevaVentas, DefaultTableModel modelNuevaVenta, String Cliente){
        String nuevaFactura = "insert into factura(hora) values(SYSDATE)";
        try {
            rs = st.executeQuery(nuevaFactura);
            rs = st.executeQuery("commit");

            int cantFilas = modelNuevaVenta.getRowCount();
            System.out.println("FIlas, "+cantFilas);

            for (int i = 0; i < cantFilas; i++){
                String nameProducto = modelNuevaVenta.getValueAt(i, 0).toString();
                String presentacion = modelNuevaVenta.getValueAt(i, 1).toString();
                String cantidad = modelNuevaVenta.getValueAt(i, 3).toString();
                String precioTotal = modelNuevaVenta.getValueAt(i, 4).toString();

                String Consulta = "insert into ventas values((select codigo_barr from inventario where nombre = '"+nameProducto+"' and presentacion = '"+presentacion+"'),(select cod_cliente from clientes where nombre = '"+Cliente+"'),(select cod_factura from factura order by cod_factura desc FETCH FIRST 1 ROW ONLY),'"+nameProducto+"',SYSDATE, (SELECT USER FROM DUAL), "+cantidad+",(select precio_unit from inventario where codigo_barr = (select codigo_barr from inventario where nombre = '"+nameProducto+"' and presentacion = '"+presentacion+"')),"+precioTotal+")";
                rs = st.executeQuery(Consulta);
            }
            rs = st.executeQuery("commit");

            JOptionPane.showMessageDialog(null, "Ventas Realizada Con Exito!!!");

        }catch (Exception e){
            System.out.println(e);
        }


    }
    public void refreshComboBox(Statement st, String consulta, String ComboBox, JComboBox<String> Combo){
        try {
            rs = st.executeQuery(consulta);

            if (ComboBox.equals("cbbNuevaVentaClientes")){
                while (rs.next()){
                    Combo.addItem(rs.getString("nombre"));
                }
            }
            Combo.repaint();

        }catch (Exception e){
            System.out.println(e);
        }

    }

    public void refreshServicios(){
        HttpRequest peticion = HttpRequest.newBuilder().GET().
                uri(URI.create(Url)) .build();

        try {
            HttpResponse<String> response = cliente.send(peticion, HttpResponse.BodyHandlers.ofString());

            java.util.List<Consulta> empleadoLista = getData(response.body(), new TypeReference<List<Consulta>>() {


            });

            Vector<Vector<Object>> dataConsultas = new Vector<Vector<Object>>();

            for (Consulta consulta : empleadoLista) {
                Object datos[] = {consulta.getCon_clave(), consulta.getDen_clave(), consulta.getPac_clave()
                        , consulta.getFecha(), consulta.getObservaciones(), consulta.getHora(), consulta.getPendiente(), consulta.getServicio()
                };

                System.out.println("el servicio fue: "+consulta.getFecha());

                Vector<Object> row = new Vector<>();

                row.add(consulta.getDen_clave());
                row.add(consulta.getPac_clave());
                row.add(consulta.getFecha());
                row.add(consulta.getObservaciones());
                row.add(consulta.getHora());

                dataConsultas.add(row);

            }
            //Aqui debi de meter los datos de la API a la Jtable

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
    public void refresCbbServicios(JComboBox<String> jccServicio){
        HttpRequest peticionS = HttpRequest.newBuilder().GET().
                uri(URI.create(UrlServicios)) .build();

        try {
            HttpResponse<String> response = cliente.send(peticionS, HttpResponse.BodyHandlers.ofString());

            java.util.List<Servicios> ServiciosLista = getData(response.body(), new TypeReference<List<Servicios>>() {

            });

            Vector<Vector<Object>> dataConsultas = new Vector<Vector<Object>>();

            for (Servicios servicio : ServiciosLista) {
                Object datos[] = {servicio.getServicio()
                };

                jccServicio.addItem(servicio.getServicio());


            }
            //Aqui debi de meter los datos de la API a la Jtable

        }catch (Exception e){
            System.out.println("El error es e"+e);
        }


    }
    public void refresCbbDentistas(JComboBox<String> jccDentistas){
        HttpRequest peticionD = HttpRequest.newBuilder().GET().
                uri(URI.create(UrlDentistas)) .build();

        try {
            HttpResponse<String> response = cliente.send(peticionD, HttpResponse.BodyHandlers.ofString());

            java.util.List<Dentistas> DentistaLista = getData(response.body(), new TypeReference<List<Dentistas>>() {

            });

            Vector<Vector<Object>> dataConsult = new Vector<Vector<Object>>();

            for (Dentistas dentista : DentistaLista) {
                Object datos[] = {dentista.getNombre()
                };

                jccDentistas.addItem(dentista.getNombre()+" "+dentista.getApp()+" "+dentista.getApm());


            }
            //Aqui debi de meter los datos de la API a la Jtable

        }catch (Exception e){
            System.out.println("El error es e"+e);
        }


    }

    public String getRegistroConsulta(int id) {

        String UrlCompleta = UrlGetR+id;

        HttpRequest peticionD = HttpRequest.newBuilder().GET().
                uri(URI.create(UrlCompleta)) .build();

        try {
            HttpResponse<String> response = cliente.send(peticionD, HttpResponse.BodyHandlers.ofString());

            java.util.List<Consulta> ConsultR = getData(response.body(), new TypeReference<List<Consulta>>() {

            });

            Vector<Vector<Object>> dataConsult = new Vector<Vector<Object>>();

            for (Consulta consulta : ConsultR) {
                Object datos[] = {consulta.getObservaciones()
                };
                System.out.println("El resultado de la consultaes: "+consulta.getObservaciones());
                return consulta.getObservaciones();
            }
            //Aqui debi de meter los datos de la API a la Jtable
            return null;

        }catch (Exception e){
            System.out.println("El error es e"+e);
        }
        return null;
    }

    public Boolean actualizarConsulta(Consulta c) {
        try {
            // URL del endpoint de tu API para actualizar una consulta
            URL url = new URL("http://localhost:8081/setUpdateConsulta/" + c.getCon_clave());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Método POST
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Convertir el objeto Consulta a JSON
            Gson gson = new Gson();
            String jsonConsulta = gson.toJson(c);

            // Escribir los datos JSON en el cuerpo de la solicitud
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonConsulta.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Verificación del código de respuesta
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Lectura de la respuesta (opcional)
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                System.out.println(response.toString());
                return true;
            } else {
                System.out.println("Error al actualizar el registro. Código de respuesta: " + responseCode);
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getIdDentistaNombre(String dent) {
        String nombreDentista = dent;
        String nombreDentistaEncoded = URLEncoder.encode(nombreDentista, StandardCharsets.UTF_8);
        String url = "http://localhost:8081/saveCita/" + nombreDentistaEncoded;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        CompletableFuture<HttpResponse<String>> futureResponse = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        try {
            HttpResponse<String> response = futureResponse.get();
            if (response.statusCode() == 200) {
                String responseBody = response.body();
                return responseBody;
            } else {
                return null; // O algún valor que indique un error
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null; // Manejar la excepción
        }

    }

    public String getIdServ(String serv) {
        String nombreDentista = serv;
        String nombreServicioencode = URLEncoder.encode(nombreDentista, StandardCharsets.UTF_8);
        String url = "http://localhost:8081/saveCita/extraIdServ/" + nombreServicioencode;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        CompletableFuture<HttpResponse<String>> futureResponse = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        try {
            HttpResponse<String> response = futureResponse.get();
            if (response.statusCode() == 200) {
                String responseBody = response.body();
                return responseBody;
            } else {
                return null; // O algún valor que indique un error
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null; // Manejar la excepción
        }

    }
}