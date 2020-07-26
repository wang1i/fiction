package cn.book.bus.service.impl;

import cn.book.bus.domain.Chapter;

import cn.book.bus.mapper.ChapterMapper;
import cn.book.bus.service.IChapterService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements IChapterService {

    @Override
    public List<Chapter> queryByFictionIdList(int fictionId) {
        QueryWrapper<Chapter> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("fiction_id",fictionId);
        return list(queryWrapper);
    }

    @Override
    public Chapter netChapter(int fiction_id, int sort) {
        QueryWrapper<Chapter> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("fiction_id",fiction_id);
        queryWrapper.eq("sort",sort);
        return getOne(queryWrapper);
    }

}
