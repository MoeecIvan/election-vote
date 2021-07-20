package test.ivan.vote.member.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("登录返回信息")
@Data
public class LoginResp {
    @ApiModelProperty(value = "会员编号")
    private Long memberId;

    @ApiModelProperty(value = "Token")
    private String token;
}
