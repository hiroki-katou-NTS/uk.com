package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeAnnualRoundProcesCla;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestUnit;
import nts.uk.shr.com.license.option.AttendanceOptions;
import nts.uk.shr.com.license.option.OptionLicense;

public class TimeAnnualSettingHelper {
	public static TimeAnnualSetting createTimeAnnualSetting () {
		TimeAnnualMaxDay annualMaxDay = TimeAnnualMaxDayHelper.createTimeAnnualMaxDay();
		
		TimeAnnualSetting annualSetting = new TimeAnnualSetting(
				annualMaxDay, TimeAnnualRoundProcesCla.TruncateOnDay0, new TimeAnnualLeaveTimeDay(),
				new TimeVacationDigestUnit(ManageDistinct.YES, TimeDigestiveUnit.OneHour));
		return annualSetting;
	}
	
	public static TimeAnnualSetting createTimeAnnualSetting_ManageDistinct_YES (ManageDistinct manageDistinct) {
		TimeAnnualMaxDay annualMaxDay = TimeAnnualMaxDayHelper.createTimeAnnualMaxDay();
		
		TimeAnnualSetting annualSetting = new TimeAnnualSetting(
				annualMaxDay, TimeAnnualRoundProcesCla.TruncateOnDay0, new TimeAnnualLeaveTimeDay(),
				new TimeVacationDigestUnit(manageDistinct, TimeDigestiveUnit.OneHour));
		return annualSetting;
	}
	
	public static TimeAnnualSetting createTimeAnnualSetting_ManageDistinct_NO (ManageDistinct manageDistinct) {
		TimeAnnualMaxDay annualMaxDay = TimeAnnualMaxDayHelper.createTimeAnnualMaxDay();
		
		TimeAnnualSetting annualSetting = new TimeAnnualSetting(
				annualMaxDay, TimeAnnualRoundProcesCla.TruncateOnDay0, new TimeAnnualLeaveTimeDay(),
				new TimeVacationDigestUnit(manageDistinct, TimeDigestiveUnit.OneHour));
		return annualSetting;
	}
	
	public static TimeAnnualSetting createTimeAnnualSetting_UpperLimitItem(ManageDistinct manageDistinct, TimeAnnualMaxDay annualMaxDay) {
		TimeAnnualSetting annualSetting = new TimeAnnualSetting(
				annualMaxDay, TimeAnnualRoundProcesCla.TruncateOnDay0, new TimeAnnualLeaveTimeDay(),
				new TimeVacationDigestUnit(manageDistinct, TimeDigestiveUnit.OneHour));
		return annualSetting;
	}
	
	/**
	 * Mock: オプションライセンスInterface
	 * hourlyPaidLeave: $Option.就業.時間休暇
	 */
	public static OptionLicense getOptionLicense(boolean hourlyPaidLeave) {
		return new OptionLicense() {
			@Override
			public AttendanceOptions attendance() {
				return new AttendanceOptions() {
					@Override
					public boolean workload() {
						return true;
					}
					@Override
					public boolean reservation() {
						return true;
					}
					@Override
					public boolean multipleWork() {
						return true;
					}
					@Override
					public boolean hourlyPaidLeave() {
						return hourlyPaidLeave;
					}
					@Override
					public boolean application() {
						return true;
					}
					@Override
					public boolean anyPeriodAggregation() {
						return true;
					}
					@Override
					public boolean alarmList() {
						return true;
					}
					@Override
					public WebTimeStamp webTimeStamp() {
						return new WebTimeStamp() {
							@Override
							public boolean isEnabled() {
								return true;
							}
							@Override
							public boolean fingerNEC() {
								return true;
							}
						};
					}
					@Override
					public Schedule schedule() {
						return new Schedule() {
							@Override
							public boolean isEnabled() {
								return true;
							}
							@Override
							public boolean nursing() {
								return true;
							}
							@Override
							public boolean medical() {
								return true;
							}
						};
					}
					@Override
					public boolean presentAndMessage() {
						return true;
					}
					@Override
					public boolean enterAndExit() {
						return true;
					}
				};
			}
		};
	}

}
