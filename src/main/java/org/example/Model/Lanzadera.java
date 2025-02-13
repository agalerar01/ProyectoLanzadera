package org.example.Model;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class Lanzadera {

    private ObjectId id;
    private String nombre;
    @BsonProperty(value= "capacidad_maxima_combustible")
    private Double capacidadMaximaCombustible;
    @BsonProperty(value = "combustible_disponible")
    private Double combustibleDisponible;
    @BsonProperty(value= "capacidad_maxima_oxigeno")
    private Double capacidadMaximaOxigeno;
    @BsonProperty(value = "oxigeno_disponible")
    private Double oxigenoDisponible;

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

    public Double getCapacidadMaximaCombustible() {
        return capacidadMaximaCombustible;
    }

    public void setCapacidadMaximaCombustible(Double capacidadMaximaCombustible) {
        this.capacidadMaximaCombustible = capacidadMaximaCombustible;
    }

    public Double getCombustibleDisponible() {
        return combustibleDisponible;
    }

    public void setCombustibleDisponible(Double combustibleDisponible) {
        this.combustibleDisponible = combustibleDisponible;
    }

    public Double getCapacidadMaximaOxigeno() {
        return capacidadMaximaOxigeno;
    }

    public void setCapacidadMaximaOxigeno(Double capacidadMaximaOxigeno) {
        this.capacidadMaximaOxigeno = capacidadMaximaOxigeno;
    }

    public Double getOxigenoDisponible() {
        return oxigenoDisponible;
    }

    public void setOxigenoDisponible(Double oxigenoDisponible) {
        this.oxigenoDisponible = oxigenoDisponible;
    }
}
