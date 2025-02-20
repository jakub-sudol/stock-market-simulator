package inwestorzy;

import giełda.Giełda;
import zlecenia.*;

public class InwestorRANDOM extends Inwestor{

    public InwestorRANDOM(Giełda giełda) {
        super(giełda);
    }

    @Override
    public Zlecenie stwórzZlecenie(int numer_zlecenia){

        int typ_zlecenia = (int)(Math.random()*2); //kupno czy sprzedaż
        int identyfikator_akcji = (int)(Math.random()*tablica_liczby_akcji.size());
        int liczba_akcji = (int)(Math.random()*10) + 1;
        int limit_ceny = (int)(Math.random()*20) - 10 + getCeny_akcji(identyfikator_akcji);
        int rodzaj_zlecenia = (int)(Math.random()*3);

        if(rodzaj_zlecenia == 0){ //zlecenie natychmiastowe
            if(typ_zlecenia == 0 && getGotówka() >= liczba_akcji * getCeny_akcji(identyfikator_akcji)){
                return new ZlecenieNatychmiastowe("kupno", getNazwaAkcji(identyfikator_akcji),
                        liczba_akcji, limit_ceny, giełda, this, numer_zlecenia);
            } else if(typ_zlecenia == 1 && getLiczbaAkcji(identyfikator_akcji) >= liczba_akcji){
                return new ZlecenieNatychmiastowe("sprzedaż", getNazwaAkcji(identyfikator_akcji),
                        liczba_akcji, limit_ceny, giełda, this, numer_zlecenia);
            } else {
                return null;
            }

        } else if(rodzaj_zlecenia == 1){ // zlecenie do n tur
            int liczba_tur = (int)(Math.random()*100) + giełda.getNumer_tury() + 1;

            if(typ_zlecenia == 0 && getGotówka() >= liczba_akcji * getCeny_akcji(identyfikator_akcji)){
                return new ZlecenieDoNTury("kupno", getNazwaAkcji(identyfikator_akcji),
                        liczba_akcji, limit_ceny, liczba_tur, giełda, this, numer_zlecenia);
            } else if(typ_zlecenia == 1 && getLiczbaAkcji(identyfikator_akcji) >= liczba_akcji){
                return new ZlecenieDoNTury("sprzedaż", getNazwaAkcji(identyfikator_akcji),
                        liczba_akcji, limit_ceny, liczba_tur, giełda, this, numer_zlecenia);
            } else {
                return null;
            }

        } else { // zlecenie bez limitu tur
            if(typ_zlecenia == 0 && getGotówka() >= liczba_akcji * getCeny_akcji(identyfikator_akcji)){
                return new ZlecenieBezLimituTur("kupno", getNazwaAkcji(identyfikator_akcji),
                        liczba_akcji, limit_ceny, giełda, this, numer_zlecenia);
            } else if(typ_zlecenia == 1 && getLiczbaAkcji(identyfikator_akcji) >= liczba_akcji){
                return new ZlecenieBezLimituTur("sprzedaż", getNazwaAkcji(identyfikator_akcji),
                        liczba_akcji, limit_ceny, giełda, this, numer_zlecenia);
            } else {
                return null;
            }
        }
    }
}
