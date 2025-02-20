package giełda;

import akcje.Akcja;
import inwestorzy.Inwestor;
import inwestorzy.InwestorRANDOM;
import inwestorzy.InwestorSMA;
import tablice_zleceń.TablicaZleceńKupna;
import tablice_zleceń.TablicaZleceńSprzedaży;
import wskaźniki.SMA;
import wskaźniki.Wskaźnik;
import zlecenia.Zlecenie;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Giełda {

    private final ArrayList<Inwestor> inwestorzy;
    private final ArrayList<Akcja> akcje;
    private int numer_tury;
    private final ArrayList<TablicaZleceńKupna> tabliceZleceńKupna;
    private final ArrayList<TablicaZleceńSprzedaży> tabliceZleceńSprzedaży;
    private final ArrayList<Wskaźnik> wskaźniki;
    private int licznik_zleceń = 0;
    private int gotówka_początkowa;

    public Giełda(String[] args) throws FileNotFoundException {
        inwestorzy = new ArrayList<Inwestor>();
        akcje = new ArrayList<Akcja>();
        numer_tury = 0;
        tabliceZleceńKupna = new ArrayList<TablicaZleceńKupna>();
        tabliceZleceńSprzedaży = new ArrayList<TablicaZleceńSprzedaży>();
        wskaźniki = new ArrayList<Wskaźnik>();

        String ścieżka = args[0];
        Scanner plik = new Scanner(new File(ścieżka));

        int numer_linii = 0;

        while(plik.hasNext()){
            String linia = plik.nextLine();

            if(!linia.matches("#.*")){
                if(numer_linii == 0){
                    for(int i = 0; i < linia.length(); i++){
                        if(linia.charAt(i) == 'R') {
                            inwestorzy.add(new InwestorRANDOM(this));
                        }else if(linia.charAt(i) == 'S'){
                            inwestorzy.add(new InwestorSMA(this));
                        }else{
                            assert linia.charAt(i) == ' ': "Niepoprawne dane wejściowe";
                        }
                    }
                } else if(numer_linii == 1){

                    assert linia != "": "Niepoprawne dane wejściowe";

                    String[] fragmenty = linia.split(":|\s");

                    int licznik = 0;

                    assert fragmenty.length % 2 == 0: "Niepoprawne dane wejściowe";

                    while(licznik < fragmenty.length){
                        assert fragmenty[licznik].length() <= 5: "Nazwa akcji jest za długa";
                        assert fragmenty[licznik].matches("[A-Z]+"): "Nazwa akcji zawiera niedozwolone znaki";
                        akcje.add(new Akcja(fragmenty[licznik], Integer.parseInt(fragmenty[licznik+1])));
                        licznik+=2;
                    }
                } else if(numer_linii == 2){

                    String[] fragmenty = linia.split(":|\s");

                    assert fragmenty.length == akcje.size()* 2 + 1: "Niepoprawne dane wejściowe";
                    gotówka_początkowa = Integer.parseInt(fragmenty[0]);

                    for (Inwestor inwestor : inwestorzy) {
                        inwestor.ustaw_gotówka(Integer.parseInt(fragmenty[0]));
                    }

                    int licznik = 1;

                    while(licznik < fragmenty.length){
                        boolean czy_istnieje_akcja = false;
                        for(int i = 0; i < akcje.size(); i++){
                            if(akcje.get(i).getNazwa().equals(fragmenty[licznik])){
                                czy_istnieje_akcja = true;
                                break;
                            }
                        }
                        assert czy_istnieje_akcja: "Niepoprawne dane wejściowe";
                        for(int i = 0; i < inwestorzy.size(); i++){
                            inwestorzy.get(i).dodaj_nową_akcję(fragmenty[licznik], Integer.parseInt(fragmenty[licznik+1]));
                        }
                        licznik += 2;
                    }
                }
                numer_linii++;
            }
        }
        assert numer_linii == 3: "Niepoprawne dane wejściowe";

        for(Akcja akcja: akcje){
            tabliceZleceńKupna.add(new TablicaZleceńKupna());
            tabliceZleceńSprzedaży.add(new TablicaZleceńSprzedaży());
        }

        wskaźniki.add(new SMA(this, 10));
        wskaźniki.add(new SMA(this, 5));

        for(Wskaźnik wskaźnik: wskaźniki){
            for(Akcja akcja: akcje){
                wskaźnik.dodajAkcje(akcja);
            }
        }

        for(Inwestor inwestor: inwestorzy){
            if(inwestor.getClass() == InwestorSMA.class){
                ((InwestorSMA) inwestor).dodaj_akcje();
            }
        }
    }

    private void wykonaj_zlecenia(TablicaZleceńKupna tablicaZleceńKupna, TablicaZleceńSprzedaży tablicaZleceńSprzedaży) {
        int index_kupna = 0;
        int index_sprzedaży = 0;

        while(index_kupna < tablicaZleceńKupna.getLiczbaZleceń() &&
                index_sprzedaży < tablicaZleceńSprzedaży.getLiczbaZleceń() &&
                tablicaZleceńKupna.getZlecenie(index_kupna).getLimit_ceny() >=
                        tablicaZleceńSprzedaży.getZlecenie(index_sprzedaży).getLimit_ceny()) {

            Zlecenie zlecenie_kupna = tablicaZleceńKupna.getZlecenie(index_kupna);
            Zlecenie zlecenie_sprzedaży = tablicaZleceńSprzedaży.getZlecenie(index_sprzedaży);
            Inwestor sprzedający = zlecenie_sprzedaży.getInwestor();
            Inwestor kupujący = zlecenie_kupna.getInwestor();

            int cena;

            if(zlecenie_kupna.getNumer_zlecenia() < zlecenie_sprzedaży.getNumer_zlecenia()) {
                cena = zlecenie_kupna.getLimit_ceny();
            }else {
                cena = zlecenie_sprzedaży.getLimit_ceny();
            }

            int liczba_akcji = Math.min(zlecenie_kupna.getLiczba_akcji(), zlecenie_sprzedaży.getLiczba_akcji());

            int koszt = cena * liczba_akcji;

            if(koszt > kupujący.getGotówka()){
                tablicaZleceńKupna.usuńZlecenie(zlecenie_kupna);
                continue;
            }
            if(liczba_akcji > sprzedający.getLiczbaAkcji(zlecenie_sprzedaży.getNazwa_akcji())){
                tablicaZleceńSprzedaży.usuńZlecenie(zlecenie_sprzedaży);
                continue;
            }

            kupujący.zabierz_gotówke(koszt);
            sprzedający.dodaj_gotówke(koszt);
            String nazwa_akcji = zlecenie_kupna.getNazwa_akcji();
            kupujący.dodaj_akcje(nazwa_akcji, liczba_akcji);
            sprzedający.zabierz_akcje(nazwa_akcji, liczba_akcji);


            if(zlecenie_sprzedaży.getLiczba_akcji() == liczba_akcji) {
                tablicaZleceńSprzedaży.usuńZlecenie(zlecenie_sprzedaży);
            }
            if(zlecenie_kupna.getLiczba_akcji() == liczba_akcji) {
                tablicaZleceńKupna.usuńZlecenie(zlecenie_kupna);
            }

            tablicaZleceńKupna.zmniejsz_liczbe_akcji_o(zlecenie_kupna, liczba_akcji);
            tablicaZleceńSprzedaży.zmniejsz_liczbe_akcji_o(zlecenie_sprzedaży, liczba_akcji);

            for(Akcja akcja:akcje){
                if(akcja.getNazwa().equals(nazwa_akcji)){
                    akcja.setCena(cena);
                    break;
                }
            }

        }
    }

    private void wyczyść_zlecenia(){
        for(TablicaZleceńKupna tablicaZleceńKupna: tabliceZleceńKupna){
            tablicaZleceńKupna.wyczyść_zlecenia(numer_tury);
        }
        for(TablicaZleceńSprzedaży tablicaZleceńSprzedaży: tabliceZleceńSprzedaży){
            tablicaZleceńSprzedaży.wyczyść_zlecenia(numer_tury);
        }
    }

    private void stwórz_zlecenia(){
        for (Inwestor inwestor : inwestorzy) {
            Zlecenie zlecenie = inwestor.stwórzZlecenie(licznik_zleceń);
            licznik_zleceń++;

            if (zlecenie == null) {
                continue;
            }

            String nazwa_akcji = zlecenie.getNazwa_akcji();
            int index = 0;
            for (Akcja akcja : akcje) {
                if (akcja.getNazwa().equals(nazwa_akcji)) {
                    break;
                }
                index++;
            }
            if (zlecenie.getTyp_zlecenia().equals("kupno")) {
                tabliceZleceńKupna.get(index).dodajZlecenie(zlecenie);
            } else if (zlecenie.getTyp_zlecenia().equals("sprzedaż")) {
                tabliceZleceńSprzedaży.get(index).dodajZlecenie(zlecenie);
            }
        }
    }

    private void zaaktualizuj_wskaźniki(){
        for(Wskaźnik wskaźnik: wskaźniki){
            wskaźnik.zaaktualizujDane();
        }
    }

    private void wykonaj_zlecenia(){
        for (Akcja akcja : akcje) {
                wykonaj_zlecenia(tabliceZleceńKupna.get(akcje.indexOf(akcja)), tabliceZleceńSprzedaży.get(akcje.indexOf(akcja)));
        }
    }

    public void wykonaj_turę(){

        zaaktualizuj_wskaźniki();

        stwórz_zlecenia();

        wykonaj_zlecenia();

        wyczyść_zlecenia();

        numer_tury++;
    }

    public void wypisz_wyniki(){
        for(Inwestor inwestor: inwestorzy){
            System.out.print(inwestor.getGotówka()+ " ");
            for(Akcja akcja: akcje){
                System.out.print(akcja.getNazwa() +":" +  inwestor.getLiczbaAkcji(akcja.getNazwa()) + " ");
            }
            System.out.println();
        }
    }

    public int getCeny_akcji(int numer_akcji) {
        return akcje.get(numer_akcji).getCena();
    }

    public int getNumer_tury() {
        return numer_tury;
    }

    public int getLiczbaAkcji() {
        return akcje.size();
    }

    public double getWskaźnik(String nazwa, int numer_akcji) {
        for (Wskaźnik wskaźnik : wskaźniki) {
            if (wskaźnik.getNazwa().equals(nazwa)) {
                return wskaźnik.getWartość(numer_akcji);
            }
        }
        return 0;
    }

    public String getNazwaAkcji(int numer_akcji) {
        return akcje.get(numer_akcji).getNazwa();
    }

    public ArrayList <Inwestor> getInwestorzy() {
        return inwestorzy;
    }

    public int getGotówka_początkowa() {
        return gotówka_początkowa;
    }
}
