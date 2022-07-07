package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.domain.Adres;
import be.vdab.frituurfrida.domain.Gemeente;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.DayOfWeek;
import java.time.LocalDate;

// enkele imports
@Controller
class IndexController {
    @GetMapping("/")
    public ModelAndView index() {
        var openGesloten = LocalDate.now().getDayOfWeek() == DayOfWeek.MONDAY ?
                "gesloten" : "open";
        var modelAndView = new ModelAndView("index", "openGesloten", openGesloten);
        modelAndView.addObject("adres",
                new Adres("Grote markt", "7", new Gemeente("Brussel", 1000)));
        return modelAndView;
    }
}
