/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import java.io.Serializable;

import lombok.Builder;

/**
 * The Class DisplaySetting.
 */
@Builder
public class DisplaySetting implements Serializable{
    
    /**
	 * Serializable
	 */
	private static final long serialVersionUID = 1L;

	/** The next grant day display. */
    // 次回付与日数表示区分
    public DisplayDivision nextGrantDayDisplay;
    
    /** The remaining number display. */
    // 残数表示区分
    public DisplayDivision remainingNumberDisplay;
}
