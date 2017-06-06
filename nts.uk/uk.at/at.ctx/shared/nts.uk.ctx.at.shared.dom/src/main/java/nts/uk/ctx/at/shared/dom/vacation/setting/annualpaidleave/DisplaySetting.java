/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import lombok.Builder;

/**
 * The Class DisplaySetting.
 */
@Builder
public class DisplaySetting {
    
    /** The remaining number display. */
    public DisplayDivision remainingNumberDisplay;
    
    /** The next grant day display. */
    public DisplayDivision nextGrantDayDisplay;
}
