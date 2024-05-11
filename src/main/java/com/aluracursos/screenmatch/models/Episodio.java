package com.aluracursos.screenmatch.models;

import java.time.LocalDate;

public class Episodio {

    private Integer temporadas;
    private String titulo;
    private Integer numeroEpisodio;
    private Double evaluacion;
    private LocalDate fechaDeLanzamiento;

    public Episodio(Integer temporada, DatosEpisodios d) {
        this.temporadas = temporada;
        this.titulo = d.titulo();
        this.numeroEpisodio = d.numeroEpisodio();
        try {this.evaluacion = Double.valueOf(d.evaluacion());} catch (NumberFormatException e) {this.evaluacion = 0.0;}
        this.fechaDeLanzamiento = LocalDate.parse(d.fechaDeLanzamiento());
    }

    //Getters
    public Integer getTemporadas() {return temporadas;}

    public String getTitulo() {return titulo;}

    public Integer getNumeroEpisodio() {return numeroEpisodio;}

    public LocalDate getFechaDeLanzamiento() {return fechaDeLanzamiento;}

    public Double getEvaluacion() {return evaluacion;}

    //Setters
    public void setTemporadas(Integer temporadas) {this.temporadas = temporadas;}

    public void setNumeroEpisodio(Integer numeroEpisodio) {this.numeroEpisodio = numeroEpisodio;}

    public void setTitulo(String titulo) {this.titulo = titulo;}

    public void setFechaDeLanzamiento(LocalDate fechaDeLanzamiento) {this.fechaDeLanzamiento = fechaDeLanzamiento;}

    public void setEvaluacion(Double evaluacion) {this.evaluacion = evaluacion;}

    @Override
    public String toString() {
        return
                "Temporadas: " + temporadas +
                ", Titulo: " + titulo + '\'' +
                ", NumeroEpisodio: " + numeroEpisodio +
                ", Evaluacion: " + evaluacion +
                ", FechaDeLanzamiento: " + fechaDeLanzamiento;
    }
}
