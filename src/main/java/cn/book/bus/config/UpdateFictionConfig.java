package cn.book.bus.config;


import cn.book.bus.aop.HttpAspect;
import cn.book.bus.domain.Fiction;
import cn.book.bus.service.IFictionService;
import cn.book.bus.service.UpdateFictionService;
import cn.book.bus.service.WriteFictionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.List;

@Configuration
public class UpdateFictionConfig {

    @Resource
    private IFictionService iFictionService;

    @Resource
    private UpdateFictionService updateFictionService;

    @Resource
    private WriteFictionService writeFictionService;

    private static final Logger log = LoggerFactory.getLogger(HttpAspect.class);

    //秒 分 时 日 月 周
    @Scheduled(cron = "0 0 12 * * ?") //更新时间
    public void update(){
        updateFiction();
    }
    private void updateFiction(){
        //获取所有小说
        List<Fiction> list=iFictionService.list();
        //循环更新小说
        for (Fiction fiction:list){
            updateFictionService.updateFiction(fiction.getId(),fiction.getFictionUrl());
            try {
                Thread.sleep(300000);
            } catch (InterruptedException e) {
                log.info("线程休眠异常");
                e.printStackTrace();
            }
        }
        log.info("开始更新小说");
    }
}
