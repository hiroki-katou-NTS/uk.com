/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
/**
 * 
 */
package nts.uk.file.pr.app.export.detailpaymentsalary.data;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class PaymentSalaryReportData.
 *
 * @author duongnd
 */

@Setter
@Getter
public class PaymentSalaryReportData {
    
    /** The header data. */
    private HeaderReportData headerData;
    
    /** The employees. */
    private List<EmployeeDto> employees; // employees are sorted by department level.
    
    /** The map employee amount. */
    private Map<EmployeeKey, Double> mapEmployeeAmount;
    
    /** The report title items. */
    private List<String> reportTitleItems;
    
    /** The category items. */
    private List<CategoryItem> categoryItems;
    
    /** The configure. */
    private PaymentSalaryPrintSettingDto configure;
}
