/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.compensatoryleave.find.dto;

import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveEmSettingSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCompensatoryManageSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCompensatoryTimeManageSetting;

public class CompensatoryLeaveEmSettingDto implements CompensatoryLeaveEmSettingSetMemento {

	// 会社ID
	/** The company id. */
	public String companyId;

	// 雇用区分コード
	/** The employment code. */
	public EmploymentCode employmentCode;

	/** The employment manage setting. */
	public EmploymentCompensatoryManageSettingDto employmentManageSetting;

	/** The employment time manage setting. */
	public EmploymentCompensatoryTimeManageSettingDto employmentTimeManageSetting;

	@Override
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	@Override
	public void setEmploymentCode(EmploymentCode employmentCode) {
		this.employmentCode = employmentCode;
	}

	@Override
	public void setEmploymentManageSetting(EmploymentCompensatoryManageSetting employmentManageSetting) {
		EmploymentCompensatoryManageSettingDto employmentCompensatoryManageSettingDto = new EmploymentCompensatoryManageSettingDto();
		employmentManageSetting.saveToMemento(employmentCompensatoryManageSettingDto);
		this.employmentManageSetting = employmentCompensatoryManageSettingDto;
	}

	@Override
	public void setEmploymentTimeManageSetting(EmploymentCompensatoryTimeManageSetting employmentTimeManageSetting) {
		EmploymentCompensatoryTimeManageSettingDto employmentCompensatoryTimeManageSettingDto = new EmploymentCompensatoryTimeManageSettingDto();
		employmentTimeManageSetting.saveToMemento(employmentCompensatoryTimeManageSettingDto);
		this.employmentTimeManageSetting = employmentCompensatoryTimeManageSettingDto;
	}

}
