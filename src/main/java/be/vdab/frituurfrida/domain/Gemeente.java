package be.vdab.frituurfrida.domain;

public class Gemeente {
    private final String naam;
    private final int postcode;

    public Gemeente(String naam, int postcode) {
        this.naam = naam;
        this.postcode = postcode;
    }

    public String getNaam() {
        return naam;
    }

    public int getPostcode() {
        return postcode;
    }
}
