package cn.book.bus.domain;

import cn.book.bus.utils.TimeUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
public class Chapter implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer fictionId;

    private String chapterTitle;

    private Timestamp createDate;

    private Integer sort;

    private Integer contentId;

    private String chapterUrl;

    public Chapter(String chapterUrl,int fictionId, String chapterTitle, Integer sort,Integer contentId) {
        this.fictionId = fictionId;
        this.chapterUrl = chapterUrl;
        this.chapterTitle = chapterTitle;
        this.createDate = TimeUtil.getTimestamp();
        this.sort = sort;
        this.contentId = contentId;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "id=" + id +
                ", fictionId=" + fictionId +
                ", chapterTitle='" + chapterTitle + '\'' +
                ", createDate=" + createDate +
                ", sort=" + sort +
                ", content_id=" + contentId +
                ", chapterUrl='" + chapterUrl + '\'' +
                '}';
    }
}
