package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.domain.Adres;
import be.vdab.frituurfrida.domain.Gemeente;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalTime;

// enkele imports
@Controller
class IndexController {

    @GetMapping("/")
    public ModelAndView index() {
        var dagVanDeWeek = LocalDate.now().getDayOfWeek().toString().equals("MONDAY") ? "Wij zijn vandaag gesloten" :"Wij zijn vandaag open";
        var modelAndView = new ModelAndView("index", "moment", dagVanDeWeek);
        modelAndView.addObject("adres", new Adres("Hogeveld","12",
                new Gemeente("Opdorp", 9255)));
        return modelAndView;
    }
}
