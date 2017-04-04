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
 * The Class HierarchyDepartmentItem.
 *
 * @author duongnd
 */

@Setter
@Getter
public class HierarchyDepartmentItem {
    
    /** The year month. */
    private String yearMonth;
    
    /** The dep code. */
    private String depCode;
    
    /** The level. */
    private int level;
    
    /** The amount. */
    private double amount;
}
