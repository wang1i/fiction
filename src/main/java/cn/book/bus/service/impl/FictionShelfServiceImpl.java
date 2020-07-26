package cn.book.bus.service.impl;

import cn.book.bus.domain.Fiction;
import cn.book.bus.domain.FictionShelf;
import cn.book.bus.domain.User;
import cn.book.bus.mapper.FictionShelfMapper;
import cn.book.bus.mapper.UserMapper;
import cn.book.bus.service.IFictionService;
import cn.book.bus.service.IFictionShelfService;
import cn.book.bus.service.IUserService;
import cn.book.bus.utils.FictionUtil;
import cn.book.bus.utils.Result;
import cn.book.bus.vo.ShelfVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service
public class FictionShelfServiceImpl extends ServiceImpl<FictionShelfMapper, FictionShelf> implements IFictionShelfService {

    @Resource
    private FictionShelfMapper fictionShelfMapper;


    @Resource
    private IUserService iUserService;

    @Resource
    private IFictionService iFictionService;

    @Transactional
    @Override
    public Result addShelf(HttpServletRequest request, int fiction_id) {
        HttpSession session = request.getSession();
        if (null==session.getAttribute("id")){
            return new Result(-1,"未登录");
        }
        int userId= (int) session.getAttribute("id");
        //写入书架
        FictionShelf fictionShelf=new FictionShelf();
        fictionShelf.setFictionId(fiction_id);
        fictionShelf.setSort(1);
        fictionShelf.setUserId(userId);
        fictionShelfMapper.insert(fictionShelf);
        int shelfId=fictionShelf.getId();//书架id

        //更新用户表
        User user = iUserService.getById(userId);
        if (null==user.getShelfIds()){
            user.setShelfIds("");
        }
        user.setShelfIds(FictionUtil.addOne(user.getShelfIds(), String.valueOf(shelfId)));
        iUserService.updateById(user);
        return new Result(200,"加入书架成功");
    }

    @Override
    public int isShelf(HttpServletRequest request,int fictionId) {
        HttpSession session = request.getSession();
        if (null==session.getAttribute("id")){
            return 0;
        }
        QueryWrapper<FictionShelf> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("fiction_id",fictionId);
        queryWrapper.eq("user_id",session.getAttribute("id"));

        FictionShelf fictionShelf = fictionShelfMapper.selectOne(queryWrapper);
        if (null==fictionShelf){
            return 0;
        }
        return 1;
    }

    @Override
    public List<ShelfVo> queryByUserId(int userId) throws Exception {
        QueryWrapper<FictionShelf> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        List<FictionShelf> shelfList = fictionShelfMapper.selectList(queryWrapper);
        if (null==shelfList){
            return null;
        }
        List<ShelfVo> list=new ArrayList<>();
        for (FictionShelf shelf:shelfList){
            QueryWrapper<Fiction> fictionQueryWrapper=new QueryWrapper<>();
            fictionQueryWrapper.eq("id",shelf.getFictionId());
            Fiction ft = iFictionService.getOne(fictionQueryWrapper);
           list.add(new ShelfVo(shelf.getId(),ft.getId(),ft.getFictionName(),ft.getCreateDate(),ft.getAuthor(),ft.getType(),ft.getNewest(),ft.getState(),ft.getImgUrl(),ft.getBrief(),shelf.getSort()));
        }
        return list;
    }

    @Override
    public int updateShelf(int fictionId, int userId, int sort) {
        QueryWrapper<FictionShelf> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("fiction_id",fictionId);
        queryWrapper.eq("user_id",userId);
        FictionShelf fictionShelf = fictionShelfMapper.selectOne(queryWrapper);
        fictionShelf.setSort(sort);
        return fictionShelfMapper.updateById(fictionShelf);
    }

    @Transactional
    @Override
    public int deleteShelf(int id, int userId) {
        //从书架表删除
        int i = fictionShelfMapper.deleteById(id);
        //更新用户表
        User user = iUserService.getById(userId);
        user.setShelfIds(FictionUtil.removeOne(user.getShelfIds(), String.valueOf(id)));
        iUserService.updateById(user);
        return i;
    }
}
