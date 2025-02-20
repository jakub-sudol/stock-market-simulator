package akcje;

public class Akcja {
    private final String nazwa;
    private int cena;

    public Akcja(String nazwa, int cena) {
        this.nazwa = nazwa;
        this.cena = cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    public String getNazwa() {
        return nazwa;
    }

    public int getCena() {
        return cena;
    }
}
