package cn.book.bus.em;

public enum FictionEnum {

    XUAN_HUAN("玄幻魔法",1),WU_XIA("武侠修真",2),DU_SHI("都市言情",3),LI_SHI("历史军事",4),ZHEN_TAN("侦探推理",5),WANG_YOU("网游动漫",6),
    KE_HUAN("科幻灵异",7),PAI_HANG("连载中",8),QUAN_BEN("完本",9);

    private  String name;
    private  int index;

    //构造函数
    FictionEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }


    //返回该名字的代表数字
    public static  int getIndex(String name){
        for (FictionEnum f: FictionEnum.values()){
            if (f.getName().equals(name)){
                return  f.getIndex();
            }
        }
        return 0;
    }

    //返回该数字的代表名字
    public static  String getName(long index){
        for (FictionEnum f: FictionEnum.values()){
            if (f.getIndex()==index){
                return  f.getName();
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
