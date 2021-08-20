package nts.uk.ctx.at.shared.dom.adapter.employment.rules.orgranization.empinfo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The Class EmploymentImport.
 */
//所属雇用
@AllArgsConstructor
@Data
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