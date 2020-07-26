package cn.book.bus.controller;

import cn.book.bus.domain.Fiction;
import cn.book.bus.dto.FictionDto;
import cn.book.bus.service.IFictionService;
import cn.book.bus.utils.ClassUtil;
import cn.book.bus.vo.LayuiPage;
import cn.book.bus.vo.FictionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页 控制器
 */
@Controller
public class IndexController {

    private final IFictionService iFictionService;


    @Autowired
    public IndexController(IFictionService iFictionService) {
        this.iFictionService = iFictionService;
    }

    /**
     * 首页
     */
    @RequestMapping(value = "index")
    public String index(FictionVo fictionVo, Model model) {
        long curr = fictionVo.getCurr();
        long limit = fictionVo.getLimit();
        Map<String,String> map=new HashMap<>();
        map= ClassUtil.setConditionMap(fictionVo,new FictionDto());
        LayuiPage<Fiction>  layuiPage = iFictionService.selectPage(curr, limit,map);

        model.addAttribute("list",layuiPage.getList());
        fictionVo.setCount(layuiPage.getTotal());
        model.addAttribute("parameter",fictionVo);
        return "index/index";
    }

    /**
     * 小说查询
     *
     */
    @RequestMapping(value = "search")
    public String  search( Model model, FictionVo fictionVo) {

        String fictionName = fictionVo.getFictionName();
        model.addAttribute("fictionName",fictionName);
        List<Fiction> list = iFictionService.queryLike(fictionName);
        model.addAttribute("list",list);
        return "index/search";
    }
}
