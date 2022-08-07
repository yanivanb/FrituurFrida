package be.vdab.frituurfrida.domain;

public class Gemeente {
    private final String naam;
    private final int Postcode;

    public Gemeente(String naam, int postcode) {
        this.naam = naam;
        Postcode = postcode;
    }

    public String getNaam() {
        return naam;
    }

    public int getPostcode() {
        return Postcode;
    }
}
