package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestUnit;
import nts.uk.shr.com.license.option.AttendanceOptions;
import nts.uk.shr.com.license.option.OptionLicense;

public class CompensatoryLeaveComSettingTestHelper {
	
	public static CompensatoryLeaveComSetting create() {
		return new CompensatoryLeaveComSetting("DUMMY-CID",
				ManageDistinct.YES,
				new CompensatoryAcquisitionUse(ExpirationTime.THIS_MONTH, ApplyPermission.ALLOW, DeadlCheckMonth.ONE_MONTH, TermManagement.MANAGE_BASED_ON_THE_DATE),
				new SubstituteHolidaySetting(new HolidayWorkHourRequired(false, new TimeSetting()), new OvertimeHourRequired(false, new TimeSetting())),
				new TimeVacationDigestUnit(ManageDistinct.YES, TimeDigestiveUnit.OneHour),
				ManageDistinct.YES);
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
