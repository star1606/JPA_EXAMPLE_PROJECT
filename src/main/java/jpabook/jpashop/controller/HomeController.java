package jpabook.jpashop.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j // 롬복으로 로거 쓸 수 있음
public class HomeController {

    // Logger log = LoggerFactory.getLogger();

    @RequestMapping("/")
    public String home() {
        log.info("home controller");
        return "home";
    }


}
