package cn.book.bus.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 用于接收前端请求参数
 */
@Data
@AllArgsConstructor
public class FictionVo {
    private long curr;
    private long limit;
    private long count;
    private long type;
    private long state;
    private long number;
    private String fictionName;
    private long sort_list_type;  //首页展示列表模式
    private String fiction_id;

    public FictionVo() {
        if (limit==0){
            this.limit = 20;
        }
        if (sort_list_type==0){
            this.sort_list_type = 1;
        }
    }
}
