/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.vacation.setting.acquisitionrule;

import lombok.Builder;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.HoursHolidaySetMemento;

/**
 * The class HoursHolidayItemDto
 */
@Builder
public class HoursHolidayItemDto implements HoursHolidaySetMemento{

	//代休を優先	
	public boolean priorityOverpaid;
	
	//60H超休を優先超休を優先
	public boolean sixtyHoursOverrideHoliday;

	@Override
	public void SetPriorityOverpaid(boolean priorityOverpaid) {
		this.priorityOverpaid = priorityOverpaid;		
	}

	@Override
	public void SetSixtyHoursOverrideHoliday(boolean sixtyHoursOverrideHoliday) {
		this.sixtyHoursOverrideHoliday = sixtyHoursOverrideHoliday;		
	}
}
