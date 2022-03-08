package nts.uk.ctx.at.record.dom.attendanceitem.monthlyattditem;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.workrule.vacation.specialvacation.timespecialvacation.TimeSpecialLeaveManagementSetting;

public class TimeSpecialLeaveManagementHelper {
	
	public static TimeSpecialLeaveManagementSetting createManagementSettingManageDistinct() {
		return new TimeSpecialLeaveManagementSetting("000000000003-0004", TimeDigestiveUnit.OneHour, ManageDistinct.YES);
	}
	
	public static TimeSpecialLeaveManagementSetting createManagementSettingManageDistinctIsNo(ManageDistinct manageDistinct) {
		return new TimeSpecialLeaveManagementSetting("000000000003-0004", TimeDigestiveUnit.OneHour, manageDistinct);
	}
	
	public static TimeSpecialLeaveManagementSetting createManagementSettingManageDistinctIsYes(ManageDistinct manageDistinct) {
		return new TimeSpecialLeaveManagementSetting("000000000003-0004", TimeDigestiveUnit.OneHour, manageDistinct);
	}
}
