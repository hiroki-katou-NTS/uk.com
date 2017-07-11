/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.employee.workplace;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.basic.dom.common.history.Period;
import nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceId;

/**
 * The Class AffiliationWorkplaceHistory.
 */
// 所属職場履歴
@Getter
public class AffWorkplaceHistory extends AggregateRoot {

	/** The period. */
	// 期間
	private Period period;

	/** The employee id. */
	// 社員ID
	private String employeeId;

	/** The work place id. */
	// 職場ID
	private WorkplaceId workplaceId;

	/**
	 * Instantiates a new employee work place history.
	 *
	 * @param memento the memento
	 */
	public AffWorkplaceHistory(AffWorkplaceHistoryGetMemento memento) {
		this.period = memento.getPeriod();
		this.employeeId = memento.getEmployeeId();
		this.workplaceId = memento.getWorkplaceId();
	}
	
	/**
	 * Save to memen to.
	 *
	 * @param memento the memen to
	 */
	public void saveToMemento(AffWorkplaceHistorySetMemento memento){
		memento.setPeriod(this.period);
		memento.setEmployeeId(this.employeeId);
		memento.setWorkplaceId(this.workplaceId);
	}
}
