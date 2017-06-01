/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestiveUnit;

/**
 * Gets the digestive unit.
 *
 * @return the digestive unit
 */
@Getter
public class EmploymentCompensatoryTimeManageSetting {
	//管理区分
	/** The is managed. */
	private ManageDistinct isManaged;
	
	//消化単位
	/** The digestive unit. */
	private TimeVacationDigestiveUnit digestiveUnit;  
}
