package com.example.practicamoviles_1tr.common;

public class Constantes {
    public static final String TITLE = "My location";
    public static final String DESCRIPTION_KEY = "DESCRIPTION_KEY";

    //constantes de los bundle
    public static final  String CURRENT_LOCATION_LATITUDE = "currentLocationLatitude";
    public static final  String CURRENT_LOCATION_LONGITUDE = "currentLocationLongitude";

    public static final  String MAPPOINT_LATITUDE = "mapPointLatitude";
    public static final  String MAPPOINT_LONGITUDE = "mapPointLongitude";

    public static final String LATITUDE = "LATITUDE";
    public static final String LONGITUDE = "LONGITUDE";
    public static final String URL_BUNDLE = "URL";


    public static final long DISTANCE = 8000;
    //constante para el broadcast
    public static final String INTENT_LOCALIZATION_ACTION = "location_position";
    //urls de la api (entrypoint y endpoints para polideportivos y otro para piscinas)
    public static final String ENTRY_POINT= "https://datos.madrid.es/egob/";
    public static final String END_POINT_SPORTS= "catalogo/200215-0-instalaciones-deportivas.json";
    public static final String END_POINT_POOLS= "catalogo/210227-0-piscinas-publicas.json";
    //duracion de la pantalla de carga
    public static final int SPLASH_DURATION = 2000;

    //constante para clave de sharedpreferences
    public static final String CLAVE_SAVELOCATION = "saveLocation";
    public static final String CLAVE_SAVELOCATION_LONGITUDE = "saveLongitude";
    public static final String CLAVE_SAVELOCATION_LATITUDE = "saveLatitude";
    public static final String CLAVE_PREFERENCES = "favoritos";
    //constante para array de sharedpreferences
    public static final String CLAVE_PREFERENCES_ARRAY = "favoritosArray";

    public static final String WEB_1 = "https://guiafitness.com/deportes/natacion";
    public static final String WEB_2 = "https://www.cnciudadalcorcon.es/";
    public static final String WEB_3 = "https://www.lomejordelbarrio.com/alcorcon/polideportivos";

}
