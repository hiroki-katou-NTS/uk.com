package nts.uk.ctx.at.shared.app.vacation.setting.retentionyearly.command;

import nts.uk.ctx.at.shared.app.vacation.setting.retentionyearly.command.dto.EmploymentSettingDto;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSetting;

public class EmploymentSaveCommand {
	private EmploymentSettingDto employmentSetting;
	public EmploymentSetting toDomain(String companyId) {
		return this.employmentSetting.toDomain(companyId);
	}
}
