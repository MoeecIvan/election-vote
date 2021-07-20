package test.ivan.vote.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import test.ivan.vote.admin.entity.User;
import test.ivan.vote.admin.mapper.UserMapper;
import test.ivan.vote.admin.service.UserService;
import test.ivan.vote.admin.service.convert.UserVoConvert;
import test.ivan.vote.admin.vo.LoginUserVo;
import test.ivan.vote.common.bean.ApiCode;
import test.ivan.vote.common.constant.CommonRedisKey;
import test.ivan.vote.common.exception.SysException;
import test.ivan.vote.common.util.CryptUtils;
import test.ivan.vote.common.util.IdUtils;
import test.ivan.vote.common.util.JwtUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author Ivan
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean login(LoginUserVo loginUser) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName, loginUser.getUserName());
        User user = this.getOne(wrapper);
        if (user == null) {
            throw new SysException(ApiCode.ACCOUNT_NOT_FOUND);
        }

        // 验证密码，加密规则： sha256(sha256(password) + salt )
        String salt = user.getSalt();
        String encryptPassword = CryptUtils.encrypt(loginUser.getPassword().toUpperCase(), salt);
        if (!encryptPassword.equals(user.getPassword())) {
            throw new SysException(ApiCode.AUTHENTICATION_EXCEPTION.getCode(), "用户名或密码错误");
        }

        long expireTime = 1800;
        String token = JwtUtils.sign(user.getUserName(), encryptPassword, expireTime);
        // token md5值
        String tokenMd5 = DigestUtils.md5Hex(token);

        // redis缓存token信息
        String tokenRedisKey = String.format(CommonRedisKey.USER_LOGIN_TOKEN, tokenMd5);
        redisTemplate.opsForValue().set(tokenRedisKey, token, expireTime, TimeUnit.SECONDS);

        loginUser.setPassword(null);
        loginUser.setToken(token);
        loginUser.setUserId(user.getUserId());

        return true;
    }

    @Override
    public LoginUserVo getUserInfo(Integer userId) {
        User user = this.getById(userId);
        if (user == null) {
            return null;
        }
        user.setPassword(null);
        return UserVoConvert.INSTANCE.userToVo(user);
    }

    public static void main(String[] args){
        String password = DigestUtils.md5Hex("123456");
        String salt = IdUtils.generateUUID();
        String encryptPassword = CryptUtils.encrypt(password.toUpperCase(), salt);
        System.out.println(String.format("password:%s\nsalt:%s\nencryptPassword:%s", password, salt, encryptPassword));
    }
}
