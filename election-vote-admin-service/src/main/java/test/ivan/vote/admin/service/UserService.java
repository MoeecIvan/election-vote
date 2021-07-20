package test.ivan.vote.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import test.ivan.vote.admin.entity.User;
import test.ivan.vote.admin.vo.LoginUserVo;

/**
 * @author Ivan
 */
public interface UserService extends IService<User> {
    /**
     * 后台用户登录，登录成功返回Token
     * @param loginUser
     * @return
     */
    boolean login(LoginUserVo loginUser);

    /**
     * 获取登录用户信息
     * @param userId
     * @return
     */
    LoginUserVo getUserInfo(Integer userId);
}
