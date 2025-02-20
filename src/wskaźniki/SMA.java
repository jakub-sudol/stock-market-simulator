package wskaźniki;

import akcje.Akcja;
import giełda.Giełda;

import java.util.ArrayList;

public class SMA extends Wskaźnik {

    private int okres;
    private ArrayList<ArrayList<Integer>> ceny; // pierwszy index to numer akcji, drugi to dzień
    public SMA(Giełda giełda, int okres) {
        super(giełda);
        this.okres = okres;
        ceny = new ArrayList<>();
        nazwa = "SMA" + okres;
    }

    @Override
    public void zaaktualizujDane(){
        for(int i = 0; i < ceny.size(); i++){
            int dzisiaj = giełda.getCeny_akcji(i);
            wartości.set(i, (wartości.get(i) * okres - ceny.get(i).get(0) + dzisiaj) / okres);
            ceny.get(i).removeFirst();
            ceny.get(i).add(dzisiaj);
        }
    }

    @Override
    public void dodajAkcje(Akcja akcja) {
        wartości.add((double) 0);
        ArrayList<Integer> ceny_akcji = new ArrayList<>();
        for(int i = 0; i < okres; i++){
            ceny_akcji.add(0);
        }
        ceny.add(ceny_akcji);
    }
}
