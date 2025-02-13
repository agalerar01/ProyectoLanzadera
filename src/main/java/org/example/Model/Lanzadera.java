package org.example.Model;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class Lanzadera {

    private ObjectId id;
    private String nombre;
    @BsonProperty(value= "capacidad_maxima_combustible")
    private Integer capacidadMaximaCombustible;
    @BsonProperty(value = "combustible_disponible")
    private Integer combustibleDisponible;
    @BsonProperty(value= "capacidad_maxima_oxigeno")
    private Integer capacidadMaximaOxigeno;
    @BsonProperty(value = "oxigeno_disponible")
    private Integer oxigenoDisponible;

    public Lanzadera() {
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

    public Integer getCapacidadMaximaCombustible() {
        return capacidadMaximaCombustible;
    }

    public void setCapacidadMaximaCombustible(Integer capacidadMaximaCombustible) {
        this.capacidadMaximaCombustible = capacidadMaximaCombustible;
    }

    public Integer getCombustibleDisponible() {
        return combustibleDisponible;
    }

    public void setCombustibleDisponible(Integer combustibleDisponible) {
        this.combustibleDisponible = combustibleDisponible;
    }

    public Integer getCapacidadMaximaOxigeno() {
        return capacidadMaximaOxigeno;
    }

    public void setCapacidadMaximaOxigeno(Integer capacidadMaximaOxigeno) {
        this.capacidadMaximaOxigeno = capacidadMaximaOxigeno;
    }

    public Integer getOxigenoDisponible() {
        return oxigenoDisponible;
    }

    public void setOxigenoDisponible(Integer oxigenoDisponible) {
        this.oxigenoDisponible = oxigenoDisponible;
    }
}
