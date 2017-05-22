/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave;

import lombok.Getter;

/**
 * The Class YearVacationTimeManageSetting.
 */
@Getter
public class YearVacationTimeManageSetting {
	
	/** The is enough time one day. */
	private boolean isEnoughTimeOneDay;
	
	/** The time unit. */
	private YearVacationTimeUnit timeUnit;
	
	/** The max day. */
	private YearVacationTimeMaxDay maxDay;
}
