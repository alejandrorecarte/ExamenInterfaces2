package model;

import java.io.Serializable;
import java.util.Date;

public class Tarea implements Serializable {

    private String nombre;
    private String descripcion;
    private Date fecha;
    private String realizada;
    private int prioridad;
    public Tarea(String nombre, Date fecha, String descripcion, String realizada, int prioridad) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.realizada = realizada;
        this.prioridad = prioridad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRealizada() {
        return realizada;
    }

    public void setRealizada(String realizada) {
        this.realizada = realizada;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }
}
