package cn.book.bus.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 文章章节 控制器
 */
@Controller
@RequestMapping("error")
public class ErrorController {

    //返回一本小说
    @RequestMapping("404")
    public String error() {

        return "error/404";
    }
}