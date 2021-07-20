package test.ivan.vote.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import test.ivan.vote.common.bean.ApiCode;
import test.ivan.vote.common.constant.CommonRedisKey;
import test.ivan.vote.common.exception.SysException;
import test.ivan.vote.common.util.CryptUtils;
import test.ivan.vote.common.util.IdUtils;
import test.ivan.vote.common.util.JwtUtils;
import test.ivan.vote.member.domain.LoginResp;
import test.ivan.vote.member.entity.Member;
import test.ivan.vote.member.mapper.MemberMapper;
import test.ivan.vote.member.service.MemberService;
import test.ivan.vote.member.enums.MemberAuthType;
import test.ivan.vote.member.enums.MemberStatus;
import test.ivan.vote.member.param.LoginParam;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * @author Ivan
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean register(Member member) {
        LambdaQueryWrapper<Member> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Member::getLoginName, member.getLoginName());
        if (this.getOne(wrapper) != null) {
            throw new SysException(ApiCode.BUSINESS_EXCEPTION.getCode(), "用户名已存在，请换其它用户名。");
        }
        member.setStatus(MemberStatus.NORMAL.getStatus());
        member.setAuthType(MemberAuthType.UNAUTH.getType());
        if (StringUtils.isBlank(member.getNickName())) {
            member.setNickName(member.getLoginName());
        }
        // 生成盐值并加密用户密码
        String salt = IdUtils.generateUUID();
        String encryptPassword = CryptUtils.encrypt(member.getPassword().toUpperCase(), salt);
        member.setSalt(salt);
        member.setPassword(encryptPassword);

        return this.save(member);
    }

    @Override
    public LoginResp login(LoginParam param) {
        LambdaQueryWrapper<Member> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Member::getLoginName, param.getLoginName());
        Member member = this.getOne(queryWrapper);
        if (member == null) {
            // 抛出找不到用户的错误信息
            return null;
        }
        // 生成盐值并加密用户密码
        String salt = member.getSalt();
        String encryptPassword = CryptUtils.encrypt(param.getPassword().toUpperCase(), salt);
        if (!member.getPassword().equals(encryptPassword)) {
            // 密码不一致，抛出密码错误信息
            throw new SysException(ApiCode.LOGIN_EXCEPTION);
        }

        // 登录成功
        long expireTime = 1800;
        String token = JwtUtils.sign(String.valueOf(member.getMemberId()), encryptPassword, expireTime);
        // token md5值
        String tokenMd5 = DigestUtils.md5Hex(token);

        // redis缓存token信息
        String tokenRedisKey = String.format(CommonRedisKey.MEMBER_LOGIN_TOKEN, tokenMd5);
        redisTemplate.opsForValue().set(tokenRedisKey, token, expireTime, TimeUnit.SECONDS);

        LoginResp resp = new LoginResp();
        resp.setMemberId(member.getMemberId());
        resp.setToken(token);
        return resp;
    }

    @Override
    public void logout(String token) {
        // token md5值
        String tokenMd5 = DigestUtils.md5Hex(token);

        // redis缓存token信息
        String tokenRedisKey = String.format(CommonRedisKey.MEMBER_LOGIN_TOKEN, tokenMd5);
        redisTemplate.delete(tokenRedisKey);
    }

    @Override
    public Member getMemberInfo(Long memberId) {
        LambdaQueryWrapper<Member> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Member::getMemberId, memberId)
                .select(Member::getMemberId, Member::getLoginName, Member::getNickName, Member::getAvatar,
                        Member::getAuthType, Member::getEmail, Member::getIdcard, Member::getStatus, Member::getStatus,
                        Member::getCreatedTime);
        Member member = this.getOne(wrapper);

        return member;
    }

    @Override
    public boolean authByEmail(Long memberId, String email) {
        // 匹配邮箱
        String pattern = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
        boolean isMatch = Pattern.matches(pattern, email);
        if (!isMatch) {
            return false;
        }

        // 查找会员
        Member member = this.getById(memberId);
        if (member == null) {
            throw new SysException(ApiCode.ACCOUNT_NOT_FOUND.getCode(), "会员不存在。");
        }

        // 更新数据库
        member.setEmail(email);
        member.setAuthType(member.getAuthType() | MemberAuthType.EMAIL.getType());
        return this.updateById(member);
    }

    @Override
    public boolean authByIdcard(Long memberId, String idCardNo) {
        // 匹配身份证规则
        String pattern = "^[A-Z]{1,1}\\d{6}\\(?[0-9]\\)?$";
        boolean isMatch = Pattern.matches(pattern, idCardNo);
        if (!isMatch) {
            return false;
        }

        // 查找会员
        Member member = this.getById(memberId);
        if (member == null) {
            throw new SysException(ApiCode.ACCOUNT_NOT_FOUND.getCode(), "会员不存在。");
        }

        // 更新数据库
        member.setIdcard(idCardNo);
        member.setAuthType(member.getAuthType() | MemberAuthType.IDCARD.getType());
        return this.updateById(member);
    }

    public static void main(String[] args){
        //MemberService service = new MemberServiceImpl();
        //boolean match = service.authByIdcard(1L, "A123456(7");
        String idcard = "Z013456(0)";
        String pattern = "^[A-Z]{1}\\d{6}\\({1}[0-9]\\){1}$";
        boolean match = Pattern.matches(pattern, idcard);
        System.out.println("match:"+match);
    }
}
