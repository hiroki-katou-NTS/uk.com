/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave;

import lombok.Getter;
import nts.uk.ctx.pr.core.dom.vacation.setting.ManageDistinct;

/**
 * The Class HalfDayManage.
 */
@Getter
public class HalfDayManage {
    
    /** The company uniform limit number. */
    private AnnualNumberDay companyUniformLimitNumber;
    
    /** The manage type. */
    private ManageDistinct manageType;
    
    /** The reference. */
    private MaxDayReference reference;
    
    /** The maximum day. */
    private Integer maximumDay; // Not mapping UI.
}
