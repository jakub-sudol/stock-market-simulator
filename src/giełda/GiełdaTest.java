package giełda;

import inwestorzy.Inwestor;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class GiełdaTest {

    Giełda giełda;
    Giełda giełda2;

    GiełdaTest() throws FileNotFoundException {
        this.giełda = new Giełda(new String[]{"input.txt","100"});
        this.giełda2 = new Giełda(new String[]{"input2.txt","1000"});
    }

    @Test
    void getCeny_akcji() {
        assertEquals(145, giełda.getCeny_akcji(0));
        assertEquals(10, giełda2.getCeny_akcji(1));
    }

    @Test
    void getLiczbaAkcji() {
        assertEquals(3, giełda.getLiczbaAkcji());
        assertEquals(3, giełda2.getLiczbaAkcji());
    }

    @Test
    void getNazwaAkcji() {
        assertEquals("MSFT", giełda.getNazwaAkcji(1));
        assertEquals("LEGIA", giełda2.getNazwaAkcji(0));
    }

    @Test
    void getNumer_tury() {
        for(int k =0; k < 2; k++) {
            for (int i = 0; i < 41; i++) {
                giełda.wykonaj_turę();
                int suma = 0;
                for (Inwestor inwestor : giełda.getInwestorzy()) {
                    suma += inwestor.getGotówka();
                }
                assertEquals(giełda.getGotówka_początkowa() * giełda.getInwestorzy().size(), suma);
                for (int j = 0; j < giełda.getLiczbaAkcji(); j++) {
                    int suma_akcji = 0;
                    for (Inwestor inwestor : giełda.getInwestorzy()) {
                        suma_akcji += inwestor.getLiczbaAkcji(j);
                    }
                    assertEquals(0, suma_akcji % giełda.getInwestorzy().size());
                }
            }
            giełda = giełda2;
        }
        assertEquals(41, giełda.getNumer_tury());
    }
}