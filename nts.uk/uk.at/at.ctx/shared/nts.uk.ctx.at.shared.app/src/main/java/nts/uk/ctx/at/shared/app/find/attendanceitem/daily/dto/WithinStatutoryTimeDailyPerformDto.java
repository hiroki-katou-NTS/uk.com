package nts.uk.ctx.at.shared.app.find.attendanceitem.daily.dto;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 日別実績の所定内時間 */
@Data
public class WithinStatutoryTimeDailyPerformDto {

	/** 所定内深夜時間: 所定内深夜時間*/
	@AttendanceItemLayout(layout="A")
	private WithinStatutoryMidNightTimeDto withinStatutoryMidNightTime;
	/** 就業時間: 勤怠時間*/
	@AttendanceItemLayout(layout="B")
	@AttendanceItemValue(itemId=-1, type=ValueType.INTEGER)
	private int workTime;
	/** 所定内割増時間: 勤怠時間*/
	@AttendanceItemLayout(layout="C")
	@AttendanceItemValue(itemId=-1, type=ValueType.INTEGER)
	private int withinPrescribedPremiumTime;
	/** 実働就業時間: 勤怠時間*/
	@AttendanceItemLayout(layout="D")
	@AttendanceItemValue(itemId=-1, type=ValueType.INTEGER)
	private int workTimeIncludeVacationTime;
	/** 休暇加算時間: 勤怠時間*/
	@AttendanceItemLayout(layout="E")
	@AttendanceItemValue(itemId=-1, type=ValueType.INTEGER)
	private int vacationAddTime;
}
