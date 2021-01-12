package nts.uk.ctx.at.request.app.find.application.overtime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.overtime.HolidayMidNightTime;

@AllArgsConstructor
@NoArgsConstructor
public class HolidayMidNightTimeDto {
	// 時間
	public Integer attendanceTime;
	// 法定区分
	public Integer legalClf;
	
	public static HolidayMidNightTimeDto fromDomain(HolidayMidNightTime holidayMidNightTime) {
		return new HolidayMidNightTimeDto(
				holidayMidNightTime.getAttendanceTime().v(),
				holidayMidNightTime.getLegalClf().ordinal());
	}
}
