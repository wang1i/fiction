package cn.book.bus.service;

import cn.book.bus.domain.Fiction;
import cn.book.bus.domain.FictionShelf;
import cn.book.bus.utils.Result;
import cn.book.bus.vo.ShelfVo;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 追风
 * @since 2020-01-05
 */
public interface IFictionShelfService extends IService<FictionShelf> {

    /**
     * 添加小说到书架
     */
   Result addShelf(HttpServletRequest request,int fiction_id);

    /**
     *是否已经保存到书架
     * 0 表示未添加或者未登录
     * 1 表示保存到了书架
     */
   int isShelf(HttpServletRequest request,int fictionId);

   /*
   查找用户的所有书
    */
   List<ShelfVo> queryByUserId(int userId) throws Exception;

   /*
   更新用户阅读章节
    */
   int  updateShelf(int fictionId,int userId,int sort);

   /*
   从书架删除小说
    */
   int deleteShelf(int id,int userId);
}
