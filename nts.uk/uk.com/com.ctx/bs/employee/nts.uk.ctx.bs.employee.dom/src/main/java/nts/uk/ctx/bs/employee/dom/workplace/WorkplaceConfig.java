/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class WorkplaceConfig.
 */
//職場構成
@Getter
public class WorkplaceConfig extends AggregateRoot {

	/** The company id. */
	//会社ID
	private String companyId;

	/** The wkp config history. */
	//履歴
	private List<WorkplaceConfigHistory> wkpConfigHistory;

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(WorkplaceConfigSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setWkpConfigHistory(this.wkpConfigHistory);
	}

	/**
	 * Instantiates a new workplace config.
	 *
	 * @param memento the memento
	 */
	public WorkplaceConfig(WorkplaceConfigGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.wkpConfigHistory = memento.getWkpConfigHistory();
	}

}
