package cn.book.bus.controller;


import cn.book.bus.domain.Chapter;
import cn.book.bus.domain.ChapterContent;
import cn.book.bus.domain.Fiction;
import cn.book.bus.service.IChapterContentService;
import cn.book.bus.service.IChapterService;
import cn.book.bus.service.IFictionService;
import cn.book.bus.service.IFictionShelfService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 文章章节 控制器
 */
@Controller
@RequestMapping("chapter")
public class ChapterController {

    private final IChapterService iChapterService;

    private final IFictionService iFictionService;

    private final IChapterContentService iChapterContentService;

    private final IFictionShelfService iFictionShelfService;


    public ChapterController(IChapterService iChapterService, IFictionService iFictionService, IChapterContentService iChapterContentService, IFictionShelfService iFictionShelfService) {
        this.iChapterService = iChapterService;
        this.iFictionService = iFictionService;
        this.iChapterContentService = iChapterContentService;
        this.iFictionShelfService = iFictionShelfService;
    }


    //返回一本小说
    @RequestMapping(value = "info/{fiction_id}", method = RequestMethod.GET)
    public String chapter(HttpServletRequest request, @PathVariable("fiction_id") int fiction_id, Model model) {
        Fiction fiction = iFictionService.getById(fiction_id);
        //添加点击量
        iFictionService.addView(fiction);
        //检查该本小说是否保存在书架
        int i = iFictionShelfService.isShelf(request, fiction_id);
        switch (i){
            case 0: model.addAttribute("presence",0);
            break;
            case 1: model.addAttribute("presence",1);
            break;
        }
        model.addAttribute("fiction",fiction);
        model.addAttribute("title",fiction.getFictionName());
        return "chapter/info";
    }
    //返回一本小说所有章节
    @RequestMapping(value = "list/{fiction_id}", method = RequestMethod.GET)
    public String chapterList(@PathVariable("fiction_id") int fiction_id, Model model) {
        Fiction fiction = iFictionService.getById(fiction_id);
        List<Chapter> chapterList = iChapterService.queryByFictionIdList(fiction_id);
        model.addAttribute("fiction",fiction);
        model.addAttribute("list",chapterList);
        model.addAttribute("size",chapterList.size());
        model.addAttribute("title",fiction.getFictionName());
        return "chapter/list";
    }

    //当前小说页
    @RequestMapping(value = "read/{fiction_id}/{sort}", method = RequestMethod.GET)
    public String chapterInfo(HttpServletRequest request,Model model, @PathVariable("fiction_id") int fiction_id,@PathVariable("sort") int sort) {
        //更新用户阅读章节
        HttpSession session = request.getSession();

        if (null!=session.getAttribute("id")){
            int id = iFictionShelfService.updateShelf(fiction_id, (Integer) session.getAttribute("id"), sort);
            if (id!=1){
                System.out.println("更新用户章节失败");
            }
        }
        Chapter chapter=iChapterService.netChapter(fiction_id,sort);
       //获取小说内容
        ChapterContent chapterContent=iChapterContentService.getById(chapter.getContentId());
        model.addAttribute("chapter",chapter);
        model.addAttribute("content",chapterContent);
        model.addAttribute("title",chapter.getChapterTitle());
        return "chapter/read";
    }


    //上下页
    @RequestMapping(value = "netRead/{fiction_id}/{sort}/{status}",method = RequestMethod.GET)
    public String netChapter(@PathVariable("fiction_id") int fiction_id,@PathVariable("sort") int sort,@PathVariable("status") int status,Model model) {
        if (status==1){
            if (sort!=1){
                sort=sort-1;
            }
        }else {
            sort=sort+1;
        }
        Chapter chapter = iChapterService.netChapter(fiction_id,sort);
        //获取小说内容
        ChapterContent chapterContent=iChapterContentService.getById(chapter.getContentId());
        model.addAttribute("chapter",chapter);
        model.addAttribute("content",chapterContent);
        model.addAttribute("title",chapter.getChapterTitle());
        return "chapter/read";
    }
}