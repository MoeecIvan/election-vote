package test.ivan.vote.common.constant;

/**
 * Redis Key 常量
 */
public class CommonRedisKey {
    // ========== 投票相关 START ==========
    /**
     * 选举活动状态 KEY
     * election:status:electionId
     */
    public final static String ELECTION_STATUS = "election:status:%d";

    /**
     * 选举投票数据 KEY
     * election:candidate:electionId
     */
    public final static String ELECTION_CANDIDATE = "election:candidate:%d";

    /**
     * 投票规则
     * election:rules:electionId
     */
    public final static String ELECTION_RULES = "election:rules:%d";

    // ========== 投票相关 END ============

    // ========== 会员相关 START ==========
    /**
     * 会员登录Token
     * member:login:token:tokenMd5
     */
    public final static String MEMBER_LOGIN_TOKEN = "member:login:token:%s";

    /**
     * 会员信息
     * member:login:user:memberId
     */
    public final static String MEMBER_LOGIN_USER = "member:login:user:%d";
    public final static String MEMBER_USER_TOKEN = "member:token:%d";
    // ========== 会员相关 END ============

    // ========== 后台用户相关 START ==========
    /**
     * 会员登录Token
     * member:login:token:tokenMd5
     */
    public final static String USER_LOGIN_TOKEN = "user:login:token:%s";

    /**
     * 会员信息
     * member:login:user:memberId
     */
    public final static String USER_LOGIN_USER = "user:login:user:%d";
    public final static String USER_TOKEN = "user:token:%d";
    // ========== 后台用户相关 END ============
}
