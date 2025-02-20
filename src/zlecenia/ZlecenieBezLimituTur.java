package zlecenia;

import inwestorzy.Inwestor;
import giełda.Giełda;

public class ZlecenieBezLimituTur extends Zlecenie{
    public ZlecenieBezLimituTur(String typ_zlecenia, String identyfikator_akcji, int liczba_akcji, int limit_ceny,
                                Giełda giełda, Inwestor inwestor, int numer_zlecenia){
        super(typ_zlecenia, identyfikator_akcji, liczba_akcji, limit_ceny, giełda, inwestor, numer_zlecenia);
    }
}
