package com.company;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class readFile {

    static Map<Integer, actor> schauspieler = new HashMap<>();
    static Map<Integer, movie> filme = new HashMap<>();
    static Map<Integer, director> regisseur = new HashMap<>();

    //Methode zum Einlesen der Datei
    static void load(String datName) {
        File file = new File(datName);
        //Falls die Datei nicht eingelesen werden kann, wird das Programm beendet
        if (!file.canRead() || !file.isFile())
            System.exit(0);
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(datName));
            String zeile;
            int i = 0;
            String[] result;
            while ((zeile = in.readLine()) != null) {
                if (zeile.startsWith("New_Entity:")) {
                    i++;
                    zeile = in.readLine();
                }
                switch (i) {
                    case 1:
                        result = zeile.split("\",\"");
                        result[0] = result[0].replaceFirst("\"", "");
                        result[1] = result[1].replace("\"", "");
                        if (result[1].startsWith(" "))
                            result[1] = result[1].replaceFirst(" ", "");
                        schauspieler.put(Integer.parseInt(result[0]), new actor(result[0], result[1]));
                        break;
                    case 2:
                        result = zeile.split("\",\"");
                        result[0] = result[0].replaceFirst("\"", "");
                        result[6] = result[6].replace("\"", "");
                        filme.put(Integer.parseInt(result[0]), new movie(result[0], result[1], result[2], result[3], result[4], result[5], result[6]));
                        break;
                    case 3:
                        result = zeile.split("\",\"");
                        result[0] = result[0].replaceFirst("\"", "");
                        result[1] = result[1].replace("\"", "");
                        regisseur.put(Integer.parseInt(result[0]), new director(result[0], result[1]));
                        break;
                    case 4:
                        result = zeile.split("\",\"");
                        result[0] = result[0].replaceFirst("\"", "");
                        result[1] = result[1].replace("\"", "");
                        actor a = schauspieler.get(Integer.parseInt(result[0]));
                        movie m = filme.get(Integer.parseInt(result[1]));
                        a.movies.add(m);
                        m.actors.add(a);
                        break;
                    case 5:
                        result = zeile.split("\",\"");
                        result[0] = result[0].replaceFirst("\"", "");
                        result[1] = result[1].replace("\"", "");
                        director d = regisseur.get(Integer.parseInt(result[0]));
                        movie n = filme.get(Integer.parseInt(result[1]));
                        d.movies.add(n);
                        n.directors.add(d);
                        break;
                    default:
                        System.out.println("Fehler beim Einlesen");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) try {
                in.close();
            } catch (IOException e) {
            }
        }

    }

    //In dieser Methode wird der Input auf Korrektheit überprüft, der Suchbegriff (also z.B. "--filmsuche=") herausgefiltert und anschließend die entsprechende Methode aufgerufen
    static String checkInput(String eingabe) {
        if (eingabe.startsWith("--filmsuche=")){
            eingabe = eingabe.replace("--filmsuche=","");
            printFilmsuche(eingabe);
        }
        else if (eingabe.startsWith("--schauspielersuche=")){
            eingabe = eingabe.replace("--schauspielersuche=","");
            printSchauspielersuche(eingabe);
        }
        else if (eingabe.startsWith("--filmnetzwerk=")){
            eingabe = eingabe.replace("--filmnetzwerk=","");
            printFilmnetzwerk(eingabe);
        }
        else if (eingabe.startsWith("--schauspielernetzwerk=")){
            eingabe = eingabe.replace("--schauspielernetzwerk=","");
            printSchauspielernetzwerk(eingabe);
        }
        else {
            System.out.println("Fehler: Ungültige Eingabe!");
            eingabe = "fail" ;
        }
        return eingabe;

    }


        //Methode zur Ausgabe der Details eines Films
        private static void printFilmsuche(String eingabe) {
            if(eingabe.contains("\"")){
                eingabe = eingabe.replace("\"","");
            }
            int h = 0;
            for(movie film: filme.values()) {
                    if (film.title.contains(eingabe) || film.movieID.equals(eingabe)) {
                        System.out.println("--------------------------------");
                        System.out.println("Titel: " + film.title);
                        System.out.println("ID: " + film.movieID);
                        System.out.println("Beschreibung: " + film.description);
                        System.out.println("Genre: " + film.genre);
                        System.out.println("Release: " +film.releaseDate);
                        System.out.println("Imdb Votes: " + film.imdbVotes);
                        System.out.println("Imdb Rating: " + film.imdbRating);
                        h++;
                    }
            }
            if(h == 0){
                System.out.println("Kein Ergebnis gefunden!");
            }
        }

        //Methode zur Ausgabe der Details eines Schauspielers
        private static void printSchauspielersuche(String eingabe) {
            if(eingabe.contains("\"")){
                eingabe = eingabe.replace("\"","");
            }
            int h = 0;
            for(actor spieler: schauspieler.values()) {
                if (spieler.name.contains(eingabe) || spieler.id.equals(eingabe)) {
                    System.out.println("--------------------------------");
                    System.out.println("Name: " + spieler.name);
                    System.out.println("ID: " + spieler.id);
                    h++;
                }
            }
            if(h==0) {
                System.out.println("Kein Ergebnis gefunden!");
            }
       }

        //Methode zur Suche der weiteren Filme im Filmnetzwerk
        static Set<String> searchFilmeVonFilmnetzwerk(movie film) {
            Set<String> movies = new HashSet<>();
            for(actor spieler: film.actors) {
                for (movie movie : spieler.movies) {
                    movies.add("'" + movie.title + "'");
                }
            }
            movies.remove("'" + film.title + "'");
            return movies;
        }

        //Methode zur Ausgabe des Filmnetzwerkes
        private static void printFilmnetzwerk(String eingabe) {
            System.out.println("Suche: " + eingabe);
            for(movie film: filme.values()) {
                if(film.title.contains(eingabe) ||  film.movieID.equals(eingabe)) {
                    System.out.println("--------------------------------");
                    System.out.println(film.title);
                    System.out.println("Schauspieler: " + film.actors.toString().replace("[","").replace("]",""));
                    System.out.print("Filme: ");
                    String separator = "";
                    for (String movie : searchFilmeVonFilmnetzwerk(film)) {
                        System.out.print(separator + movie);
                        separator = ", ";
                    }
                }
            }
        }

        //Methode zur Suche der weiteren Schauspieler im Schauspielernetzwerk
        static Set<String> searchSpielerVonSchauspielernetzwerk(actor spieler) {
            Set<String> actors = new HashSet<>();
            for(movie film: spieler.movies) {
                for(actor actor: film.actors) {
                    actors.add(actor.name);
                }
            }
            actors.remove(spieler.name);
            return actors;
       }

        //Methode zur Ausgabe des Schauspielernetzwerkes
        private static void printSchauspielernetzwerk(String eingabe) {
            System.out.println("Suche: " + eingabe);
            for(actor spieler: schauspieler.values()) {
                if(spieler.name.contains(eingabe) || spieler.id.equals(eingabe)) {
                    System.out.println("--------------------------------");
                    System.out.println(spieler.name);
                    System.out.println("Filme: " + spieler.movies.toString().replace("[","").replace("]",""));
                    System.out.print("Schauspieler: ");
                    String separator = "";
                    for(String actor : searchSpielerVonSchauspielernetzwerk(spieler)) {
                        System.out.print(separator + actor);
                        separator = ", ";
                    }
                }
            }
        }


        //Vor dem Starten darauf achten, dass korrekter Dateipfad ausgewählt ist
        public static void main (String[] args) {
            String dateiName = "C:\\Users\\buehring\\Desktop\\DHBW\\2. Semester\\Java\\Filmprojekt\\src\\com\\company\\movieproject2019.txt";  //Hier muss der Dateipfad der Datei eingegeben werden
            load(dateiName);
            String eingabe = args[0];
            checkInput(eingabe);
        }
}
