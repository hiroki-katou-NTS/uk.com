package nts.uk.ctx.at.record.dom.attendanceitem.dailyattditem;

import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ManageDeadline;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.SubstVacationSetting;

public class ComSubstVacationHelper {
	public static ComSubstVacation createComSubstVacation() {
		SubstVacationSetting vacationSetting = new SubstVacationSetting(ManageDeadline.MANAGE_BY_BASE_DATE, 
				ExpirationTime.EIGHT_MONTH, ApplyPermission.ALLOW);
		ComSubstVacation comSubstVacation = new ComSubstVacation("000000000003-0004", vacationSetting, ManageDistinct.NO, ManageDistinct.NO);
		return comSubstVacation;
	}
	
	public static ComSubstVacation createComSubstVacation(ManageDistinct manageDistinct) {
		SubstVacationSetting vacationSetting = new SubstVacationSetting(ManageDeadline.MANAGE_BY_BASE_DATE, 
				ExpirationTime.EIGHT_MONTH, ApplyPermission.ALLOW);
		ComSubstVacation comSubstVacation = new ComSubstVacation("000000000003-0004", vacationSetting, manageDistinct, ManageDistinct.NO);
		return comSubstVacation;
	}
}
