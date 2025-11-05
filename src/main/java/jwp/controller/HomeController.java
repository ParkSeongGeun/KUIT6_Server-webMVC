package jwp.controller;

import jwp.dao.QuestionDao;
import jwp.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;

    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("questions", homeService.findAll());
        return "home";
    }
}
