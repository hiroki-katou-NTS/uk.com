/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.StatutoryWorkTimeSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSettingNew;

/**
 * The Class CompanyTransLaborHour.
 */

/**
 * Gets the company id.
 *
 * @return the company id
 */
@Getter
// 会社別変形労働労働時間.
public class CompanyTransLaborHour extends AggregateRoot implements StatutoryWorkTimeSetting{
 
	/** The company id. */
	/** 会社ID. */
	private CompanyId companyId;
	
	/** The working time setting new. */
	/** 会社労働時間設定. */
	private WorkingTimeSettingNew workingTimeSettingNew;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.StatutoryWorkTimeSettingNew#getWorkingTimeSettingNew()
	 */
	@Override
	public WorkingTimeSettingNew getWorkingTimeSettingNew() {
		return this.workingTimeSettingNew;
	}
	
	/**
	 * Instantiates a new company trans labor hour.
	 *
	 * @param memento the memento
	 */
	public CompanyTransLaborHour (CompanyTransLaborHour memento) {
		this.companyId  = memento.getCompanyId();
		this.workingTimeSettingNew = memento.getWorkingTimeSettingNew();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento (CompanyTransLaborHourSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setWorkingTimeSettingNew(this.workingTimeSettingNew);
	}

}
