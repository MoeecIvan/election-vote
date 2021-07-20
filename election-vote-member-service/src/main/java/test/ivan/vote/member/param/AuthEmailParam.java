package test.ivan.vote.member.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 邮箱验证参数
 */
@ApiModel("邮箱验证参数")
@Data
public class AuthEmailParam {

    @ApiModelProperty(value = "会员编号", required = true)
    @NotNull(message = "会员编号不能为空")
    private Long memberId;

    @ApiModelProperty(value = "邮箱", required = true)
    @NotBlank(message = "邮箱不能为空")
    private String email;
}
