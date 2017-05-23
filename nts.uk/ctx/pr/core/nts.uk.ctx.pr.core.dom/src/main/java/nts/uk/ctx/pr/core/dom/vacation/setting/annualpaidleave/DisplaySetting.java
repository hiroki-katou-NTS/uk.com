/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class DisplaySetting.
 */
@Setter
@Getter
public class DisplaySetting {
    
    /** The remaining number display. */
    private DisplayDivision remainingNumberDisplay;
    
    /** The next grant day display. */
    private DisplayDivision nextGrantDayDisplay;
}
