/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.workplace;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.basic.dom.common.history.Period;
import nts.uk.ctx.basic.dom.company.organization.CompanyId;

/**
 * The Class WorkHistory.
 */
@Getter
public class WorkPlaceHistory extends AggregateRoot {

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The history id. */
	// 履歴ID
	private HistoryId historyId;

	/** The period. */
	// 期間
	private Period period;

	/** The lst work hierarchy. */
	// 職場階層
	private List<WorkPlaceHierarchy> lstWorkHierarchy;

	/**
	 * Instantiates a new work place history.
	 *
	 * @param memento the memento
	 */
	public WorkPlaceHistory(WorkPlaceHistoryGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.historyId = memento.getHistoryId();
		this.period = memento.getPeriod();
		this.lstWorkHierarchy = memento.getLstWorkHierarchy();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(WorkPlaceHistorySetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setHistoryId(this.historyId);
		memento.setPeriod(this.period);
		memento.setLstWorkHierarchy(this.lstWorkHierarchy);
	}
}
