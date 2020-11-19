package com.example.practicamoviles_1tr.common;

public class Constantes {
    public static final String LATITUDE = "LATITUDE";
    public static final String LONGITUDE = "LONGITUDE";
    //constante para el broadcast
    public static final String INTENT_LOCALIZATION_ACTION = "location_position";

    //urls de la api (entrypoint y endpoints para polideportivos y otro para piscinas)
    public static final String ENTRY_POINT= "https://datos.madrid.es/egob/";
    public static final String END_POINT_SPORTS= "catalogo/200186-0-polideportivos.json";
    public static final String END_POINT_POOLS= "catalogo/210227-0-piscinas-publicas.json";
    //duracion de la pantalla de carga
    public static final int SPLASH_DURATION = 2000;
}
