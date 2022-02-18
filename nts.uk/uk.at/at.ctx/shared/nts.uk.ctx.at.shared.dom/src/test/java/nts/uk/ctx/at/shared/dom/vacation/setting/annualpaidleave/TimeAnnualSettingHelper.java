package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeAnnualRoundProcesCla;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;

public class TimeAnnualSettingHelper {
	public static TimeAnnualSetting createTimeAnnualSetting () {
		TimeAnnualMaxDay annualMaxDay = TimeAnnualMaxDayHelper.createTimeAnnualMaxDay();
		
		TimeAnnualSetting annualSetting = new TimeAnnualSetting(ManageDistinct.YES, TimeDigestiveUnit.OneHour, 
				annualMaxDay, TimeAnnualRoundProcesCla.RoundUpToTheDay, new TimeAnnualLeaveTimeDay());
		return annualSetting;
	}
	
	public static TimeAnnualSetting createTimeAnnualSetting_ManageDistinct_YES (ManageDistinct manageDistinct) {
		TimeAnnualMaxDay annualMaxDay = TimeAnnualMaxDayHelper.createTimeAnnualMaxDay();
		
		TimeAnnualSetting annualSetting = new TimeAnnualSetting(manageDistinct, TimeDigestiveUnit.OneHour, 
				annualMaxDay, TimeAnnualRoundProcesCla.RoundUpToTheDay, new TimeAnnualLeaveTimeDay());
		return annualSetting;
	}
	
	public static TimeAnnualSetting createTimeAnnualSetting_ManageDistinct_NO (ManageDistinct manageDistinct) {
		TimeAnnualMaxDay annualMaxDay = TimeAnnualMaxDayHelper.createTimeAnnualMaxDay();
		
		TimeAnnualSetting annualSetting = new TimeAnnualSetting(manageDistinct, TimeDigestiveUnit.OneHour, 
				annualMaxDay, TimeAnnualRoundProcesCla.RoundUpToTheDay, new TimeAnnualLeaveTimeDay());
		return annualSetting;
	}
	
	public static TimeAnnualSetting createTimeAnnualSetting_UpperLimitItem(ManageDistinct manageDistinct, TimeAnnualMaxDay annualMaxDay) {
		TimeAnnualSetting annualSetting = new TimeAnnualSetting(manageDistinct, TimeDigestiveUnit.OneHour, 
				annualMaxDay, TimeAnnualRoundProcesCla.RoundUpToTheDay, new TimeAnnualLeaveTimeDay());
		return annualSetting;
	}
}
