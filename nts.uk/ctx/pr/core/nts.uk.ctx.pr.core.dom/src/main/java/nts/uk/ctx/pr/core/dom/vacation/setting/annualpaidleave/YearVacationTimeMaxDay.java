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
    
    /** The manage max day vacation. */
    private ManageDistinct manageMaxDayVacation;
    
    /** The reference. */
    private MaxDayReference reference;
    
    /** The max time day. */
    private MaxTimeDay maxTimeDay;
}
