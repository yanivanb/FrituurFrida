package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.domain.Saus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
class SausController {
    private final Saus[] alleSauzen = {
            new Saus(3, "cocktail", new String[] {"mayonaise", "ketchup", "cognac"}),
            new Saus(6, "mayonaise", new String[] {"ei", "mosterd"}),
            new Saus(7, "mosterd", new String[] {"mosterd", "azijn", "witte wijn"}),
            new Saus(12, "tartare", new String[] {"mayonaise", "augurk", "tabasco"}),
            new Saus(44, "vinaigrette", new String[] {"olijfolie","mosterd","azijn"})};
    @GetMapping("/sauzen")
    public ModelAndView findAll() {
        return new ModelAndView("sauzen", "alleSauzen", alleSauzen);
    }
}
