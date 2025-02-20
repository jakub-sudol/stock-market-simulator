package inwestorzy;

import giełda.Giełda;
import zlecenia.Zlecenie;
import zlecenia.ZlecenieBezLimituTur;
import zlecenia.ZlecenieDoNTury;
import zlecenia.ZlecenieNatychmiastowe;

import java.util.ArrayList;

public class InwestorSMA extends Inwestor{
    private int okres = 10;
    private ArrayList <Double> aktualne_SMA10;
    private ArrayList <Double> poprzednie_SMA10;
    private ArrayList <Double> aktualne_SMA5;
    private ArrayList <Double> poprzednie_SMA5;

    public InwestorSMA(Giełda giełda) {
        super(giełda);
        aktualne_SMA10 = new ArrayList <Double> ();
        aktualne_SMA5 = new ArrayList <Double> ();
        poprzednie_SMA10 = new ArrayList <Double> ();
        poprzednie_SMA5 = new ArrayList <Double> ();

        for(int i = 0; i < giełda.getLiczbaAkcji(); i++){
            aktualne_SMA10.add((double) 0);
            aktualne_SMA5.add((double) 0);
            poprzednie_SMA10.add((double) 0);
            poprzednie_SMA5.add((double) 0);
        }
    }

    public void dodaj_akcje(){ // ustawia odpowiednie rozmiary tablic
        for(int i = 0; i < giełda.getLiczbaAkcji(); i++){
            aktualne_SMA10.add((double) 0);
            aktualne_SMA5.add((double) 0);
            poprzednie_SMA10.add((double) 0);
            poprzednie_SMA5.add((double) 0);
        }
    }

    private void zaaktualizujDane(){
        for(int numer_akcji = 0; numer_akcji < giełda.getLiczbaAkcji(); numer_akcji++){
            poprzednie_SMA5.set(numer_akcji, aktualne_SMA5.get(numer_akcji));
            poprzednie_SMA10.set(numer_akcji, aktualne_SMA10.get(numer_akcji));
            aktualne_SMA10.set(numer_akcji, giełda.getWskaźnik("SMA10", numer_akcji));
            aktualne_SMA5.set(numer_akcji, giełda.getWskaźnik("SMA5", numer_akcji));
        }
    }

    @Override
    public Zlecenie stwórzZlecenie(int numer_zlecenia) {

        zaaktualizujDane();

        if (getNumer_tury() >= okres) {
            for (int i = 0; i < giełda.getLiczbaAkcji(); i++) {
                if (aktualne_SMA5.get(i) > aktualne_SMA10.get(i) && poprzednie_SMA5.get(i) <= poprzednie_SMA10.get(i)) {

                    int rodzaj_kupna = (int) (Math.random() * 3);
                    int liczba_akcji = (int) (Math.random() * 10) + 1;

                    if (gotówka >= liczba_akcji * giełda.getCeny_akcji(i)) {

                        if (rodzaj_kupna == 0) {
                            return new ZlecenieNatychmiastowe("kupno", giełda.getNazwaAkcji(i),
                                    liczba_akcji, giełda.getCeny_akcji(i), giełda, this, numer_zlecenia);

                        } else if (rodzaj_kupna == 1) {
                            int liczba_tur = (int) (Math.random() * 100);
                            return new ZlecenieDoNTury("kupno", giełda.getNazwaAkcji(i), liczba_akcji,
                                    giełda.getCeny_akcji(i), liczba_tur, giełda, this, numer_zlecenia);

                        } else {
                            return new ZlecenieBezLimituTur("kupno", giełda.getNazwaAkcji(i),
                                    liczba_akcji, giełda.getCeny_akcji(i), giełda, this, numer_zlecenia);
                        }
                    }
                } else if (aktualne_SMA5.get(i) < aktualne_SMA10.get(i)
                        && poprzednie_SMA5.get(i) >= poprzednie_SMA10.get(i)) {

                    int rodzaj_sprzedaży = (int) (Math.random() * 3);
                    int liczba_akcji = (int) (Math.random() * 10);

                    if (tablica_liczby_akcji.get(i) >= liczba_akcji) {
                        if (rodzaj_sprzedaży == 0) {
                            return new ZlecenieNatychmiastowe("sprzedaż", giełda.getNazwaAkcji(i),
                                    liczba_akcji, giełda.getCeny_akcji(i), giełda, this, numer_zlecenia);

                        } else if (rodzaj_sprzedaży == 1) {
                            int liczba_tur = (int) (Math.random() * 10);
                            return new ZlecenieDoNTury("sprzedaż", giełda.getNazwaAkcji(i), liczba_akcji,
                                    giełda.getCeny_akcji(i), liczba_tur, giełda, this, numer_zlecenia);

                        } else {
                            return new ZlecenieBezLimituTur("sprzedaż", giełda.getNazwaAkcji(i),
                                    liczba_akcji, giełda.getCeny_akcji(i), giełda, this, numer_zlecenia);
                        }
                    }
                }
            }
        }
        return null;


    }
}
