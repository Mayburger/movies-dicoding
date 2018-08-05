package com.nacoda.movies.libs;

import java.util.ArrayList;

/**
 * Created by Mayburger on 1/11/18.
 */

public class Helper {

    public static String getGenres(String data, String language) {
        if (language.equals("English")) {
            return String.valueOf(data)
                    .replace("28", "Action")
                    .replace("27", "Horror")
                    .replace("12", "Adventure")
                    .replace("16", "Animation")
                    .replace("35", "Comedy")
                    .replace("80", "Crime")
                    .replace("99", "Documentary")
                    .replace("18", "Drama")
                    .replace("14", "Fantasy")
                    .replace("10402", "Music")
                    .replace("9648", "Mystery")
                    .replace("10749", "Romance")
                    .replace("878", "Science Fiction")
                    .replace("10770", "TV Movie")
                    .replace("10752", "War")
                    .replace("37", "Western")
                    .replace("10751", "Family")
                    .replace("53", "Thriller")
                    .replace("36", "History")
                    .replace("[", "")
                    .replace("]", "")
                    .replace(",", ", ");
        } else {
            return String.valueOf(data)
                    .replace("28", "Aksi")
                    .replace("27", "Kengerian")
                    .replace("12", "Petualangan")
                    .replace("16", "Animasi")
                    .replace("35", "Komedi")
                    .replace("80", "Kejahatan")
                    .replace("99", "Dokumenter")
                    .replace("18", "Drama")
                    .replace("14", "Fantasi")
                    .replace("10402", "Musik")
                    .replace("9648", "Misteri")
                    .replace("10749", "Percintaan")
                    .replace("878", "Cerita Fiksi")
                    .replace("10770", "Film TV")
                    .replace("10752", "Peperangan")
                    .replace("37", "Barat")
                    .replace("10751", "Keluarga")
                    .replace("53", "Cerita Seru")
                    .replace("36", "Sejarah")
                    .replace("[", "")
                    .replace("]", "")
                    .replace(",", ", ");
        }
    }
}
