/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class CompensatoryLeaveEmSetting.
 */
@Getter
public class CompensatoryLeaveEmSetting extends AggregateRoot {

	// 会社ID
	/** The company id. */
	private String companyId;

	// 雇用区分コード
	/** The employment code. */
	private EmploymentCode employmentCode;

	/** The employment manage setting. */
	private EmploymentCompensatoryManageSetting employmentManageSetting;

	/** The employment time manage setting. */
	private EmploymentCompensatoryTimeManageSetting employmentTimeManageSetting;

	public CompensatoryLeaveEmSetting(CompensatoryLeaveEmSettingGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.employmentCode = memento.getEmploymentCode();
		this.employmentManageSetting = memento.getEmploymentManageSetting();
		this.employmentTimeManageSetting = memento.getEmploymentTimeManageSetting();
	}

	public void saveToMemento(CompensatoryLeaveEmSettingSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setEmploymentCode(this.employmentCode);
		memento.setEmploymentManageSetting(this.employmentManageSetting);
		memento.setEmploymentTimeManageSetting(this.employmentTimeManageSetting);
	}
}
