package tablice_zleceń;

import akcje.Akcja;
import zlecenia.Zlecenie;
import zlecenia.ZlecenieNatychmiastowe;
import zlecenia.ZlecenieZLimitemTur;

import java.util.ArrayList;

public abstract class TablicaZleceń {
    protected ArrayList<Zlecenie> zlecenia;

    public TablicaZleceń() {
        zlecenia = new ArrayList<Zlecenie>();
    }

    public abstract void dodajZlecenie(Zlecenie zlecenie);

    public abstract void usuńZlecenie(Zlecenie zlecenie);

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        for(Zlecenie zlecenie: zlecenia){
            result.append(zlecenie.getTyp_zlecenia()).append(" ").append(zlecenie.getNazwa_akcji()).append(" ")
                    .append(zlecenie.getLiczba_akcji()).append(" ").append(zlecenie.getLimit_ceny()).append("\n");
        }
        return result.toString();
    }

    public void zmniejsz_liczbe_akcji_o(Zlecenie zlecenie, int liczba_akcji){
        zlecenie.zmniejsz_liczbe_akcji_o(liczba_akcji);
    }

    public void wyczyść_zlecenia(int numer_tury){
        for(int i = 0; i < zlecenia.size(); i++){
            if(zlecenia.get(i).getClass() == ZlecenieNatychmiastowe.class){
                zlecenia.remove(i);
                i--;
            }
            else if(zlecenia.get(i).getClass() == ZlecenieZLimitemTur.class){
                if(((ZlecenieZLimitemTur) zlecenia.get(i)).getLimit_tur() <= numer_tury){
                    zlecenia.remove(i);
                    i--;
                }
            }
        }
    }

    public Zlecenie getZlecenie(int index){
        return zlecenia.get(index);
    }

    public int getLiczbaZleceń(){
        return zlecenia.size();
    }
}
