package org.example.Model;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class Carga {

    private ObjectId id;
    private String nombre;
    @BsonProperty(value = "cantidad_por_tipo")
    private CantidadPorTipo cantidadPorTipo;
    @BsonProperty(value = "peso_por_unidad")
    private Double pesoPorUnidad;

    public Carga() {
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public CantidadPorTipo getCantidadPorTipo() {
        return cantidadPorTipo;
    }

    public void setCantidadPorTipo(CantidadPorTipo cantidadPorTipo) {
        this.cantidadPorTipo = cantidadPorTipo;
    }

    public Double getPesoPorUnidad() {
        return pesoPorUnidad;
    }

    public void setPesoPorUnidad(Double pesoPorUnidad) {
        this.pesoPorUnidad = pesoPorUnidad;
    }
}
