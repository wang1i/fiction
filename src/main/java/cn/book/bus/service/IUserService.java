package cn.book.bus.service;

import cn.book.bus.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 追风
 * @since 2019-12-16
 */
public interface IUserService extends IService<User> {



    /**
     *  验证用户名是否可用
     * @param username 用户名
     * @return 成功或者失败
     */
    boolean verificationUser(String username);

    /**
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录成功或失败
     */
    boolean verificationUser(String username,String password);
}
