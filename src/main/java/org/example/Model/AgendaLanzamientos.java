package org.example.Model;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.example.Enums.Estado;

import java.time.LocalDate;
import java.util.List;

public class AgendaLanzamientos {

    private ObjectId id;
    @BsonProperty(value = "fecha")
    private LocalDate Fecha;
    @BsonProperty(value = "lanzadera_id")
    private ObjectId LanzaderaId;
    @BsonProperty(value = "nave_id")
    private ObjectId NaveId;
    @BsonProperty(value = "estado")
    private Estado Estado;
    @BsonProperty(value = "plan_vuelo")
    private List<List<String>> PlanVuelo;
    @BsonProperty(value = "tripulacion")
    private List<ObjectId> TripulacionIds;

    public AgendaLanzamientos() {
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return Fecha;
    }

    public void setFecha(LocalDate fecha) {
        Fecha = fecha;
    }

    public ObjectId getLanzaderaId() {
        return LanzaderaId;
    }

    public void setLanzaderaId(ObjectId lanzaderaId) {
        LanzaderaId = lanzaderaId;
    }

    public ObjectId getNaveId() {
        return NaveId;
    }

    public void setNaveId(ObjectId naveId) {
        NaveId = naveId;
    }

    public org.example.Enums.Estado getEstado() {
        return Estado;
    }

    public void setEstado(org.example.Enums.Estado estado) {
        Estado = estado;
    }

    public List<List<String>> getPlanVuelo() {
        return PlanVuelo;
    }

    public void setPlanVuelo(List<List<String>> planVuelo) {
        PlanVuelo = planVuelo;
    }


    public List<ObjectId> getTripulacionIds() {
        return TripulacionIds;
    }

    public void setTripulacionIds(List<ObjectId> tripulacionIds) {
        TripulacionIds = tripulacionIds;
    }
}
