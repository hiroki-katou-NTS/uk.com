package nts.uk.ctx.at.record.dom.attendanceitem.dailyattditem;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestUnit;
import nts.uk.ctx.at.shared.dom.workrule.vacation.specialvacation.timespecialvacation.TimeSpecialLeaveManagementSetting;

public class TimeSpecialLeaveManagementHelper {
	
	public static TimeSpecialLeaveManagementSetting createManagementSettingManageDistinct() {
		return new TimeSpecialLeaveManagementSetting("000000000003-0004", new TimeVacationDigestUnit(ManageDistinct.YES, TimeDigestiveUnit.OneHour));
	}
	
	public static TimeSpecialLeaveManagementSetting createManagementSettingManageDistinctIsNo(ManageDistinct manageDistinct) {
		return new TimeSpecialLeaveManagementSetting("000000000003-0004", new TimeVacationDigestUnit(manageDistinct, TimeDigestiveUnit.OneHour));
	}
	
	public static TimeSpecialLeaveManagementSetting createManagementSettingManageDistinctIsYes(ManageDistinct manageDistinct) {
		return new TimeSpecialLeaveManagementSetting("000000000003-0004", new TimeVacationDigestUnit(manageDistinct, TimeDigestiveUnit.OneHour));
	}
}
