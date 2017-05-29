package nts.uk.ctx.at.shared.app.vacation.setting.retentionyearly.command;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.vacation.setting.retentionyearly.command.dto.EmploymentSettingDto;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSetting;

@Getter
@Setter
public class EmploymentSaveCommand {
	private EmploymentSettingDto employmentSetting;
	public EmploymentSetting toDomain(String companyId) {
		return this.employmentSetting.toDomain(companyId);
	}
}
