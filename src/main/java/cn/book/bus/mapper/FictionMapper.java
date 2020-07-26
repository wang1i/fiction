package cn.book.bus.mapper;

import cn.book.bus.domain.Fiction;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

public interface FictionMapper extends BaseMapper<Fiction> {

    /**
     * 分页显示
     */
    IPage<Fiction> selectPageVo(Page page, @Param("state") Integer state);

}
