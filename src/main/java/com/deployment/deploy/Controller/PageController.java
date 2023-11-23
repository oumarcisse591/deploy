package com.deployment.deploy.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping("/")
    public String homePage(){
        return "index";
    }

    @RequestMapping("/about")
    public String aboutPage(){
        return "about";
    }
}
