package wskaźniki;

import akcje.Akcja;
import giełda.Giełda;

import java.util.ArrayList;

public abstract class Wskaźnik {
    protected ArrayList <Double> wartości;
    protected Giełda giełda;
    protected String nazwa;

    public Wskaźnik(Giełda giełda){
        wartości = new ArrayList <Double> ();
        this.giełda = giełda;
    }

    public abstract void zaaktualizujDane();

    public abstract void dodajAkcje(Akcja akcja);
    public String getNazwa(){
        return nazwa;
    }

    public Double getWartość(int numer_akcji){
        return wartości.get(numer_akcji);
    }
}
