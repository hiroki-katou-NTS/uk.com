/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.StatutoryWorkTimeSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSettingNew;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * The Class EmploymentDeforLaborWorkingHour.
 */
@Getter
// 雇用別変形労働労働時間.
public class EmploymentDeforLaborWorkingHour extends AggregateRoot implements StatutoryWorkTimeSetting{
	
	/** The company id. */
	/** 会社ID. */
	private CompanyId companyId;
	
	/** The employment code. */
	/** 雇用コード. */
	private EmploymentCode employmentCode;
	
	/** The working time setting new. */
	/** 会社労働時間設定. */
	private WorkingTimeSettingNew workingTimeSettingNew;
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.StatutoryWorkTimeSetting#getWorkingTimeSetting()
	 */
	@Override
	public WorkingTimeSettingNew getWorkingTimeSettingNew() {
		return workingTimeSettingNew;
	}
	
	/**
	 * Instantiates a new employment defor labor working hour.
	 *
	 * @param memento the memento
	 */
	public EmploymentDeforLaborWorkingHour (EmploymentDeforLaborWorkingHour memento) {
		this.companyId  = memento.getCompanyId();
		this.employmentCode = memento.getEmploymentCode();
		this.workingTimeSettingNew = memento.getWorkingTimeSettingNew();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento (EmploymentDeforLaborWorkingHourSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setEmploymentCode(this.employmentCode);
		memento.setWorkingTimeSettingNew(this.workingTimeSettingNew);		
	}


}
