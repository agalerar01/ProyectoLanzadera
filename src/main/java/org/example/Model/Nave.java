package org.example.Model;

import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.example.Enums.TipoNave;

public class Nave {

    private ObjectId id;
    private String nombre;
    @BsonProperty(value = "lanzadera_id")
    private ObjectId lanzaderaId;
    private TipoNave tipo;
    private Double combustible;
    private Double oxigeno;
    @BsonProperty(value = "dias_duracion_investigacion")
    private Integer diasDuracion;
    @BsonIgnore
    private Double modificador;
    @BsonIgnore
    private Integer capacidad;

    public Nave() {
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

    public ObjectId getLanzaderaId() {
        return lanzaderaId;
    }

    public void setLanzaderaId(ObjectId lanzaderaId) {
        this.lanzaderaId = lanzaderaId;
    }

    public TipoNave getTipo() {
        return tipo;
    }

    public void setTipo(TipoNave tipoNave) {
        tipo = tipoNave;
    }

    public Double getCombustible() {
        return combustible;
    }

    public void setCombustible(Double combustible) {
        this.combustible = combustible;
    }

    public Double getOxigeno() {
        return oxigeno;
    }

    public void setOxigeno(Double oxigeno) {
        this.oxigeno = oxigeno;
    }

    public Integer getDiasDuracion() {
        return diasDuracion;
    }

    public void setDiasDuracion(Integer diasDuracion) {
        this.diasDuracion = diasDuracion;
    }

    public Double getModificador() {
        return modificador;
    }

    public void setModificador(Double modificador) {
        this.modificador = modificador;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }
}
