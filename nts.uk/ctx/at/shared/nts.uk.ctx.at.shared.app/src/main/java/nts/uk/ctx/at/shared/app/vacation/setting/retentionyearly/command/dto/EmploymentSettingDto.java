package nts.uk.ctx.at.shared.app.vacation.setting.retentionyearly.command.dto;

import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSettingGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.ManagementCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.UpperLimitSetting;

public class EmploymentSettingDto{
//	private String companyId;

	/** The employment code. */
	private String employmentCode;
	private UpperLimitSettingDto upperLimitSettingDto;
	private Integer managementCategory;
	
	public EmploymentSetting toDomain(String companyId) {
		return new EmploymentSetting(new GetMementoImpl(companyId, this));
	}
	
	private class GetMementoImpl implements EmploymentSettingGetMemento{
		private EmploymentSettingDto dto;
		private String companyId;
		
		public GetMementoImpl(String companyId, EmploymentSettingDto dto) {
			super();
			this.companyId = companyId;
			this.dto = dto;
		}
		
		@Override
		public String getCompanyId() {
			return this.companyId;
		}
		@Override
		public String getEmploymentCode() {
			return dto.employmentCode;
		}
		@Override
		public UpperLimitSetting getUpperLimitSetting() {
			return dto.upperLimitSettingDto.toDomain();
		}
		@Override
		public ManagementCategory getManagementCategory() {
			return ManagementCategory.valueOf(dto.managementCategory);
		}
	}
	
}
