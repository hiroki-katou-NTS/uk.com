package nts.uk.ctx.at.shared.dom.vacation.setting;

import nts.uk.shr.com.license.option.AttendanceOptions;
import nts.uk.shr.com.license.option.OptionLicense;

public class TimeVacationDigestUnitHelper {

	/**
	 * Create default data with 管理区分=管理する and 消化単位=1分
	 * @return 時間休暇の消化単位
	 */
	public static TimeVacationDigestUnit createDefault() {
		ManageDistinct manage = ManageDistinct.YES;
		TimeDigestiveUnit digestUnit = TimeDigestiveUnit.OneMinute;
		return new TimeVacationDigestUnit(manage, digestUnit);
	}
	
	public static TimeVacationDigestUnit createWithManage(ManageDistinct manage) {
		TimeDigestiveUnit digestUnit = TimeDigestiveUnit.OneMinute;
		return new TimeVacationDigestUnit(manage, digestUnit);
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
