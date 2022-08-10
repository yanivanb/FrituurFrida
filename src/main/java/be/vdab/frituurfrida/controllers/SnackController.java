package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.domain.Snack;
import be.vdab.frituurfrida.exceptions.SnackNietGevondenException;
import be.vdab.frituurfrida.forms.BeginNaamForm;
import be.vdab.frituurfrida.services.SnackService;
import be.vdab.frituurfrida.sessions.SausRaden;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("snacks")
public class SnackController {
    private final char[] alfabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private final SnackService snackService;

    public SnackController(SnackService snackService) {
        this.snackService = snackService;
    }

    @GetMapping("alfabet")
    public ModelAndView alfabet() {
        return new ModelAndView("snackAlfabet", "alfabet", alfabet);
    }
    @GetMapping("alfabet/{letter}")
    public ModelAndView findByBeginNaam(@PathVariable char letter) {
        return new ModelAndView("snackAlfabet", "alfabet", alfabet)
                .addObject("snacks",snackService.findByBeginNaam(String.valueOf(letter)));
    }
    @GetMapping("verkochteaantallenpersnack")
    ModelAndView findVerkochteAantallenPerSnack() {
        return new ModelAndView("verkochteaantallenpersnack",
                "verkochteAantallenPerSnack",snackService.findVerkochteAantallenPerSnack());
    }

    @GetMapping("beginnaam/form")
    public ModelAndView beginNaamForm() {
        return new ModelAndView("beginNaam").addObject(new BeginNaamForm(""));
    }
    @GetMapping("beginnaam")
    public ModelAndView findByBeginNaam(@Valid BeginNaamForm form) {
        return new ModelAndView("beginNaam")
                .addObject("snacks", snackService.findByBeginNaam(form.begin()));
    }

    @GetMapping("{id}/wijzigen/form")
    public ModelAndView wijzigenForm(@PathVariable long id) {
        var modelAndView = new ModelAndView("wijzigSnack");
        snackService.findById(id).ifPresent(snack -> modelAndView.addObject(snack));
        return modelAndView;
    }
    @PostMapping("wijzigen")
    public String wijzigen(@Valid Snack snack, Errors errors, RedirectAttributes redirect){
        if (errors.hasErrors()) {
            return "wijzigSnack";
        }
        try {
            snackService.update(snack);
            return "redirect:/";
        } catch (SnackNietGevondenException ex) {
            redirect.addAttribute("snackNietGevonden", snack.getId());
            return "redirect:/";
        }
    }
}
