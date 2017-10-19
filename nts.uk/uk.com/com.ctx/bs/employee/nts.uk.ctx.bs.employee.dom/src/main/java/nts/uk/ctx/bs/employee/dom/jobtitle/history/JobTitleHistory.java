/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.jobtitle.history;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class JobTitleHistory.
 */
@Getter
// 職場履歴
public class JobTitleHistory extends DomainObject {

	/** The history id. */
	// 履歴ID
	private String historyId;

	/** The period. */
	// 期間
	private DatePeriod period;

	/**
	 * Instantiates a new job title history.
	 *
	 * @param memento the memento
	 */
	public JobTitleHistory(JobTitleHistoryGetMemento memento) {
		this.historyId = memento.getHistoryId();
		this.period = memento.getPeriod();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(JobTitleHistorySetMemento memento) {
		memento.setHistoryId(this.historyId);
		memento.setPeriod(this.period);
	}
	
	/**
	 * Update start date.
	 *
	 * @param newStartDate the new start date
	 */
	public void updateStartDate(GeneralDate newStartDate) {
		this.period = new DatePeriod(newStartDate, this.period.end());
	}
	
	/**
	 * Update end date.
	 *
	 * @param newEndDate the new end date
	 */
	public void updateEndDate(GeneralDate newEndDate) {
		this.period = new DatePeriod(this.period.start(), newEndDate);
	}
}
