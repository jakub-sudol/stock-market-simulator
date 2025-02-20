package zlecenia;

import inwestorzy.Inwestor;
import giełda.Giełda;

public abstract class Zlecenie {

    private String typ_zlecenia;
    private String identyfikator_akcji;
    private int liczba_akcji;
    private int limit_ceny;

    protected Giełda giełda;

    private Inwestor inwestor;
    private int numer_zlecenia;

    public Zlecenie (String typ_zlecenia, String identyfikator_akcji, int liczba_akcji, int limit_ceny, Giełda giełda,
                     Inwestor inwestor, int numer_zlecenia){
        this.typ_zlecenia = typ_zlecenia;
        this.identyfikator_akcji = identyfikator_akcji;
        this.liczba_akcji = liczba_akcji;
        this.limit_ceny = limit_ceny;
        this.giełda = giełda;
        this.inwestor = inwestor;
        this.numer_zlecenia = numer_zlecenia;
    }

    public void zmniejsz_liczbe_akcji_o(int liczba_akcji){
        this.liczba_akcji -= liczba_akcji;
    }

    public Inwestor getInwestor(){
        return inwestor;
    }

    public int getLimit_ceny(){
        return limit_ceny;
    }

    public String getTyp_zlecenia(){
        return typ_zlecenia;
    }

    public String getNazwa_akcji(){
        return identyfikator_akcji;
    }

    public int getLiczba_akcji() {
        return liczba_akcji;
    }

    public int getNumer_zlecenia(){
        return numer_zlecenia;
    }
}
