package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;

/** 休出深夜 */
@Data
public class HolidayMidnightWorkDto {

	 /** 休出深夜時間: 休出深夜時間 */
	 @AttendanceItemLayout(layout="A", jpPropertyName="休出深夜時間")
	 private HolidayWorkMidNightTimeDto holidayWorkMidNightTime;

	/** 法定内: 計算付き時間 */
//	@AttendanceItemLayout(layout = "A")
	private CalcAttachTimeDto withinPrescribedHolidayWork;

	/** 法定外: 計算付き時間 */
//	@AttendanceItemLayout(layout = "B")
	private CalcAttachTimeDto excessOfStatutoryHolidayWork;

	/** 祝日: 計算付き時間 */
//	@AttendanceItemLayout(layout = "C")
	private CalcAttachTimeDto publicHolidayWork;
}
