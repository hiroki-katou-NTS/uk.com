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
 * The Class CompanyRegularLaborHour.
 */
@Getter
// 会社別通常勤務労働時間.
public class CompanyRegularLaborHour extends AggregateRoot implements StatutoryWorkTimeSetting {

	/** The company id. */
	/** 会社ID. */
	private CompanyId companyId;

	/** The working time setting new. */
	/** 会社労働時間設定. */
	private WorkingTimeSettingNew workingTimeSettingNew;

	/**
	 * Instantiates a new company regular labor hour.
	 *
	 * @param memento
	 *            the memento
	 */
	public CompanyRegularLaborHour(CompanyRegularLaborHour memento) {
		this.companyId = memento.getCompanyId();
		this.workingTimeSettingNew = memento.getWorkingTimeSettingNew();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(CompanyRegularLaborHourSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setWorkingTimeSettingNew(this.workingTimeSettingNew);
	}
}
