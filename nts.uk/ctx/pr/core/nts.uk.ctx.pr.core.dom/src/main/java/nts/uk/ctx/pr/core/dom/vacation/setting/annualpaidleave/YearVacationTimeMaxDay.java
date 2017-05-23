/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave;

import lombok.Builder;
import nts.uk.ctx.pr.core.dom.vacation.setting.ManageDistinct;

/**
 * The Class YearVacationTimeMaxDay.
 */
@Builder
public class YearVacationTimeMaxDay {
    
    /** The manage max day vacation. */
    public ManageDistinct manageMaxDayVacation;
    
    /** The reference. */
    public MaxDayReference reference;
    
    /** The max time day. */
    public MaxTimeDay maxTimeDay;
}
