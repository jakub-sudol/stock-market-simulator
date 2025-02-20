package tablice_zleceń;

import akcje.Akcja;
import zlecenia.Zlecenie;
import zlecenia.ZlecenieZLimitemTur;

public class TablicaZleceńKupna extends TablicaZleceń{
    public TablicaZleceńKupna() {
        super();
    }

    @Override
    public void dodajZlecenie(Zlecenie zlecenie){
        zlecenia.add(zlecenie);
        int index = zlecenia.size() - 1;
        while(index > 0 && zlecenia.get(index).getLimit_ceny() > zlecenia.get(index - 1).getLimit_ceny()) {
            Zlecenie temp = zlecenia.get(index);
            zlecenia.set(index, zlecenia.get(index - 1));
            zlecenia.set(index - 1, temp);
            index--;
        }
    }
    @Override
    public void usuńZlecenie(Zlecenie zlecenie) {
        zlecenia.remove(zlecenie);
    }

}
