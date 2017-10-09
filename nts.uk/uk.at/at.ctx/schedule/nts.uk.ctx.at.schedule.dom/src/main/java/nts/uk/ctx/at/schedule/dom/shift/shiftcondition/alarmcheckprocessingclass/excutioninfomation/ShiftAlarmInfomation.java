package nts.uk.ctx.at.schedule.dom.shift.shiftcondition.alarmcheckprocessingclass.excutioninfomation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktimeset.TimeDayAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * シフトアラーム情報
 * 
 * @author sonnh1
 *
 */
@Getter
@AllArgsConstructor
public class ShiftAlarmInfomation {
	private String shiftAlarmId;
	private ErrorState errorState;
	private ExcutionState excutionState;
	private TimeWithDayAttr shiftAlarmStartClock;
	private TimeDayAtr shiftAlarmStartDayAtr;
	private TimeWithDayAttr shiftAlarmEndClock;
	private TimeDayAtr shiftAlarmEndDayAtr;
}
