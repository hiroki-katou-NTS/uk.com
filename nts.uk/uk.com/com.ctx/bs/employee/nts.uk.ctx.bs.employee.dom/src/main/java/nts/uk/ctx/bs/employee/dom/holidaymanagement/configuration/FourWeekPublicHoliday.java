/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.holidaymanagement.configuration;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

@Getter
@Setter
// 4週間の公休日数
public class FourWeekPublicHoliday extends DomainObject{
	
	/** The last week added days. */
	// 最終週の加算日数
	private LastWeekHolidayNumberOfFourWeek lastWeekAddedDays;
	
	/** The in legal holiday. */
	// 法定内休日日数
	private FourWeekDay inLegalHoliday;
	
	/** The out legal holiday. */
	// 法定外休日日数
	private FourWeekDay outLegalHoliday;
	
	/**
	 * Instantiates a new four week public holiday.
	 *
	 * @param memento the memento
	 */
	public FourWeekPublicHoliday(FourWeekPublicHolidayGetMemento memento) {
		this.lastWeekAddedDays = memento.getLastWeekAddedDays();
		this.inLegalHoliday = memento.getInLegalHoliday();
		this.outLegalHoliday = memento.getOutLegalHoliday();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(FourWeekPublicHolidaySetMemento memento) {
		memento.setLastWeekAddedDays(this.lastWeekAddedDays);
		memento.setInLegalHoliday(this.inLegalHoliday);
		memento.setOutLegalHoliday(this.outLegalHoliday);
	}
}
