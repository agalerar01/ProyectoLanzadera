package org.example.Model;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class Carga {

    private ObjectId id;
    @BsonProperty(value = "nombre")
    private String Nombre;
    @BsonProperty(value = "cantidad_por_tipo")
    private CantidadPorTipo cantidadPorTipo;
    @BsonProperty(value = "peso_por_unidad")
    private Integer PesoPorUnidad;

    public Carga() {
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public CantidadPorTipo getCantidadPorTipo() {
        return cantidadPorTipo;
    }

    public void setCantidadPorTipo(CantidadPorTipo cantidadPorTipo) {
        this.cantidadPorTipo = cantidadPorTipo;
    }

    public Integer getPesoPorUnidad() {
        return PesoPorUnidad;
    }

    public void setPesoPorUnidad(Integer pesoPorUnidad) {
        PesoPorUnidad = pesoPorUnidad;
    }
}
