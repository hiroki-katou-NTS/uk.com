/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * 時間年休より優先する休暇
 */
@Getter
@Setter
@NoArgsConstructor
public class HoursHoliday {	
	
	//代休を優先	
	private boolean priorityOverpaid;
	
	//60H超休を優先超休を優先
	private boolean sixtyHoursOverrideHoliday;
	
	public HoursHoliday(boolean priorityOverpaid, boolean sixtyHoursOverrideHoliday) {
		this.priorityOverpaid = priorityOverpaid;
		this.sixtyHoursOverrideHoliday = sixtyHoursOverrideHoliday;
	}

	public void saveToMemento(HoursHolidaySetMemento memento) {
		memento.SetPriorityOverpaid(priorityOverpaid);
		memento.SetSixtyHoursOverrideHoliday(sixtyHoursOverrideHoliday);
	}
}
