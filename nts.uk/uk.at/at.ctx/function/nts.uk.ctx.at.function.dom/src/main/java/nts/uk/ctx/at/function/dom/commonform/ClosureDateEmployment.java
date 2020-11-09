package nts.uk.ctx.at.function.dom.commonform;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;

/**
 * 社員雇用と締め日
 */
@Getter
public class ClosureDateEmployment {
    // 社員ID
    private String employeeId;

    // 雇用コード
    private String employmentCode;

    // 雇用名称
    private String employmentName;

    // 締め
    private Closure closure;

    public ClosureDateEmployment(String employeeId, String employmentCode, String employmentName, Closure closure) {
        this.employeeId = employeeId;
        this.employmentCode = employmentCode;
        this.employmentName = employmentName;
        this.closure = closure;
    }
}