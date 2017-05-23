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
    
    /** The maximum day. */
    private Integer maximumDay; // Not mapping UI.
    
    /** The manage type. */
    private ManageDistinct manageType;
    
    /** The reference. */
    private MaxDayReference reference;
    
    /** The max number uniform company. */
    private AnnualNumberDay maxNumberUniformCompany;
}
