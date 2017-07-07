/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.employee.jobtile;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.basic.dom.common.history.Period;
import nts.uk.ctx.basic.dom.company.organization.jobtitle.PositionId;

/**
 * The Class JobTitleHistory.
 */
//所属職位履歴
@Getter
public class AffiliationJobTitleHistory extends AggregateRoot{
	
	/** The period. */
	// 期間
	private Period period;

	/** The employee id. */
	// 社員ID
	private String employeeId;
	
	
	/** The job title id. */
	// 職位ID
	private PositionId jobTitleId;
	
	
	/**
	 * Instantiates a new job title history.
	 *
	 * @param memento the memento
	 */
	public AffiliationJobTitleHistory(AffiliationJobTitleHistoryGetMemento memento) {
		this.period = memento.getPeriod();
		this.employeeId = memento.getEmployeeId();
		this.jobTitleId = memento.getJobTitleId();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(AffiliationJobTitleHistorySetMemento memento){
		memento.setPeriod(this.period);
		memento.setEmployeeId(this.employeeId);
		memento.setJobTitleId(this.jobTitleId);
	}
	
}
