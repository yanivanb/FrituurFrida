package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.domain.Saus;
import be.vdab.frituurfrida.services.SausService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

@Controller
@RequestMapping("sauzen")
class SausController {
    private final Saus[] alleSauzen = {
            new Saus(3, "cocktail", new String[] {"mayonaise", "ketchup", "cognac"}),
            new Saus(6, "mayonaise", new String[] {"ei", "mosterd"}),
            new Saus(7, "mosterd", new String[] {"mosterd", "azijn", "witte wijn"}),
            new Saus(12, "tartare", new String[] {"mayonaise", "augurk", "tabasco"}),
            new Saus(44, "vinaigrette", new String[] {"olijfolie","mosterd","azijn"})};

    private final char[] alfabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private final SausService sausService;

    public SausController(SausService sausService) {
        this.sausService = sausService;
    }

    @GetMapping
    public ModelAndView findAll() {
        return new ModelAndView("sauzen",
                "alleSauzen",sausService.findAll().iterator());
    }
    private Optional<Saus> findByIdHelper(long id) {
        return Arrays.stream(alleSauzen).filter(saus->saus.getId()==id).findFirst();
    }
    @GetMapping("{id}")
    public ModelAndView findById(@PathVariable long id) {
        var modelAndView = new ModelAndView("saus");
        sausService.findById(id).ifPresent(saus -> modelAndView.addObject(saus));
        return modelAndView;
    }
    @GetMapping("alfabet")
    public ModelAndView alfabet() {
        return new ModelAndView("sausAlfabet", "alfabet", alfabet);
    }
    private Stream<Saus> findByBeginNaamHelper(char letter) {
        return Arrays.stream(alleSauzen)
                .filter(saus -> saus.getNaam().charAt(0) == letter);
    }
    @GetMapping("alfabet/{letter}")
    public ModelAndView findByBeginNaam(@PathVariable char letter) {
        return new ModelAndView("sausAlfabet", "alfabet", alfabet)
                .addObject("sauzen", sausService.findByBeginNaam(letter).iterator());
    }
}
