/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave;

import lombok.Getter;
import nts.uk.ctx.pr.core.dom.vacation.setting.ManageDistinct;

/**
 * The Class YearVacationTimeMaxDay.
 */
@Getter
public class YearVacationTimeMaxDay {
    
    /** The max time day. */
    private MaxTimeDay maxTimeDay;
    
    /** The manage type. */
    private ManageDistinct manageType;
    
    /** The reference. */
    private MaxDayReference reference;
}
