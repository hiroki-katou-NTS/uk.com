package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import nts.uk.ctx.at.shared.dom.common.TimeOfDay;
import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.worktime.common.DesignatedTime;
import nts.uk.ctx.at.shared.dom.worktime.common.OneDayTime;

public class CompensatoryLeaveComSettingHelper {
	public static CompensatoryAcquisitionUse createCompensatoryAcquisitionUse() {
		return new CompensatoryAcquisitionUse(ExpirationTime.EIGHT_MONTH, ApplyPermission.ALLOW, 
				DeadlCheckMonth.FIVE_MONTH, TermManagement.MANAGE_BY_TIGHTENING);
	}
	
	public static SubstituteHolidaySetting createSubstituteHolidaySetting() {
		return new SubstituteHolidaySetting(new HolidayWorkHourRequired(true, 
				new TimeSetting(new CertainPeriodOfTime(new TimeOfDay(1)), 
						new DesignatedTime(new OneDayTime(1), new OneDayTime(2)), EnumTimeDivision.FIXED_TIME)), 
				new OvertimeHourRequired(true, 
						new TimeSetting(new CertainPeriodOfTime(new TimeOfDay(1)), 
								new DesignatedTime(new OneDayTime(1), new OneDayTime(2)), EnumTimeDivision.FIXED_TIME)));
	}
	
	public static CompensatoryDigestiveTimeUnit createCompensatoryDigestiveTimeUnit() {
		return new CompensatoryDigestiveTimeUnit(ManageDistinct.NO, TimeDigestiveUnit.FifteenMinute);
	}
	
	public static CompensatoryLeaveComSetting createCompensatoryLeaveComSetting () {
		CompensatoryLeaveComSetting comSetting = new CompensatoryLeaveComSetting(
				"000000000003-0006", 
				ManageDistinct.NO, 
				CompensatoryLeaveComSettingHelper.createCompensatoryAcquisitionUse(),
				CompensatoryLeaveComSettingHelper.createSubstituteHolidaySetting(), 
				CompensatoryLeaveComSettingHelper.createCompensatoryDigestiveTimeUnit(), 
				ManageDistinct.NO);
		return comSetting;
	}
	
	public static CompensatoryLeaveComSetting createCompensatoryLeaveComSetting (ManageDistinct isManaged, CompensatoryDigestiveTimeUnit compensatoryDigestiveTimeUnit) {
		CompensatoryLeaveComSetting comSetting = new CompensatoryLeaveComSetting(
				"000000000003-0006", 
				isManaged, 
				CompensatoryLeaveComSettingHelper.createCompensatoryAcquisitionUse(),
				CompensatoryLeaveComSettingHelper.createSubstituteHolidaySetting(), 
				compensatoryDigestiveTimeUnit, 
				ManageDistinct.NO);
		return comSetting;
	}
	
	public static CompensatoryDigestiveTimeUnit createCompensatoryDigestiveTimeUnit(ManageDistinct isManagedTime) {
		return new CompensatoryDigestiveTimeUnit(isManagedTime, TimeDigestiveUnit.FifteenMinute);
	}
}
