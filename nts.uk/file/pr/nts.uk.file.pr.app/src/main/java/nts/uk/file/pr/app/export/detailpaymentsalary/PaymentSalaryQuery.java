/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
/**
 * 
 */
package nts.uk.file.pr.app.export.detailpaymentsalary;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class PaymentSalaryQuery.
 *
 * @author duongnd
 */

@Setter
@Getter
public class PaymentSalaryQuery {
    
    /** The output format type. */
    private String outputFormatType;
    
    /** The output setting code. */
    private String outputSettingCode;
    
    /** The is vertical line. */
    private boolean isVerticalLine;
    
    /** The is horizontal line. */
    private boolean isHorizontalLine;
    
    /** The output language. */
    private String outputLanguage;
    
    /** The page break setting. */
    private String pageBreakSetting;
    
    /** The hierarchy. */
    private String hierarchy;
}
