/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.employee.classification;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.basic.dom.common.history.Period;
import nts.uk.ctx.basic.dom.company.organization.classification.ClassificationCode;
import nts.uk.ctx.basic.dom.company.organization.employee.EmployeeId;

/**
 * The Class AffiliationClassificationHistory.
 */
// 所属分類履歴
@Getter
public class AffiliationClassificationHistory extends AggregateRoot {

	/** The classification code. */
	// 分類コード
	private ClassificationCode classificationCode;

	/** The period. */
	// 期間
	private Period period;

	/** The employee id. */
	// 社員ID
	private EmployeeId employeeId;
	
	
	/**
	 * Instantiates a new classification history.
	 *
	 * @param memento the memento
	 */
	public AffiliationClassificationHistory(AffiliationClassificationHistoryGetMemento memento) {
		this.classificationCode = memento.getClassificationCode();
		this.period = memento.getPeriod();
		this.employeeId = memento.getEmployeeId();
	}
	
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(AffiliationClassificationHistorySetMemento memento){
		memento.setClassificationCode(this.classificationCode);
		memento.setPeriod(this.period);
		memento.setEmployeeId(this.employeeId);
	}
}
