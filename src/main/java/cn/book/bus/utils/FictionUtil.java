package cn.book.bus.utils;

public class FictionUtil {

    /**
     * 从书架删除用户添加的书
     * @param ids 书架id集合
     * @param id 小说id
     * @return 从新拼接
     */
    public static String removeOne(String ids, String id) {
        String string = "";
        String[] result = ids.split(",");
        for (String s : result) {
            if (!s.equals(",")) {
                if (!s.equals(id)) {
                    if (string.length() > 0) {
                        string = string.concat("," + s);
                    } else {
                        string = string.concat(s);
                    }
                }
            }
        }
        return string;
    }

    /**
     * 添加书籍到书架
     * @param ids 书架id
     * @param id 添加的id
     * @return 书架id集合
     */
    public static String addOne(String ids, String id) {
        if (ids.equals("")) {
            ids = id;
        } else {
            ids = ids.concat("," + id);
        }
        return ids;
    }
}
