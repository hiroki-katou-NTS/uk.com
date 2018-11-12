package nts.uk.ctx.pr.transfer.dom.adapter.employee;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * 所属雇用
 */
@Value
@AllArgsConstructor
public class EmploymentImport {

    /**
     * The employment code.
     */
    private String employmentCode; //雇用コード

    /**
     * The employment name.
     */
    private String employmentName; //雇用名称
}
