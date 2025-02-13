package org.example.Model;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class Lanzadera {

    private ObjectId id;
    @BsonProperty(value= "nombre")
    private String Nombre;
    @BsonProperty(value= "capacidad_maxima_combustible")
    private Integer CapacidadMaximaCombustible;
    @BsonProperty(value = "combustible_disponible")
    private Integer CombustibleDisponible;
    @BsonProperty(value= "capacidad_maxima_oxigeno")
    private Integer CapacidadMaximaOxigeno;
    @BsonProperty(value = "oxigeno_disponible")
    private Integer OxigenoDisponible;

    public Lanzadera() {
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

    public Integer getCapacidadMaximaCombustible() {
        return CapacidadMaximaCombustible;
    }

    public void setCapacidadMaximaCombustible(Integer capacidadMaximaCombustible) {
        CapacidadMaximaCombustible = capacidadMaximaCombustible;
    }

    public Integer getCombustibleDisponible() {
        return CombustibleDisponible;
    }

    public void setCombustibleDisponible(Integer combustibleDisponible) {
        CombustibleDisponible = combustibleDisponible;
    }

    public Integer getCapacidadMaximaOxigeno() {
        return CapacidadMaximaOxigeno;
    }

    public void setCapacidadMaximaOxigeno(Integer capacidadMaximaOxigeno) {
        CapacidadMaximaOxigeno = capacidadMaximaOxigeno;
    }

    public Integer getOxigenoDisponible() {
        return OxigenoDisponible;
    }

    public void setOxigenoDisponible(Integer oxigenoDisponible) {
        OxigenoDisponible = oxigenoDisponible;
    }
}
