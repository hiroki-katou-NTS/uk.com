/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours;

import nts.uk.ctx.at.shared.dom.vacation.setting.SixtyHourExtra;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestUnit;

/**
 * The Interface ComSubstVacationSetMemento.
 */
public interface Com60HourVacationSetMemento {

	void setCompanyId(String companyId);

	void setTimeVacationDigestUnit(TimeVacationDigestUnit digestiveUnit);
	
	void setSixtyHourExtra(SixtyHourExtra sixtyHourExtra);

}
