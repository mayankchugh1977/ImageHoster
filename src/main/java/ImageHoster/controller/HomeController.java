package ImageHoster.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {


    public HomeController(){
        System.out.println("** HomeController **");
    }


    @RequestMapping("/")
    public String getRoot(){
        return "index";
    }
}