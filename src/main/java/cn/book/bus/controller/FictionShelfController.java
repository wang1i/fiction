package cn.book.bus.controller;


import cn.book.bus.service.IFictionShelfService;
import cn.book.bus.utils.Result;
import cn.book.bus.vo.ShelfVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class FictionShelfController {

    private final IFictionShelfService iFictionShelfService;

    @Autowired
    public FictionShelfController(IFictionShelfService iFictionShelfService) {
        this.iFictionShelfService = iFictionShelfService;
    }

    /**
     * 书架
     */
    @RequestMapping(value = "shelf")
    public String Shelf(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        try {
            List<ShelfVo> list = iFictionShelfService.queryByUserId((Integer) session.getAttribute("id"));
            model.addAttribute("list",list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "shelf/shelf";
    }


    /**
     * 用户加入书架
     *
     * @param Fiction_id 小说id
     * @return 成功或者失败
     */
    @ResponseBody
    @RequestMapping(value = "addShelf")
    public Result AddShelf(HttpServletRequest request, @RequestParam("fiction_id") int Fiction_id) {

        return iFictionShelfService.addShelf(request, Fiction_id);
    }

    /*
    从书架删除小说
     */
    @ResponseBody
    @RequestMapping(value = "deleteShelf")
    public Result deleteShelf(HttpServletRequest request, @RequestParam("id") int id){
        HttpSession session = request.getSession();
        int userId= (int) session.getAttribute("id");
        int i = iFictionShelfService.deleteShelf(id, userId);
        if (i==0){
            return new Result(-1,"移除书架失败");
        }
        return new Result(200,"移除书架成功");
    }
}

