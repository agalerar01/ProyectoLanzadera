package org.example.Model;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.List;

public class Tripulante {

    private ObjectId id;
    @BsonProperty(value = "nombre")
    private String Nombre;
    @BsonProperty(value = "peso")
    private Integer Peso;
    @BsonProperty(value = "tipo")
    private TipoTripu Tipo;
    @BsonProperty(value = "disponible")
    private boolean Disponible;
    @BsonProperty(value = "lanzadera_id")
    private List<ObjectId> LanzaderaIds;

    public Tripulante() {
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

    public Integer getPeso() {
        return Peso;
    }

    public void setPeso(Integer peso) {
        Peso = peso;
    }

    public TipoTripu getTipo() {
        return Tipo;
    }

    public void setTipo(TipoTripu tipo) {
        Tipo = tipo;
    }

    public boolean isDisponible() {
        return Disponible;
    }

    public void setDisponible(boolean disponible) {
        Disponible = disponible;
    }

    public List<ObjectId> getLanzaderaIds() {
        return LanzaderaIds;
    }

    public void setLanzaderaIds(List<ObjectId> lanzaderaIds) {
        LanzaderaIds = lanzaderaIds;
    }
}
