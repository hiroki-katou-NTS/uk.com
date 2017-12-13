package nts.uk.ctx.at.shared.app.find.attendanceitem.daily.dto;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 日別実績の総労働時間 */
@Data
public class TotalWorkingTimeDto {

	/** 臨時時間: 日別実績の臨時時間 */
	@AttendanceItemLayout(layout="A")
	private TemporaryTimeDto temporaryTime;
	
	/** 遅刻時間: 日別実績の遅刻時間 */
	@AttendanceItemLayout(layout="B", isList=true)
	private List<LateTimeDto> lateTime;
	
	/** 短時間勤務時間: 日別実績の短時間勤務時間 */
	@AttendanceItemLayout(layout="C")
	private ShortWorkTimeSheetDto shortWorkTimeSheet;
	
	/** 早退時間: 日別実績の早退時間 */
	@AttendanceItemLayout(layout="D", isList= true)
	private List<LeaveEarlyTimeDailyPerformDto> leaveEarlyTime; 
	
	/** 所定内時間: 日別実績の所定内時間 */
	@AttendanceItemLayout(layout="E")
	private WithinStatutoryTimeDailyPerformDto withinStatutoryTime;
	
	/** 所定外時間: 日別実績の所定外時間 */
	@AttendanceItemLayout(layout="F")
	private ExcessOfStatutoryTimeDailyPerformDto excessOfStatutoryTime;
	
	/** 休憩時間: 日別実績の休憩時間 */
	@AttendanceItemLayout(layout="G")
	private BreakTimeSheetDailyPerformDto breakTimeSheet;
	
	/** 休暇: 日別実績の休暇 */
	@AttendanceItemLayout(layout="H")
	private HolidayDailyPerformDto dailyOfHoliday; 
	
	/** 外出時間: 日別実績の外出時間 */
	@AttendanceItemLayout(layout="I", isList= true)
	private List<GoOutTimeSheetDailyPerformDto> goOutTimeSheet;
	
	/** 加給時間: 日別実績の加給時間 */
	@AttendanceItemLayout(layout="J")
	private RaisingSalaryTimeDailyPerformDto raisingSalaryTime;
	
	/** 勤務回数: 勤務回数 */
	@AttendanceItemLayout(layout="K")
	@AttendanceItemValue(itemId=-1, type=ValueType.INTEGER)
	
	private int workTimes;
	/** 実働時間: 勤怠時間 */
	@AttendanceItemLayout(layout="L")
	@AttendanceItemValue(itemId=-1, type=ValueType.INTEGER)
	
	private int actualTime;
	/** 総計算時間: 勤怠時間 */
	@AttendanceItemLayout(layout="M")
	@AttendanceItemValue(itemId=-1, type=ValueType.INTEGER)
	private int totalCalcTime;
	
	/** 総労働時間: 勤怠時間 */
	@AttendanceItemLayout(layout="N")
	@AttendanceItemValue(itemId=-1, type=ValueType.INTEGER)
	private int totalWorkingTime;
}
