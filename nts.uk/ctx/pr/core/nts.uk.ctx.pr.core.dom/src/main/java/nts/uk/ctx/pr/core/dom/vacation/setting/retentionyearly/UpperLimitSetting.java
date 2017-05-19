/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.vacation.setting.retentionyearly;

import lombok.Getter;

/**
 * The Class UpperLimitSetting.
 */
@Getter
public class UpperLimitSetting {
	
	/** The retention years amount. */
	private RetentionYearsAmount retentionYearsAmount;
	
	/** The max days cumulation. */
	private MaxDaysRetention maxDaysCumulation;
}
