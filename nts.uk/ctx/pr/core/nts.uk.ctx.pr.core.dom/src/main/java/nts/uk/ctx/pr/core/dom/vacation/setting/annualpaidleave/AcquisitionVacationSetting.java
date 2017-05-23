/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.vacation.setting.ApplyPermission;

/**
 * The Class AcquisitionVacationSetting.
 */
@Setter
@Getter
public class AcquisitionVacationSetting {

	/** The year vacation priority. */
	private ApplyPermission yearVacationPriority;
	
	/** The permit type. */
	private PreemptionPermit permitType;
}
