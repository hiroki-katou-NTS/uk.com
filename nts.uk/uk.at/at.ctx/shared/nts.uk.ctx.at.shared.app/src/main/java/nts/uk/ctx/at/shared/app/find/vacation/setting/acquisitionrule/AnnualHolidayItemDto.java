/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.vacation.setting.acquisitionrule;

import lombok.Builder;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AnnualHolidaySetMemento;

/**
 *The class AnnualHolidayItemDto
 */
@Builder
public class AnnualHolidayItemDto implements AnnualHolidaySetMemento{

	//代休を優先
	public boolean priorityPause;
	
	//振休を優先
	public boolean prioritySubstitute;
	
	//60H超休を優先
	public boolean sixtyHoursOverrideHoliday;

	@Override
	public void SetPriorityPause(boolean priorityPause) {
		this.priorityPause = priorityPause;
	}

	@Override
	public void SetPrioritySubstitute(boolean prioritySubstitute) {
		this.prioritySubstitute = prioritySubstitute;
	}

	@Override
	public void SetSixtyHoursOverrideHoliday(boolean sixtyHoursOverrideHoliday) {
		this.sixtyHoursOverrideHoliday = sixtyHoursOverrideHoliday;
	}
}
