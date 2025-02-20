package symulacja;

import giełda.Giełda;

import java.io.FileNotFoundException;

public class GPWSimulation {
    public static void main(String[] args) throws FileNotFoundException {

        int liczba_tur = Integer.parseInt(args[1]) + 1;
        Giełda giełda = new Giełda(args);

        for(int i = 0; i < liczba_tur; i++){
            giełda.wykonaj_turę();
        }

        giełda.wypisz_wyniki();
    }
}