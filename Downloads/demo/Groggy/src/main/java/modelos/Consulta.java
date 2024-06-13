package modelos;

public class Consulta {
    public int con_clave;
    public int den_clave;
    public int pac_clave;
    public String fecha;
    public String observaciones;
    public String hora;
    public String pendiente;
    public int servicio;

    public Consulta(){

    }

    public Consulta(int con_clave, int den_clave, int pac_clave, String fecha, String observaciones, String hora, String pendiente, int servicio) {
        this.con_clave = con_clave;
        this.den_clave = den_clave;
        this.pac_clave = pac_clave;
        this.fecha = fecha;
        this.observaciones = observaciones;
        this.hora = hora;
        this.pendiente = pendiente;
        this.servicio = servicio;
    }

    public int getCon_clave() {
        return con_clave;
    }

    public void setCon_clave(int con_clave) {
        this.con_clave = con_clave;
    }

    public int getDen_clave() {
        return den_clave;
    }

    public void setDen_clave(int den_clave) {
        this.den_clave = den_clave;
    }

    public int getPac_clave() {
        return pac_clave;
    }

    public void setPac_clave(int pac_clave) {
        this.pac_clave = pac_clave;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getPendiente() {
        return pendiente;
    }

    public void setPendiente(String pendiente) {
        this.pendiente = pendiente;
    }

    public int getServicio() {
        return servicio;
    }

    public void setServicio(int servicio) {
        this.servicio = servicio;
    }
}
