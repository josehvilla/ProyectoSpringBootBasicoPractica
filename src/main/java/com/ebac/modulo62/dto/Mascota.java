package com.ebac.modulo62.dto;

public class Mascota {
    private String nombre;
    private String dueño;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDueño() {
        return dueño;
    }

    public void setDueño(String dueño) {
        this.dueño = dueño;
    }

    @Override
    public String toString() {
        return "Mascota{" +
                "nombre='" + nombre + '\'' +
                ", dueño='" + dueño + '\'' +
                '}';
    }
}
