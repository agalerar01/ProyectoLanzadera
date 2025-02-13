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
}
