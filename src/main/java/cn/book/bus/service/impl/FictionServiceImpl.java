package cn.book.bus.service.impl;

import cn.book.bus.aop.HttpAspect;
import cn.book.bus.domain.Fiction;
import cn.book.bus.mapper.FictionMapper;
import cn.book.bus.service.IFictionService;
import cn.book.bus.vo.LayuiPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Condition;

@Service
public class FictionServiceImpl extends ServiceImpl<FictionMapper, Fiction> implements IFictionService {


    @Resource
    private IFictionService iFictionService;

    private static final Logger log = LoggerFactory.getLogger(HttpAspect.class);

    @Override
    public List<Fiction> queryLike(String v) {
        QueryWrapper<Fiction> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("fiction_name",v);
        return list(queryWrapper);
    }


    @Override
    public LayuiPage<Fiction> selectPage(long curr, long limit, Map<String, String> map) {
        if (curr == 0) {
            curr = 1;
        }
        if (limit == 0) {
            limit = 20;
        }
        //构建分页条件第二页每页显示3条
        Page<Fiction> page = new Page<>(curr, limit);
        QueryWrapper<Fiction> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_date");
        if (map.size()!=0){

            //通过map设置查询参数
            // JDK8的迭代方式
            map.forEach((key, value) -> {
                //设置条件
                System.out.println();
                queryWrapper.eq(key,value);
            });
            //使用分页条件查询
            iFictionService.page(page,queryWrapper);
        }else {
            iFictionService.page(page,queryWrapper);
        }
        //获取分页后查询出的记录
        List<Fiction> records = page.getRecords();

        LayuiPage<Fiction> layuiPage = new LayuiPage<>();
        layuiPage.setCurr(page.getCurrent());
        layuiPage.setLimit(page.getSize());
        layuiPage.setTotal(page.getTotal());
        layuiPage.setList(records);

        return layuiPage;
    }

    @Override
    public void addView(Fiction fiction) {
        int i=fiction.getViews();
        int v=i+1;
        fiction.setViews(v);
        iFictionService.updateById(fiction);
    }
}
