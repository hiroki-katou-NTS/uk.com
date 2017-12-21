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
// ４週間の最終週の公休日数
public class LastWeekHolidayNumberOfFourWeek extends DomainObject{
	
	/** The in legal holiday. */
	// 法定内休日日数
	private FourWeekDay inLegalHoliday;
	
	/** The out legal holiday. */
	// 法定外休日日数
	private FourWeekDay outLegalHoliday;
	
	/**
	 * Instantiates a new last week holiday number of four week.
	 *
	 * @param memento the memento
	 */
	public LastWeekHolidayNumberOfFourWeek(LastWeekHolidayNumberOfFourWeekGetMemento memento) {
		this.inLegalHoliday = memento.getInLegalHoliday();
		this.outLegalHoliday = memento.getOutLegalHoliday();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(LastWeekHolidayNumberOfFourWeekSetMemento memento) {
		memento.setInLegalHoliday(this.inLegalHoliday);
		memento.setOutLegalHoliday(this.outLegalHoliday);
	}
}
