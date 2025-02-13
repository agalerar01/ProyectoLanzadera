package org.example.Model;

import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.example.Enums.TipoNave;

public class Nave {

    private ObjectId id;
    @BsonProperty(value = "nombre")
    private String Nombre;
    @BsonProperty(value = "lanzadera_id")
    private ObjectId LanzaderaId;
    @BsonProperty(value = "tipo")
    private TipoNave Tipo;
    @BsonProperty(value = "combustible")
    private Integer Combustible;
    @BsonProperty(value = "oxigeno")
    private Integer Oxigeno;
    @BsonProperty(value = "dias_duracion_investigacion")
    private Integer diasDuracion;
    @BsonIgnore
    private Double Modificador;

    public Nave() {
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

    public ObjectId getLanzaderaId() {
        return LanzaderaId;
    }

    public void setLanzaderaId(ObjectId lanzaderaId) {
        LanzaderaId = lanzaderaId;
    }

    public TipoNave getTipo() {
        return Tipo;
    }

    public void setTipo(TipoNave tipoNave) {
        Tipo = tipoNave;
    }

    public Integer getCombustible() {
        return Combustible;
    }

    public void setCombustible(Integer combustible) {
        Combustible = combustible;
    }

    public Integer getOxigeno() {
        return Oxigeno;
    }

    public void setOxigeno(Integer oxigeno) {
        Oxigeno = oxigeno;
    }

    public Integer getDiasDuracion() {
        return diasDuracion;
    }

    public void setDiasDuracion(Integer diasDuracion) {
        this.diasDuracion = diasDuracion;
    }

    public Double getModificador() {
        return Modificador;
    }

    public void setModificador(Double modificador) {
        Modificador = modificador;
    }
}
