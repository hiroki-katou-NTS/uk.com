/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.holidaysetting.configuration;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

@Getter
@Setter
// 1週間の最終週の公休日数
public class LastWeekHolidayNumberOfOneWeek extends DomainObject{
	
	/** The in legal holiday. */
	// 法定内休日日数
	private WeekNumberOfDay inLegalHoliday;
	
	/** The out legal holiday. */
	// 法定外休日日数
	private WeekNumberOfDay outLegalHoliday;
	
	/**
	 * Instantiates a new last week holiday number of one week.
	 *
	 * @param memento the memento
	 */
	public LastWeekHolidayNumberOfOneWeek(LastWeekHolidayNumberOfOneWeekGetMemento memento) {
		this.inLegalHoliday = memento.getInLegalHoliday();
		this.outLegalHoliday = memento.getOutLegalHoliday();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(LastWeekHolidayNumberOfOneWeekSetMemento memento) {
		memento.setInLegalHoliday(this.inLegalHoliday);
		memento.setOutLegalHoliday(this.outLegalHoliday);
	}
}
