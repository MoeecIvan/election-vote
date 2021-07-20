package test.ivan.vote.common.exception;

import test.ivan.vote.common.bean.ApiCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统异常
 *
 * @author ivan
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysException extends RuntimeException {
    private static final long serialVersionUID = 1674890939651877661L;

    private Integer errorCode;
    private String message;

    public SysException() {
        super();
    }

    public SysException(String message) {
        super(message);
        this.message = message;
    }

    public SysException(Integer errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }

    public SysException(String message, Throwable cause) {
        super(message, cause);
    }

    public SysException(Throwable cause) {
        super(cause);
    }

    public SysException(ApiCode apiCode) {
        super(apiCode.getMessage());
        this.errorCode = apiCode.getCode();
        this.message = apiCode.getMessage();
    }
}
