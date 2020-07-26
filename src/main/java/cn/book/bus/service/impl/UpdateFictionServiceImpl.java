package cn.book.bus.service.impl;

import cn.book.bus.aop.HttpAspect;
import cn.book.bus.domain.Chapter;
import cn.book.bus.domain.ChapterContent;
import cn.book.bus.domain.Fiction;
import cn.book.bus.mapper.ChapterContentMapper;
import cn.book.bus.mapper.ChapterMapper;
import cn.book.bus.service.IChapterService;
import cn.book.bus.service.IFictionService;
import cn.book.bus.service.UpdateFictionService;
import cn.book.bus.utils.JsoupUtil;
import cn.book.bus.utils.TimeUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UpdateFictionServiceImpl implements UpdateFictionService {


    @Resource
    private IFictionService iFictionService;

    @Resource
    private IChapterService iChapterService;

    @Resource
    private ChapterMapper chapterMapper;

    @Resource
    private ChapterContentMapper chapterContentMapper;

    // 系统调度 定时更新小说 此处开启了事务
    @Async
    @Override
    public void updateFiction(int fiction_id, String fiction_url) {
        QueryWrapper<Chapter> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("fiction_id",fiction_id);
        List<Chapter> list=iChapterService.list(queryWrapper);
        Fiction fiction=iFictionService.getById(fiction_id);
        int catLength=list.size();
        Document doc = JsoupUtil.getDoc(fiction_url);
        Elements elements = doc.select("dd>a");
        int elnLength=elements.size()-12;
        if (catLength!=elnLength){
            if (catLength==0){
                int sort = 1;
                int cot = 0;
                ChapterContent chapterContent=new ChapterContent();
                for (Element element : elements) {
                    if (cot<12){
                        cot++;
                        continue;
                    }
                    String url = element.attr("abs:href");
                    Document document = JsoupUtil.getDoc(url);
                    String title = document.select("h1").text();//章节标题
                    String text = document.getElementById("content").html();//章节内容
                    //保存小说内容
                    chapterContentMapper.insert(chapterContent.setContent(text));

                    chapterMapper.insert(new Chapter(url,fiction_id, title, sort,chapterContent.getId()));
                    System.out.println("重新抓取小说：" + title);
                    sort++;
                }
            }else {
                //有更新
                ChapterContent chapterContent=new ChapterContent();
                int i=elements.size()-catLength-12;
                System.out.println(fiction.getFictionName()+"最新章节有："+i+"章");
                int sort=catLength+1;
                int k=catLength+12;//最新章节下标
                Chapter chapter=new Chapter();
                for (int j=0;j<i;j++){
                String url=elements.get(k).attr("abs:href");
                Document document = JsoupUtil.getDoc(url);
                String title = document.select("h1").text();//章节标题
                String text = document.getElementById("content").html();//章节内容
                    //保存小说内容
                chapterContentMapper.insert(chapterContent.setContent(text));
                chapter.setChapterUrl(url);
                chapter.setFictionId(fiction_id);
                chapter.setChapterTitle(title);
                chapter.setSort(sort);
                chapter.setContentId(chapterContent.getId());//章节内容id
                chapter.setCreateDate(TimeUtil.getTimestamp());
                chapterMapper.insert(chapter);
                   k++;
                   sort++;//排序
                   // 设置最新章节
                   fiction.setNewest(title);
                }
                //更新时间
                fiction.setCreateDate(TimeUtil.getTimestamp());
                //更新最新章节
                iFictionService.updateById(fiction);
            }
        }else {
            System.out.println(fiction.getFictionName()+"小说已经是最新");
        }
    }
}
