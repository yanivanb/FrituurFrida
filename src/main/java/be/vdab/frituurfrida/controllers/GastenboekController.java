package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.forms.GastenBoekForm;
import be.vdab.frituurfrida.services.GastService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("gastenboek")
public class GastenboekController {
    private final GastService gastenBoekService;

    public GastenboekController(GastService gastenBoekService) {
        this.gastenBoekService = gastenBoekService;
    }

    @GetMapping
    public ModelAndView findAll() {
        return new ModelAndView("gastenboek",
                "gastenboekEntries", gastenBoekService.findAll());
    }
    @GetMapping("toevoegen/form")
    public ModelAndView toevoegForm() {
        return new ModelAndView("gastenboek",
                "gastenboekEntries", gastenBoekService.findAll())
                .addObject(new GastenBoekForm("", ""));
    }
    @PostMapping("toevoegen")
    public ModelAndView toevoegen(@Valid GastenBoekForm form, Errors errors){
        if (errors.hasErrors()) {
            return new ModelAndView("gastenboek",
                    "gastenboekEntries", gastenBoekService.findAll());
        }
        gastenBoekService.create(form);
        return new ModelAndView("redirect:/gastenboek");
    }
}
