package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.shr.com.license.option.AttendanceOptions;
import nts.uk.shr.com.license.option.OptionLicense;

public class AnnualPaidLeaveSettingTestHelper {
	
	public static AnnualPaidLeaveSetting createDefault() {
		String companyId = "DUMMY-CID";
		AcquisitionSetting acquisitionSetting = new AcquisitionSetting(AnnualPriority.FIFO);
		ManageDistinct yearManageType = ManageDistinct.YES;
		ManageAnnualSetting manageAnnualSetting = createManageAnnualSetting();
		TimeAnnualSetting timeSetting = TimeAnnualSettingHelper.createTimeAnnualSetting();
		return new AnnualPaidLeaveSetting(companyId, acquisitionSetting, yearManageType, manageAnnualSetting, timeSetting);
	}
	
	private static ManageAnnualSetting createManageAnnualSetting() {
		ManageAnnualSettingGetMemento memento = new ManageAnnualSettingGetMemento() {
			
			@Override
			public YearLyOfNumberDays getYearLyOfDays() {
				return null;
			}
			
			@Override
			public RemainingNumberSetting getRemainingNumberSetting() {
				return null;
			}
			
			@Override
			public HalfDayManage getHalfDayManage() {
				return null;
			}
			
			@Override
			public String getCompanyId() {
				return "DUMMY-CID";
			}
		};
		return new ManageAnnualSetting(memento);
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
