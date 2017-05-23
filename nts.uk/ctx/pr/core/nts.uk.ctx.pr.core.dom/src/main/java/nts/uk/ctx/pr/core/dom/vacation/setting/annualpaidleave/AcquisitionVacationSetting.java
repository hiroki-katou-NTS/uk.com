/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave;

import lombok.Builder;
import nts.uk.ctx.pr.core.dom.vacation.setting.ApplyPermission;

/**
 * The Class AcquisitionVacationSetting.
 */
@Builder
public class AcquisitionVacationSetting {

	/** The year vacation priority. */
	public ApplyPermission yearVacationPriority;
	
	/** The permit type. */
	public PreemptionPermit permitType;
}
