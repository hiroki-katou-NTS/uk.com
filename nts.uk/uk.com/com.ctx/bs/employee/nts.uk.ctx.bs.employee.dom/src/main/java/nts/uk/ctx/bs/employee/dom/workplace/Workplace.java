/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class Workplace.
 */

@Getter
//職場
public class Workplace extends AggregateRoot{

	/** The company id. */
	// 会社ID
	private String companyId;

	/** The workplace history. */
	//履歴
	private List<WorkplaceHistory> workplaceHistory;

	/**
	 * Instantiates a new workplace.
	 *
	 * @param memento the memento
	 */
	public Workplace(WorkplaceGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.workplaceHistory = memento.getWorkplaceHistory();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(WorkplaceSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setWorkplaceHistory(this.workplaceHistory);
	}
}
