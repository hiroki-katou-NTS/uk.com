/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.GeneralHistoryItem;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * The Class WorkplaceHistory.
 */
@Getter
//職場履歴
public class WorkplaceHistory  extends GeneralHistoryItem<WorkplaceHistory, DatePeriod, GeneralDate> {

	/**
	 * Instantiates a new workplace history.
	 *
	 * @param memento the memento
	 */
	public WorkplaceHistory(WorkplaceHistoryGetMemento memento) {
		super(memento.getHistoryId(), memento.getPeriod());
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(WorkplaceHistorySetMemento memento) {
		memento.setHistoryId(this.identifier());
		memento.setPeriod(this.span());
	}
}
