package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.domain.Saus;
import be.vdab.frituurfrida.forms.SausRadenForm;
import be.vdab.frituurfrida.services.SausService;
import be.vdab.frituurfrida.sessions.SausRaden;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

@Controller
@RequestMapping("sauzen")
class SausController {
    private final SausRaden sausRaden;
    private final SausService sausService;
    private final Saus[] alleSauzen = {
            new Saus(1L, "Cocktail", new String[]{"Tomaat", "Suiker", "Water"}),
            new Saus(2L, "Mayonaisse", new String[]{"Azijn", "Ei", "Peper", "Zout"}),
            new Saus(3L, "Mosterd", new String[]{"Mosterdzaden", "Azijn", "Water", "Zout"}),
            new Saus(4L, "Tartare", new String[]{"Ei", "Peper", "Zout"}),
            new Saus(5L, "Vinaigrette", new String[]{"Azijn", "Water", "Ei"})};

    SausController(SausService sausService, SausRaden sausRaden) {
        this.sausService = sausService;
        this.sausRaden = sausRaden;
    }

    private String randomSaus() {
        var sauzen = sausService.findAll().toList();
        var random = new Random();
        var randomIndex =random.nextInt(sauzen.size());
        return sauzen.get(randomIndex).getNaam();
    }

    @GetMapping("raden")
    public ModelAndView radenForm() {
        sausRaden.reset(randomSaus());
        return new ModelAndView("sausRaden").addObject(sausRaden)
                .addObject(new SausRadenForm(null));
    }
    @PostMapping("raden/nieuwspel")
    public String radenNieuwSpel() {
        return "redirect:/sauzen/raden";
    }
    @PostMapping("raden")
    public ModelAndView raden(@Valid SausRadenForm form, Errors errors) {
        if (errors.hasErrors()) {
            return new ModelAndView("sausRaden").addObject(sausRaden);
        }
        sausRaden.gok(form.letter());
        return new ModelAndView("redirect:/sauzen/raden/volgendegok");
    }
    @GetMapping("raden/volgendegok")
    public ModelAndView volgendeGok() {
        return new ModelAndView("sausRaden").addObject(sausRaden)
                .addObject(new SausRadenForm(null));
    }
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
