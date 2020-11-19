package nts.uk.ctx.at.request.app.command.application.overtime;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.overtime.HolidayMidNightTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.StaturoryAtrOfHolidayWork;
import nts.uk.ctx.at.shared.dom.worktype.HolidayAtr;

public class HolidayMidNightTimeCommand {
	// 時間
	public Integer attendanceTime;
	// 法定区分
	public Integer legalClf;
	
	public HolidayMidNightTime toDomain() {
		return new HolidayMidNightTime(
				new AttendanceTime(attendanceTime),
				StaturoryAtrOfHolidayWork.deicisionAtrByHolidayAtr(EnumAdaptor.valueOf(legalClf, HolidayAtr.class)));
	}
}
