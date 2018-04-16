/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * 年休より優先する休暇
 */
@Getter
@Setter
@NoArgsConstructor
public class AnnualHoliday {

	//代休を優先
	private boolean priorityPause;
	
	//振休を優先
	private boolean prioritySubstitute;
	
	//60H超休を優先
	private boolean sixtyHoursOverrideHoliday;
	
	public AnnualHoliday(boolean priorityPause, boolean prioritySubstitute, boolean sixtyHoursOverrideHoliday){
		this.priorityPause = priorityPause;
		this.prioritySubstitute = prioritySubstitute;
		this.sixtyHoursOverrideHoliday = sixtyHoursOverrideHoliday;
	}
	
	public void saveToMemento(AnnualHolidaySetMemento memento) {
		memento.SetPriorityPause(priorityPause);
		memento.SetPrioritySubstitute(prioritySubstitute);
		memento.SetSixtyHoursOverrideHoliday(sixtyHoursOverrideHoliday);
	}
}
