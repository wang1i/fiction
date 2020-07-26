package cn.book.bus.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * layui分页
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LayuiPage <T>{
    private long curr; //当前页
    private long limit; //每页页数
    private long total;//总数
    private List<T> list;
}
