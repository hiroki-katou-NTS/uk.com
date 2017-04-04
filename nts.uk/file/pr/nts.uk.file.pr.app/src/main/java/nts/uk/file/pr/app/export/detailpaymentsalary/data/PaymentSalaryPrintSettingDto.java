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
 * The Class PaymentSalaryPrintSettingDto.
 *
 * @author duongnd
 */

@Setter
@Getter
public class PaymentSalaryPrintSettingDto {
    
    /** The selected levels. */
    private List<Integer> selectedLevels;
}
