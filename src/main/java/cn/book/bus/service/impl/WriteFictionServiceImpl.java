package cn.book.bus.service.impl;


import cn.book.bus.aop.HttpAspect;
import cn.book.bus.domain.Chapter;
import cn.book.bus.domain.ChapterContent;
import cn.book.bus.domain.Fiction;
import cn.book.bus.mapper.ChapterContentMapper;
import cn.book.bus.mapper.ChapterMapper;
import cn.book.bus.service.IChapterContentService;
import cn.book.bus.service.IFictionService;
import cn.book.bus.service.WriteFictionService;
import cn.book.bus.utils.JsoupUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 小说抓取实现类
 */
@Service
public class WriteFictionServiceImpl implements WriteFictionService {

    private static final Logger log = LoggerFactory.getLogger(HttpAspect.class);

    @Resource
    private IFictionService iFictionService;

    @Resource
    private ChapterContentMapper chapterContentMapper;

    @Resource
    private ChapterMapper chapterMapper;


    /**
     * 抓取小说持久化到mysql数据库
     * @param fictionURL
     */
    @Async
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public   void insert(String fictionURL) {
        //爬取小说基本信息
        Fiction fiction = getFictions(JsoupUtil.getDoc(fictionURL));
        if (fiction != null) {
            //查询数据库信息
            QueryWrapper<Fiction> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("fiction_name",fiction.getFictionName());
            Fiction ft = iFictionService.getOne(queryWrapper);
            if (ft == null) {
                fiction.setFictionUrl(fictionURL);//设置url
                iFictionService.save(fiction);
                Document doc = JsoupUtil.getDoc(fictionURL);
                Elements elements = doc.select("dd>a");
                log.info("小说章数：" + elements.size());
                int k = 12;//前面12章节重复
                int sort = 1;//数据库排序
                int j = elements.size() - 12;//前面12章节重复
                ChapterContent chapterContent=new ChapterContent();
                for (int i = 0; i < j; i++) {
                    String url = elements.get(k).attr("abs:href");
                    Document document = JsoupUtil.getDoc(url);
                    String title = document.select("h1").text();//章节标题
                    String text = JsoupUtil.subContent(document.getElementById("content").html());//章节内容
                     //保存小说内容
                    chapterContentMapper.insert(chapterContent.setContent(text));
                    int id = chapterContent.getId();
                    chapterMapper.insert(new Chapter(url,fiction.getId(), title, sort,id));
                    log.info("抓取小说：" + fiction.getFictionName() + "" + title);
                    sort++;
                    k++;
                }
                log.info("抓取小说完成");
            } else {
                log.info("这本小说已经保存到数据库");
            }
        } else {
            log.info("小说连接地址错误");
        }
    }

    /*
     * 获取 书趣阁小说基本信息
     *
     * @param document
     * @return document
     */
    @Override
    public Fiction getFictions(Document document) {
        try {
            String fictionName = document.select("meta[property=og:novel:book_name]").attr("content");
//          String createDate = document.select("meta[property=og:novel:update_time]").attr("content");
            String brief = document.select("meta[property=og:description]").attr("content");
            //String brief=s.substring(0,85);
            String author = document.select("meta[property=og:novel:author]").attr("content");
            String type = document.select("meta[property=og:novel:category]").attr("content");
            String newest = document.select("meta[property=og:novel:latest_chapter_name]").attr("content");
            String state = document.select("meta[property=og:novel:status]").attr("content");
            String img = document.select("div>img").attr("abs:src");

            Elements small = document.select("div.small");//小说基本信息
            String number = JsoupUtil.sub(small.get(0).child(3).text());//字数
            return new Fiction("", img, brief, fictionName, author, type, newest, state, number);
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }
}
