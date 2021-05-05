package com.waracle.cakemgr.controller;

import com.waracle.cakemgr.entities.Cake;
import com.waracle.cakemgr.entities.CakeUIForm;
import com.waracle.cakemgr.service.CakeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String addNewCake(@ModelAttribute("cake") CakeUIForm cakeForm) {
        System.out.println(cakeForm);
        return "index";
    }
}
