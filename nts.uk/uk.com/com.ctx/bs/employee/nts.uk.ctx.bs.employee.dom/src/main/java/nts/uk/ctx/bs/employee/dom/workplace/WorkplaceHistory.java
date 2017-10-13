/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class WorkplaceHistory.
 */
@Getter
//職場履歴
public class WorkplaceHistory extends DomainObject {

	/** The history id. */
	//履歴ID
	private String historyId;

	/** The period. */
	//期間
	@Setter
	private DatePeriod period;

	/**
	 * Instantiates a new workplace history.
	 *
	 * @param memento the memento
	 */
	public WorkplaceHistory(WorkplaceHistoryGetMemento memento) {
		this.historyId = memento.getHistoryId();
		this.period = memento.getPeriod();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(WorkplaceHistorySetMemento memento) {
		memento.setHistoryId(this.historyId);
		memento.setPeriod(this.period);
	}
}
