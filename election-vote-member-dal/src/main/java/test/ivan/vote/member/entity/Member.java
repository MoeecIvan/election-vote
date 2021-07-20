package test.ivan.vote.member.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 会员用户表
 *
 * @author Ivan
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("BIZ_MEMBER")
@ApiModel(value="Member对象", description="会员用户表")
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "会员编号")
    @TableId(value = "MEMBER_ID", type = IdType.AUTO)
    private Long memberId;

    @ApiModelProperty(value = "登录名")
    @TableField("LOGIN_NAME")
    private String loginName;

    @ApiModelProperty(value = "密码")
    @TableField("PASSWORD")
    private String password;

    @ApiModelProperty(value = "盐值")
    @TableField("SALT")
    private String salt;

    @ApiModelProperty(value = "昵称")
    @TableField("NICK_NAME")
    private String nickName;

    @ApiModelProperty(value = "头像")
    @TableField("AVATAR")
    private String avatar;

    @ApiModelProperty(value = "邮箱")
    @TableField("EMAIL")
    private String email;

    @ApiModelProperty(value = "身份证")
    @TableField("IDCARD")
    private String idcard;

    @ApiModelProperty(value = "认证标识类型,采用位标记法 0-未认证,1-邮箱认证,2-身份证认证,3-邮箱和身份证认证")
    @TableField("AUTH_TYPE")
    private Integer authType;

    @ApiModelProperty(value = "用户状态 0-禁用,1-正常")
    @TableField("STATUS")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "CREATED_TIME", fill = FieldFill.INSERT)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "UPDATED_TIME", fill = FieldFill.INSERT_UPDATE)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedTime;


}
