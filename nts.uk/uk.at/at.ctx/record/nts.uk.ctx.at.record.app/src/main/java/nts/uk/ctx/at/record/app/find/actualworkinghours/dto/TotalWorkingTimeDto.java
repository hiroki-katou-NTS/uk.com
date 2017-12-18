package nts.uk.ctx.at.record.app.find.actualworkinghours.dto;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 日別実績の総労働時間 */
@Data
public class TotalWorkingTimeDto {

	/** 総労働時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "A")
	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer totalWorkingTime;

	/** 総計算時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "B")
	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer totalCalcTime;

	/** 実働時間: 勤怠時間 */
	@AttendanceItemLayout(layout = "C")
	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer actualTime;

	/** 所定内時間: 日別実績の所定内時間 */
	@AttendanceItemLayout(layout = "D")
	private WithinStatutoryTimeDailyPerformDto withinStatutoryTime;

	/** 所定外時間: 日別実績の所定外時間 */
	@AttendanceItemLayout(layout = "E")
	private ExcessOfStatutoryTimeDailyPerformDto excessOfStatutoryTime;

	/** 臨時時間: 日別実績の臨時時間 */
	@AttendanceItemLayout(layout = "F", isList = true)
	private List<TemporaryTimeFrameDto> temporaryTime;

	/** 遅刻時間: 日別実績の遅刻時間 */
	@AttendanceItemLayout(layout = "G", isList = true)
	private List<LateTimeDto> lateTime;

	/** 早退時間: 日別実績の早退時間 */
	@AttendanceItemLayout(layout = "H", isList = true)
	private List<LeaveEarlyTimeDailyPerformDto> leaveEarlyTime;

	/** 休憩時間: 日別実績の休憩時間 */
	@AttendanceItemLayout(layout = "I")
	private BreakTimeSheetDailyPerformDto breakTimeSheet;

	/** 外出時間: 日別実績の外出時間 */
	@AttendanceItemLayout(layout = "J", isList = true)
	private List<GoOutTimeSheetDailyPerformDto> goOutTimeSheet;

	/** 短時間勤務時間: 日別実績の短時間勤務時間 */
	@AttendanceItemLayout(layout = "K")
	private ShortWorkTimeSheetDto shortWorkTimeSheet;

	/** 加給時間: 日別実績の加給時間 */
	@AttendanceItemLayout(layout = "L")
	private RaisingSalaryTimeDailyPerformDto raisingSalaryTime;

	/** 休暇時間: 日別実績の休暇 */
	@AttendanceItemLayout(layout = "M")
	private HolidayDailyPerformDto dailyOfHoliday;

	/** 勤務回数: 勤務回数 */
	@AttendanceItemLayout(layout = "N")
	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer workTimes;
}
