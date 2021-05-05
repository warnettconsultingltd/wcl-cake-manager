package com.waracle.cakemgr.controller;

import com.waracle.cakemgr.service.CakeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
