package test.ivan.vote.member.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 身份证验证参数
 */
@ApiModel("身份证验证参数")
@Data
public class AuthIdCardParam {

    @ApiModelProperty(value = "会员编号", required = true)
    @NotNull(message = "会员编号不能为空")
    private Long memberId;

    @ApiModelProperty(value = "身份证号", required = true)
    @NotBlank(message = "身份证号不能为空")
    private String idCard;
}
