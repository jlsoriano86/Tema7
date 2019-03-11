package com.example.tema7.Model;

public class Lugar {
    private Long id;
    private String nombre;
    private Integer categoria;
    private Float longitud;
    private Float latitud;
    private Float valoracion;
    private String comentarios;


    public Lugar() {
        this.id = null;
        this.nombre = "";
        this.categoria = 0;
        this.longitud = 0.0f;
        this.latitud = 0.0f;
        this.valoracion = 0.0f;
        this.comentarios = "";
    }

    public Lugar(String nombre, Integer categoria, Float longitud, Float latitud, Float valoracion, String comentarios) {
        this.id = null;
        this.nombre = nombre;
        this.categoria = categoria;
        this.longitud = longitud;
        this.latitud = latitud;
        this.valoracion = valoracion;
        this.comentarios = comentarios;
    }

    public Lugar(Long id, String nombre, Integer categoria, Float longitud, Float latitud, Float valoracion, String comentarios) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.longitud = longitud;
        this.latitud = latitud;
        this.valoracion = valoracion;
        this.comentarios = comentarios;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCategoria() {
        return categoria;
    }

    public void setCategoria(Integer categoria) {
        this.categoria = categoria;
    }

    public Float getLongitud() {
        return longitud;
    }

    public void setLongitud (Float longitud) { this.longitud = longitud; }

    public Float getLatitud() {
        return latitud;
    }

    public void setLatitud (Float latitud) { this.latitud = latitud; }

    public Float getValoracion() {
        return valoracion;
    }

    public void setValoracion (Float valoracion) { this.valoracion = valoracion; }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    @Override
    public String toString() {
        return "Lugar {" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", categoria=" + categoria +
                ", longitud=" + longitud +
                ", latitud=" + latitud +
                ", valoracion=" + valoracion +
                ", comentarios='" + comentarios + '\'' +
                '}';
    }
}
