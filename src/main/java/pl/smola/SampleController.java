package pl.smola;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {
    @GetMapping("api/hello")
    public String hello() {
        return "Hello from the other side...";
    }
}
