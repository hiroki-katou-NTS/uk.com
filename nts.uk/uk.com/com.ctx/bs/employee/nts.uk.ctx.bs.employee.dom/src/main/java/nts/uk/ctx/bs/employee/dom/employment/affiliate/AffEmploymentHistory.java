/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.employment.affiliate;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class AffEmploymentHistory.
 */
// 所属雇用履歴
@Getter
public class AffEmploymentHistory extends AggregateRoot{

	/** The employment code. */
	// 雇用コード
	private EmploymentCode employmentCode;
	
	/** The period. */
	// 期間
	private DatePeriod period;

	/** The employee id. */
	// 社員ID
	private String employeeId;
	
	
	/**
	 * Instantiates a new employment history.
	 *
	 * @param memento the memento
	 */
	public AffEmploymentHistory(AffEmploymentHistoryGetMemento memento) {
		this.employmentCode = memento.getEmploymentCode();
		this.period = memento.getPeriod();
		this.employeeId = memento.getEmployeeId();
	}
	
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(AffEmploymentHistorySetMemento memento){
		memento.setEmploymentCode(this.employmentCode);
		memento.setPeriod(this.period);
		memento.setEmployeeId(this.employeeId);
	}
}
