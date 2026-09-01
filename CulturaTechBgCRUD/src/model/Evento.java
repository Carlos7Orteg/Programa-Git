package model;

import java.time.LocalDateTime;

public class Evento {
    private int idEvento;
    private String titulo;
    private String descripcion;
    private LocalDateTime fechaHora;
    private double costo;
    private String imagen;
    private String estado;
    private int idCategoria;
    private int idLugar;
    
    public Evento(){
    }
    
    public Evento(int idEvento, String titulo, String descripcion,
            LocalDateTime fechaHora, double costo, String imagen,
            String estado, int idCategoria, int idLugar) {
    
        this.idEvento = idEvento;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaHora = fechaHora;
        this.costo = costo;
        this.imagen = imagen;
        this.estado = estado;
        this.idCategoria = idCategoria;
        this.idLugar = idLugar;
    }
    
    public int getIdEvento(){
        return idEvento;
    }
    
    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
     public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getIdLugar() {
        return idLugar;
    }

    public void setIdLugar(int idLugar) {
        this.idLugar = idLugar;
    }
}
