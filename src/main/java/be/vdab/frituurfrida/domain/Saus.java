package be.vdab.frituurfrida.domain;

public class Saus {
    private final long id;
    private final String naam;
    private final String ingredienten[];

    public Saus(long nummer, String naam, String[] ingredienten) {
        this.id = nummer;
        this.naam = naam;
        this.ingredienten = ingredienten;
    }

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public String[] getIngredienten() {
        return ingredienten;
    }
}
