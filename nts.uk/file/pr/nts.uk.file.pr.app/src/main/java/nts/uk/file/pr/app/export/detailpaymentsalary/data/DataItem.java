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

/**
 * The Class DataIem.
 *
 * @author duongnd
 */

@Setter
@Getter
public class DataItem {
    
    /** The year month. */
    private String yearMonth;
    
    /** The code. */
    private String code;
    
    /** The level. */
    private int level;
    
    /** The amount. */
    private double amount;
}
