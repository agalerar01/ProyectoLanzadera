package org.example.Model;

import org.bson.codecs.pojo.annotations.BsonProperty;

public class CantidadPorTipo {

    @BsonProperty(value = "exploracion")
    private Integer Exploracion;
    @BsonProperty(value = "transbordador")
    private Integer Transbordador;
    @BsonProperty(value = "investigacion")
    private Integer Investigacion;

    public CantidadPorTipo() {
    }

    public Integer getExploracion() {
        return Exploracion;
    }

    public void setExploracion(Integer exploracion) {
        Exploracion = exploracion;
    }

    public Integer getTransbordador() {
        return Transbordador;
    }

    public void setTransbordador(Integer transbordador) {
        Transbordador = transbordador;
    }

    public Integer getInvestigacion() {
        return Investigacion;
    }

    public void setInvestigacion(Integer investigacion) {
        Investigacion = investigacion;
    }
}
