/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.vacationsetting.yearvacationsetting;

import lombok.Getter;
import nts.uk.ctx.pr.core.dom.vacation.setting.ApplyPermission;

/**
 * The Class AcquisitionVacationSetting.
 */
@Getter
public class AcquisitionVacationSetting {

	/** The year vacation priority. */
	private ApplyPermission yearVacationPriority;
	
	/** The permit type. */
	private PreemptionPermit permitType;
}
