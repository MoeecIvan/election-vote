package test.ivan.vote.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import test.ivan.vote.member.domain.LoginResp;
import test.ivan.vote.member.entity.Member;
import test.ivan.vote.member.param.LoginParam;

/**
 * @author Ivan
 */
public interface MemberService extends IService<Member> {
    /**
     * 会员注册
     * @param member 会员注册信息
     * @return
     */
    boolean register(Member member);

    /**
     * 会员登录
     * @param param 登录参数
     * @return
     */
    LoginResp login(LoginParam param);

    /**
     * 会员登出
     * @param token
     */
    void logout(String token);

    /**
     * 获取会员信息
     * @param memberId 会员编号
     * @return
     */
    Member getMemberInfo(Long memberId);

    /**
     * 邮箱认证
     * @param memberId 会员编号
     * @param email 邮箱
     * @return
     */
    boolean authByEmail(Long memberId, String email);

    /**
     * 身份证认证
     * @param memberId 会员编号
     * @param idCardNo 身份证号
     * @return
     */
    boolean authByIdcard(Long memberId, String idCardNo);
}
