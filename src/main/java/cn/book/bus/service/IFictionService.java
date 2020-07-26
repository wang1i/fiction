package cn.book.bus.service;

import cn.book.bus.domain.Fiction;
import cn.book.bus.vo.LayuiPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 追风
 * @since 2019-12-16
 */
public interface IFictionService extends IService<Fiction>{

    /**
     * 模糊查询
     * @param v 字符串
     * @return list
     */
    List<Fiction> queryLike(String v);

    /**
     *
     * @param curr 当前页
     * @param limit 每一页的条数
     * @param map 查询条件
     * @return 分页类
     */
    LayuiPage<Fiction> selectPage(long curr, long limit, Map<String,String> map);

    /**
     * 小说点击量
     */
  void   addView(Fiction fiction);
}
