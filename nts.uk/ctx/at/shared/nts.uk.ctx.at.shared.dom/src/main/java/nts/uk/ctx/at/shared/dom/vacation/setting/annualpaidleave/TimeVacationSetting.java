/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestiveUnit;

/**
 * The Class YearVacationTimeManageSetting.
 */
@Setter
@Getter
public class TimeVacationSetting {

	/** The time manage type. */
	private ManageDistinct timeManageType;

	/** The time unit. */
	private TimeVacationDigestiveUnit timeUnit;

	/** The max day. */
	private YearVacationTimeMaxDay maxDay;

	/** The is enough time one day. */
	private boolean isEnoughTimeOneDay;
}
