package com.aluracursos.screenmatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosSeries(
        @JsonAlias("Title") String titulo,
        @JsonAlias("totalSeasons") Integer Temporadas,
        @JsonAlias("imdbRating") String evaluacion) {
}
