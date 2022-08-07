package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.domain.Saus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

@Controller
@RequestMapping("sauzen")
class SausController {
    private final Saus[] alleSauzen = {
            new Saus(1L, "Cocktail", new String[]{"Tomaat", "Suiker", "Water"}),
            new Saus(2L, "Mayonaisse", new String[]{"Azijn", "Ei", "Peper", "Zout"}),
            new Saus(3L, "Mosterd", new String[]{"Mosterdzaden", "Azijn", "Water", "Zout"}),
            new Saus(4L, "Tartare", new String[]{"Ei", "Peper", "Zout"}),
            new Saus(5L, "Vinaigrette", new String[]{"Azijn", "Water", "Ei"})};
    @GetMapping
    public ModelAndView findAll() {
        return new ModelAndView("sauzen", "alleSauzen", alleSauzen);
    }

    private Optional<Saus> findByIdHelper(long id) {
        return Arrays.stream(alleSauzen).filter(saus->saus.getId() == id).findFirst();
    }
    @GetMapping("{id}")
    public ModelAndView findById(@PathVariable long id) {
        var modelAndView = new ModelAndView("saus");
        findByIdHelper(id).ifPresent(saus -> modelAndView.addObject("saus", saus));
        return modelAndView;
    }

    private final char[] alfabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    @GetMapping("alfabet")
    public ModelAndView alfabet() {
        return new ModelAndView("sausAlfabet", "alfabet", alfabet);
    }
    private Stream<Saus> findByBeginNaamHelper(char letter) {
        return Arrays.stream(alleSauzen).filter(saus -> saus.getNaam().charAt(0) == letter);
    }
    @GetMapping("alfabet/{letter}")
    public ModelAndView findByBeginNaam(@PathVariable char letter) {
        return new ModelAndView("sausAlfabet", "alfabet", alfabet)
                .addObject("sauzen", findByBeginNaamHelper(letter).iterator());
    }
}
