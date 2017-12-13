package nts.uk.ctx.at.shared.app.find.attendanceitem.daily.dto;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 日別実績の医療時間 */
@Data
public class MedicalTimeDailyPerformDto {

	/** 日勤夜勤区分: 日勤夜勤区分 */
	@AttendanceItemLayout(layout="A")
	@AttendanceItemValue(itemId=-1, type=ValueType.DATE)
	private int dayNightAtr;
	
	/** 申送時間: 勤怠時間 */
	@AttendanceItemLayout(layout="B")
	@AttendanceItemValue(itemId=-1, type=ValueType.DATE)
	private Integer takeOverTime;
	
	/** 控除時間: 勤怠時間 */
	@AttendanceItemLayout(layout="C")
	@AttendanceItemValue(itemId=-1, type=ValueType.DATE)
	private Integer deductionTime;
	
	/** 勤務時間: 勤怠時間 */
	@AttendanceItemLayout(layout="D")
	@AttendanceItemValue(itemId=-1, type=ValueType.DATE)
	private Integer workTime;
}
