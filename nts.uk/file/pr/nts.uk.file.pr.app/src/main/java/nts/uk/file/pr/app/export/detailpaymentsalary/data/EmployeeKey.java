/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
/**
 * 
 */
package nts.uk.file.pr.app.export.detailpaymentsalary.data;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.report.dom.salarydetail.SalaryCategory;

/**
 * The Class EmployeeKey.
 *
 * @author duongnd
 */

@Setter
@Getter
public class EmployeeKey {
    
    /** The year month. */
    private String yearMonth;
    
    /** The employee code. */
    private String employeeCode;
    
    /** The salary category. */
    private SalaryCategory salaryCategory;
    
    /** The item name. */
    private String itemName;
}
