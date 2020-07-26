package cn.book.bus.service;

import cn.book.bus.domain.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 追风
 * @since 2019-12-16
 */
public interface IChapterService extends IService<Chapter>{

    List<Chapter> queryByFictionIdList( int fictionId);

    //小说下一章
    Chapter netChapter(int fiction_id, int sort);

}
