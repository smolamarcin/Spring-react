package pl.smola;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
final class HomeController {

    @RequestMapping("/")
    public String index(){
        return "index";
    }
}
