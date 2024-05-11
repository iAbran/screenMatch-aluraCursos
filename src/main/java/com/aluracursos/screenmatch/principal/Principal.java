package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.models.DatosEpisodios;
import com.aluracursos.screenmatch.models.DatosSeries;
import com.aluracursos.screenmatch.models.DatosTemporadas;
import com.aluracursos.screenmatch.models.Episodio;
import com.aluracursos.screenmatch.services.ConsumoAPI;
import com.aluracursos.screenmatch.services.ConvierteDatos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=43e87bc2";
    

    public void MuestraElMenu() {
        System.out.println("Por favor escribe el nombre de la serie que deseas buscar");
        //Busca los datos generales de las series
        var nombreSerie = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + API_KEY);
        var datos = conversor.obtenerDatos(json, DatosSeries.class);
        System.out.println(datos);

        //Busca los datos de todas las temporadas
        ArrayList<DatosTemporadas> temporadas = new ArrayList<>();
        for (int i = 1; i < datos.Temporadas(); i++) {
            json = consumoAPI.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + "&Season="+ i + API_KEY);
            var datosTemporadas = conversor.obtenerDatos(json, DatosTemporadas.class);
            temporadas.add(datosTemporadas);
        }
//        temporadas.forEach(System.out::println);

        //Mostrar solo el titulo de los episodios para las temporadas
//        for (int i = 0; i < datos.Temporadas(); i++) {
//            List<DatosEpisodios> episodiosTemposada = temporadas.get(1).episodios();
//            for (int j = 0; j < episodiosTemposada.size(); j++) {
//                System.out.println(episodiosTemposada.get(j).titulo());;
//            }
//        }
//        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        //Convertir todas las informaciones en una lista del tipo DatoEpisodio

        List<DatosEpisodios> datosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

        //imprimir los Top 5 episodios
//        System.out.println("Top 5 episodios");
//
//        datosEpisodios.stream()
//                .filter(e -> !e.evaluacion().equalsIgnoreCase("N/A"))
//                .sorted(Comparator.comparing(DatosEpisodios::evaluacion).reversed())
//                .limit(6)
//                .forEach(System.out::println);

        //Convirtiendo los datos a una lista deL tipo episodio
        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.temporada(),d)))
                .collect(Collectors.toList());
//        episodios.forEach(System.out::println);

        //Busqueda de episodio apartir de x año
//        System.out.println("Ingrese de que año quieres comenzar ver los episodios");
//        var fecha = teclado.nextInt();
//        teclado.nextInt();
//
//        LocalDate fechaBusqueda = LocalDate.of(fecha, 1, 1);
//
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        episodios.stream()
//                .filter(e -> e.getFechaDeLanzamiento() != null && e.getFechaDeLanzamiento().isAfter(fechaBusqueda))
//                .forEach(e -> System.out.println(
//                        "Temporada: " + e.getTemporadas() +
//                                ", Episodio: " + e.getTitulo() +
//                                ", Fecha de Lanzamiento: " + e.getFechaDeLanzamiento().format(dtf)
//                ));

        //Busca espisodios por pedasos de titulos
//        System.out.println("ingrese el titulo del episodio que deseas");
//        var pedazoTitulo = teclado.nextLine();
//        teclado.nextLine();
//        Optional<Episodio> episodioBuscado = episodios.stream()
//                .filter(e -> e.getTitulo().toUpperCase().contains(pedazoTitulo.toUpperCase()))
//                .findFirst();
//        if (episodioBuscado.isPresent()) {
//            System.out.println("Episodio encontrado");
//            System.out.println("Resultados: " + episodioBuscado.get());
//        }else {
//            System.out.println("No se encontro ningun resultado");
//        }

        Map<Integer, Double> EvaluacionesPorTemporadas = episodios.stream()
                .filter(e -> e.getEvaluacion() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporadas,
                        Collectors.averagingDouble(Episodio::getEvaluacion)));
        System.out.println(EvaluacionesPorTemporadas);

        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getEvaluacion() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getEvaluacion));
        System.out.println("Episodio Mas evaluado: " + est.getMax());
        System.out.println("Episodio Menos evaluado: " + est.getMin());
        System.out.println("Promedio de las evaluaciobes: " + est.getAverage());
    }
}
