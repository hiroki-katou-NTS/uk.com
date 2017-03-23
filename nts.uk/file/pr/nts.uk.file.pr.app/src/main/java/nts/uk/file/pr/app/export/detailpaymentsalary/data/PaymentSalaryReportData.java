/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
/**
 * 
 */
package nts.uk.file.pr.app.export.detailpaymentsalary.data;

import java.util.List;

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
    
    /** The payments. */
    private List<RowItemDto> payments;
    
    /** The deductions. */
    private List<RowItemDto> deductions;
    
    /** The attendances. */
    private List<RowItemDto> attendances;
    
    /** The article others. */
    private List<RowItemDto> articleOthers;
}
