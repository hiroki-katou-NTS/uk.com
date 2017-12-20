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
// 1週間の公休日数
public class OneWeekPublicHoliday extends DomainObject {
	
	/** The last week added days. */
	// 最終週の加算日数
	private LastWeekHolidayNumberOfOneWeek lastWeekAddedDays;
	
	/** The in legal holiday. */
	// 法定内休日日数
	private WeekNumberOfDay inLegalHoliday;
	
	/** The out legal holiday. */
	// 法定外休日日数
	private WeekNumberOfDay outLegalHoliday;
	
	/**
	 * Instantiates a new one week public holiday.
	 *
	 * @param memento the memento
	 */
	public OneWeekPublicHoliday(OneWeekPublicHolidayGetMemento memento) {
		this.lastWeekAddedDays = memento.getLastWeekAddedDays();
		this.inLegalHoliday = memento.getInLegalHoliday();
		this.outLegalHoliday = memento.getOutLegalHoliday();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(OneWeekPublicHolidaySetMemento memento) {
		memento.setLastWeekAddedDays(this.lastWeekAddedDays);
		memento.setInLegalHoliday(this.inLegalHoliday);
		memento.setOutLegalHoliday(this.outLegalHoliday);
	}
}
