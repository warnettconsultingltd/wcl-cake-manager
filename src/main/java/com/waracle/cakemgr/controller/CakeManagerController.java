package com.waracle.cakemgr.controller;

import com.waracle.cakemgr.entities.Cake;
import com.waracle.cakemgr.entities.CakeUIForm;
import com.waracle.cakemgr.service.CakeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.URI;

@Controller
public class CakeManagerController {

    private final CakeDataService cakeDataService;

    @Autowired
    public CakeManagerController(CakeDataService cakeDataService) {
        this.cakeDataService = cakeDataService;
    }

    @GetMapping("/")
    public String displayCakeMainPage(Model model) {
        model.addAttribute("cakes", cakeDataService.getAllCakes());

        return "index";
    }

    @GetMapping("/cakes")
    public String displayCakeMainPageAsJson(Model model) {
        model.addAttribute("cakes_json", cakeDataService.getAllCakesAsJson());

        return "cakes";
    }

    @GetMapping("/add")
    public String displayAddCakeForm(Model model) {
        var cakeForm = new CakeUIForm();
        model.addAttribute("cake", cakeForm);
        return "add_cake_form";
    }

    @PostMapping("/add")
    public String addUser(CakeUIForm newCake, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("cake", newCake);
            return "add_cake_form";
        }

        if (!cakeDataService.existsForTitle(newCake.getTitle())) {
            cakeDataService.insertCake(new Cake(null,
                    newCake.getTitle(),
                    newCake.getDescription(),
                    URI.create(newCake.getImageURL())));
        } else {
            model.addAttribute("titleErrorMessage", "A Cake with that Title already exists");
            model.addAttribute("cake", newCake);
            return "add_cake_form";
        }

        return "redirect:/";
    }
}
