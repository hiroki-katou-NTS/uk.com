package nts.uk.ctx.at.shared.app.command.vacation.setting.specialleave;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestUnit;
import nts.uk.ctx.at.shared.dom.workrule.vacation.specialvacation.timespecialvacation.TimeSpecialLeaveManagementSetting;


/**
 * The Class TimeSpecialLeaveSaveCommand.
 */
@Setter
@Getter
@AllArgsConstructor
public class TimeSpecialLeaveSaveCommand {
	
	/** 管理区分 */
    private Integer timeManageType;

    /** 時間休暇消化単位 */
    private Integer timeUnit;
    
	public TimeSpecialLeaveManagementSetting toDomain(String companyId) {
		TimeSpecialLeaveManagementSetting specialLeaveManagementSetting = new TimeSpecialLeaveManagementSetting();
		specialLeaveManagementSetting.setCompanyId(companyId);
		ManageDistinct manage = ManageDistinct.toEnum(timeManageType);
		TimeDigestiveUnit timeDigestiveUnit = TimeDigestiveUnit.toEnum(timeUnit);
		TimeVacationDigestUnit digestUnit = new TimeVacationDigestUnit(manage, timeDigestiveUnit);
		specialLeaveManagementSetting.setTimeVacationDigestUnit(digestUnit);
		return specialLeaveManagementSetting;
	}
}
