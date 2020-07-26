package cn.book.bus.service.impl;

import cn.book.bus.domain.ChapterContent;
import cn.book.bus.mapper.ChapterContentMapper;
import cn.book.bus.service.IChapterContentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class ChapterContentServiceImpl extends ServiceImpl<ChapterContentMapper, ChapterContent> implements IChapterContentService {

}
