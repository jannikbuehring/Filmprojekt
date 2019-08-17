package com.company;

import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class ReadFileTest {

    private String s;
    private String res;

    @Test
    public void checkInputRemovesSearchTerm1() {
        s = "--filmsuche=test";
        res = "test";
        s = readFile.checkInput(s);
        assertEquals("checkInputRemovesSearchTerm1 hast failed, because", res, s);
    }

    @Test
    public void checkInputRemovesSearchTerm2(){
        s = "--keinesuche=test";
        res = "fail";
        s = readFile.checkInput(s);
        assertEquals("checkInputRemovesSearchTerm2 hast failed, because", res, s);
    }

    @Test
    public void testFilmNetzwerk() {
        readFile.load("C:\\Users\\buehring\\Desktop\\DHBW\\2. Semester\\Java\\Filmprojekt\\src\\com\\company\\movieproject2019.txt");
        Set<String> relatedMovies = readFile.searchFilmeVonFilmnetzwerk(readFile.filme.get(2081));
        assertEquals(39, relatedMovies.size());
        assertTrue(relatedMovies.contains("'Red Planet'"));
    }

    @Test
    public void testSchauspielerNetzwerk() {
        readFile.load("C:\\Users\\buehring\\Desktop\\DHBW\\2. Semester\\Java\\Filmprojekt\\src\\com\\company\\movieproject2019.txt");
        Set<String> relatedActors = readFile.searchSpielerVonSchauspielernetzwerk(readFile.schauspieler.get(19786));
        assertEquals(27, relatedActors.size());
        assertTrue(relatedActors.contains("Jet Li"));
    }
}