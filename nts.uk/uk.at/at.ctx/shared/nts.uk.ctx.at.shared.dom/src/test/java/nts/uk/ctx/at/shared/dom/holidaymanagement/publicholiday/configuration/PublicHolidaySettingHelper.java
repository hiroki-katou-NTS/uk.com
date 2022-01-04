package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration;

public class PublicHolidaySettingHelper {
	public static PublicHolidaySetting createPublicHolidaySetting() {
		return new PublicHolidaySetting("000000000006-0008", 1, 
				PublicHolidayPeriod.CLOSURE_PERIOD, 
				PublicHolidayCarryOverDeadline.CURRENT_MONTH, 
				1);
	}
	
	public static PublicHolidaySetting createPublicHolidaySetting(int isManagePublicHoliday) {
		return new PublicHolidaySetting("000000000006-0008", isManagePublicHoliday, 
				PublicHolidayPeriod.CLOSURE_PERIOD, 
				PublicHolidayCarryOverDeadline.CURRENT_MONTH, 
				1);
	}
}
