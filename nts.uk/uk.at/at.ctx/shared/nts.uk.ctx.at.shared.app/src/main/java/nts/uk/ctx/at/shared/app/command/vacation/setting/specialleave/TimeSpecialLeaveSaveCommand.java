package nts.uk.ctx.at.shared.app.command.vacation.setting.specialleave;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.specialleave.TimeSpecialLeaveManagementSettingGetMemento;
import nts.uk.ctx.at.shared.dom.workrule.vacation.specialvacation.timespecialvacation.TimeSpecialLeaveManagementSetting;


/**
 * The Class TimeSpecialLeaveSaveCommand.
 */
@Setter
@Getter
public class TimeSpecialLeaveSaveCommand {
	
    private Integer timeManageType;

    private Integer timeUnit;
    
 
	public TimeSpecialLeaveManagementSetting toDomain(String companyId) {
		return new TimeSpecialLeaveManagementSetting(new TimeSpecialLeaveManagementSettingGetMementoIpml(companyId, this));
	}

    private class TimeSpecialLeaveManagementSettingGetMementoIpml implements TimeSpecialLeaveManagementSettingGetMemento {
    	
        private String companyId;

        private TimeSpecialLeaveSaveCommand command;

		public TimeSpecialLeaveManagementSettingGetMementoIpml(String companyId, TimeSpecialLeaveSaveCommand command) {
			this.companyId = companyId;
			this.command = command;
		}

		@Override
        public String getCompanyId() {
            return this.companyId;
        }

		@Override
		public TimeVacationDigestUnit getTimeVacationDigestUnit() {
			return new TimeVacationDigestUnit(ManageDistinct.valueOf(this.command.getTimeManageType()),
					TimeDigestiveUnit.valueOf(this.command.getTimeUnit()));
			
		}
    }
}
