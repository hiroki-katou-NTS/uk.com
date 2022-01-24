package nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

public class RetentionYearlySettingHelper {
	public static UpperLimitSetting createUpperLimitSetting() {
		return new UpperLimitSetting(new RetentionYearsAmount(10), new MaxDaysRetention(1));
	}

	public static RetentionYearlySetting createRetentionYearlySetting() {
		RetentionYearlySetting retentionYearlySetting = new RetentionYearlySetting("000000000003-0004",
				RetentionYearlySettingHelper.createUpperLimitSetting(), ManageDistinct.YES);
		return retentionYearlySetting;
	}
	
	public static RetentionYearlySetting createRetentionYearlySetting(ManageDistinct manageDistinct) {
		RetentionYearlySetting retentionYearlySetting = new RetentionYearlySetting("000000000003-0004",
				RetentionYearlySettingHelper.createUpperLimitSetting(), manageDistinct);
		return retentionYearlySetting;
	}
}
