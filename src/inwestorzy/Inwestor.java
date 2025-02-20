package inwestorzy;

import giełda.Giełda;
import zlecenia.Zlecenie;

import java.util.ArrayList;

public abstract class Inwestor {
    protected int gotówka;
    protected ArrayList<String> tablica_nazw_akcji;
    protected ArrayList<Integer> tablica_liczby_akcji;
    protected Giełda giełda;

    public Inwestor(Giełda giełda){
        this.giełda = giełda;
        tablica_nazw_akcji = new ArrayList<>();
        tablica_liczby_akcji = new ArrayList<>();
    }

    public void ustaw_gotówka(int gotówka) {
        this.gotówka = gotówka;
    }

    public void dodaj_nową_akcję(String nazwa_akcji, int liczba_akcji) {
        tablica_nazw_akcji.add(nazwa_akcji);
        tablica_liczby_akcji.add(liczba_akcji);
    }

    public abstract Zlecenie stwórzZlecenie(int numer_zlecenia);

    public void dodaj_gotówke(int gotówka){
        this.gotówka += gotówka;
    }

    public void zabierz_gotówke(int gotówka){
        this.gotówka -= gotówka;
    }

    public void dodaj_akcje(String nazwa_akcji, int liczba_akcji){
        int index = tablica_nazw_akcji.indexOf(nazwa_akcji);
        tablica_liczby_akcji.set(index, tablica_liczby_akcji.get(index) + liczba_akcji);
    }

    public void zabierz_akcje(String nazwa_akcji, int liczba_akcji){
        int index = tablica_nazw_akcji.indexOf(nazwa_akcji);
        tablica_liczby_akcji.set(index, tablica_liczby_akcji.get(index) - liczba_akcji);
    }

    public int getNumer_tury() {
        return giełda.getNumer_tury();
    }

    public int getCeny_akcji(int numer_akcji) {
        return giełda.getCeny_akcji(numer_akcji);
    }

    public int getGotówka() {
        return gotówka;
    }

    public String getNazwaAkcji(int numer_akcji) {
        return tablica_nazw_akcji.get(numer_akcji);
    }

    public int getLiczbaAkcji(int numer_akcji) {
        return tablica_liczby_akcji.get(numer_akcji);
    }

    public int getLiczbaAkcji(String nazwa_akcji){
        return tablica_liczby_akcji.get(tablica_nazw_akcji.indexOf(nazwa_akcji));
    }

}
