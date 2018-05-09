/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.StatutoryWorkTimeSet;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * The Class EmploymentRegularWorkHour.
 */
@Getter
// 雇用別通常勤務労働時間.
public class EmpRegularLaborTime extends AggregateRoot implements StatutoryWorkTimeSet{

	/** The company id. */
	// 会社ID
	private CompanyId companyId;
	
	/** The employment code. */
	// 雇用コード
	private EmploymentCode employmentCode;
	
	/** The working time setting new. */
	// 会社労働時間設定
	private WorkingTimeSetting workingTimeSet;
	
	/**
	 * Instantiates a new employment regular work hour.
	 *
	 * @param memento the memento
	 */
	public EmpRegularLaborTime (EmpRegularLaborTimeGetMemento memento) {
		this.companyId  = memento.getCompanyId();
		this.employmentCode = memento.getEmploymentCode();
		this.workingTimeSet = memento.getWorkingTimeSet();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento (EmpRegularLaborTimeSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setEmploymentCode(this.employmentCode);
		memento.setWorkingTimeSet(this.workingTimeSet);	
	}
	
}
