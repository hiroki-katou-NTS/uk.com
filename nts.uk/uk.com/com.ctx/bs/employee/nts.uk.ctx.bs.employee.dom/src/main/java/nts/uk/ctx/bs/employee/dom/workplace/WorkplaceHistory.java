/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class WorkplaceHistory.
 */
@Getter
//職場履歴
public class WorkplaceHistory extends DomainObject {

	/** The history id. */
	//履歴ID
	private HistoryId historyId;

	/** The period. */
	//期間
	private Period period;

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
