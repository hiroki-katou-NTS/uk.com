package nts.uk.ctx.at.record.dom.attendanceitem.monthlyattditem;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeName;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHolidayFrame;
import nts.uk.shr.com.enumcommon.NotUseAtr;

public class SpecialHolidayFrameHelper {
	public static SpecialHolidayFrame createSpecialHolidayFrame (int specialHolidayCode) {
		SpecialHolidayFrame maxDay = new SpecialHolidayFrame("000000000008-0006", specialHolidayCode, new WorkTypeName("Name"), 
				ManageDistinct.NO, NotUseAtr.NOT_USE);
		return maxDay;
	}
	
	public static SpecialHolidayFrame createSpecialHolidayFrame (ManageDistinct classification, int specialHolidayCode) {
		SpecialHolidayFrame maxDay = new SpecialHolidayFrame("000000000008-0006", specialHolidayCode, new WorkTypeName("Name"), 
				classification, NotUseAtr.NOT_USE);
		return maxDay;
	}
}
