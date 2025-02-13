package org.example.Model;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.example.Enums.TipoTripu;

import java.util.List;

public class Tripulante {

    private ObjectId id;
    private String nombre;
    private Double peso;
    private TipoTripu tipo;
    private boolean disponible;
    @BsonProperty(value = "lanzadera_id")
    private List<ObjectId> lanzaderaIds;

    public Tripulante() {
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

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public TipoTripu getTipo() {
        return tipo;
    }

    public void setTipo(TipoTripu tipo) {
        this.tipo = tipo;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public List<ObjectId> getLanzaderaIds() {
        return lanzaderaIds;
    }

    public void setLanzaderaIds(List<ObjectId> lanzaderaIds) {
        this.lanzaderaIds = lanzaderaIds;
    }
}
