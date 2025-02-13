package org.example.Model;

import org.bson.codecs.pojo.annotations.BsonProperty;

public class CantidadPorTipo {

    private Integer exploracion;
    private Integer transbordador;
    private Integer investigacion;

    public CantidadPorTipo() {
    }

    public Integer getExploracion() {
        return exploracion;
    }

    public void setExploracion(Integer exploracion) {
        this.exploracion = exploracion;
    }

    public Integer getTransbordador() {
        return transbordador;
    }

    public void setTransbordador(Integer transbordador) {
        this.transbordador = transbordador;
    }

    public Integer getInvestigacion() {
        return investigacion;
    }

    public void setInvestigacion(Integer investigacion) {
        this.investigacion = investigacion;
    }
}
