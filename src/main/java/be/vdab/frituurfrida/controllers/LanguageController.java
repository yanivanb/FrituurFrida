package be.vdab.frituurfrida.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;

@Controller
@RequestMapping("taal")
class LanguageController {
    private static final String[] Talen = {"nl"};

    @GetMapping
    public ModelAndView taal(@RequestHeader("Accept-Language") String acceptLanguage) {
        var modelAndView = new ModelAndView("taal");
        Arrays.stream(Talen)
                .filter(taal -> acceptLanguage.contains(taal))
                .findFirst()
                .ifPresent(gevondenTaal -> modelAndView.addObject("taal", gevondenTaal));
        return modelAndView;
    }
}
