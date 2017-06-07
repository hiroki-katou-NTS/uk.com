/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.compensatoryleave.command;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.vacation.setting.compensatoryleave.command.dto.EmploymentCompensatoryManageSettingDto;
import nts.uk.ctx.at.shared.app.vacation.setting.compensatoryleave.command.dto.EmploymentCompensatoryTimeManageSettingDto;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveEmSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveEmSettingGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCompensatoryManageSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCompensatoryTimeManageSetting;

@Getter
@Setter
public class SaveEmploymentCompensatoryCommand {

	// 会社ID
	/** The company id. */
	private String companyId;

	// 雇用区分コード
	/** The employment code. */
	private String employmentCode;

	/** The employment manage setting. */
	private EmploymentCompensatoryManageSettingDto employmentManageSetting;

	/** The employment time manage setting. */
	private EmploymentCompensatoryTimeManageSettingDto employmentTimeManageSetting;
	
	public CompensatoryLeaveEmSetting toDomain(String companyId){
		return new CompensatoryLeaveEmSetting(new CompensatoryLeaveEmSettingGetMementoImpl(companyId,this));
	}
	public class CompensatoryLeaveEmSettingGetMementoImpl implements CompensatoryLeaveEmSettingGetMemento{

		private String companyId;
		
		private SaveEmploymentCompensatoryCommand command;
		
		/**
		 * @param companyId
		 * @param command
		 */
		public CompensatoryLeaveEmSettingGetMementoImpl(String companyId, SaveEmploymentCompensatoryCommand command) {
			this.companyId = companyId;
			this.command = command;
		}

		@Override
		public String getCompanyId() {
			return this.companyId;
		}

		@Override
		public EmploymentCode getEmploymentCode() {
			return new EmploymentCode(this.command.getEmploymentCode());
		}

		@Override
		public EmploymentCompensatoryManageSetting getEmploymentManageSetting() {
			return this.command.employmentManageSetting.toDomain();
		}

		@Override
		public EmploymentCompensatoryTimeManageSetting getEmploymentTimeManageSetting() {
			return this.command.employmentTimeManageSetting.toDomain();
		}
		
	}
}
