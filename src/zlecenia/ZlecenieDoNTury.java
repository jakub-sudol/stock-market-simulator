package zlecenia;

import inwestorzy.Inwestor;
import giełda.Giełda;

public class ZlecenieDoNTury extends ZlecenieZLimitemTur{

        public ZlecenieDoNTury(String typ_zlecenia, String identyfikator_akcji, int liczba_akcji, int limit_ceny,
                            int limit_tur, Giełda giełda, Inwestor inwestor, int numer_zlecenia) {
            super(typ_zlecenia, identyfikator_akcji, liczba_akcji, limit_ceny, limit_tur, giełda, inwestor, numer_zlecenia);
        }
}
