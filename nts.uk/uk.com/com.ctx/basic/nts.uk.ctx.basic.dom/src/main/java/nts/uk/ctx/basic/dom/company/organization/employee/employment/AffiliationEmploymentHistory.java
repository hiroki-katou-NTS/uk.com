/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.employee.employment;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.basic.dom.common.history.Period;
import nts.uk.ctx.basic.dom.company.organization.employee.EmployeeId;
import nts.uk.ctx.basic.dom.company.organization.employment.EmploymentCode;

/**
 * The Class AffiliationEmploymentHistory.
 */
// 所属雇用履歴
@Getter
public class AffiliationEmploymentHistory extends AggregateRoot{

	/** The employment code. */
	// 雇用コード
	private EmploymentCode employmentCode;
	
	/** The period. */
	// 期間
	private Period period;

	/** The employee id. */
	// 社員ID
	private EmployeeId employeeId;
	
	
	/**
	 * Instantiates a new employment history.
	 *
	 * @param memento the memento
	 */
	public AffiliationEmploymentHistory(AffiliationEmploymentHistoryGetMemento memento) {
		this.employmentCode = memento.getEmploymentCode();
		this.period = memento.getPeriod();
		this.employeeId = memento.getEmployeeId();
	}
	
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(AffiliationEmploymentHistorySetMemento memento){
		memento.setEmploymentCode(this.employmentCode);
		memento.setPeriod(this.period);
		memento.setEmployeeId(this.employeeId);
	}
}
