package pe.apiconz.android.cooltura.app.model.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Armando on 12/13/2015.
 */

public class EventEntity implements Parcelable {

    public static final Parcelable.Creator<EventEntity> CREATOR = new Parcelable.Creator() {
        public EventEntity createFromParcel(Parcel in) {
            return new EventEntity(in);
        }

        public EventEntity[] newArray(int size) {
            return new EventEntity[size];
        }
    };

    private String nombre;
    private String fecha;
    private String hora;
    private int id;

    public EventEntity(String nombre, String fecha, String hora, int id) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.hora = hora;
        this.id = id;
    }

    public EventEntity(Parcel in) {
        nombre = in.readString();
        fecha = in.readString();
        hora = in.readString();
        id = in.readInt();
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(fecha);
        dest.writeString(hora);
        dest.writeInt(id);

    }
}

