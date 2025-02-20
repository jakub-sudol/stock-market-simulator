package zlecenia;

import inwestorzy.Inwestor;
import giełda.Giełda;

public abstract class ZlecenieZLimitemTur extends Zlecenie {

    private int limit_tur;
    public ZlecenieZLimitemTur(String typ_zlecenia, String identyfikator_akcji, int liczba_akcji, int limit_ceny,
                               int limit_tur, Giełda giełda, Inwestor inwestor, int numer_zlecenia){
        super(typ_zlecenia, identyfikator_akcji, liczba_akcji, limit_ceny, giełda, inwestor, numer_zlecenia);
        this.limit_tur = limit_tur;
    }

    public int getLimit_tur(){
        return limit_tur;
    }
}
