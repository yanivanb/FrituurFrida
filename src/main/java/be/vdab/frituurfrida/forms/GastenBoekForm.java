package be.vdab.frituurfrida.forms;

import be.vdab.frituurfrida.domain.Gast;

import java.time.LocalDate;

public class GastenBoekForm extends Gast {
    public GastenBoekForm(String naam, String bericht) {
        super(0, LocalDate.now(),naam, bericht);
    }
}
