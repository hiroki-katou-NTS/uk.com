package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.declare;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 申告用出勤系範囲
 * @author shuichi_ishida
 */
@Getter
@Setter
public class DeclareAttdRange {

	/** 出勤 */
	private Optional<TimeWithDayAttr> attendance;
	/** 早出開始 */
	private Optional<TimeWithDayAttr> earlyStart;
	/** 早出終了 */
	private Optional<TimeWithDayAttr> earlyEnd;
	/** 早出深夜開始 */
	private Optional<TimeWithDayAttr> earlyMnStart;
	/** 早出時間 */
	private AttendanceTime earlyTime;
	/** 早出深夜時間 */
	private AttendanceTime earlyMnTime;
	
	public DeclareAttdRange(){
		this.attendance = Optional.empty();
		this.earlyStart = Optional.empty();
		this.earlyEnd = Optional.empty();
		this.earlyMnStart = Optional.empty();
		this.earlyTime = new AttendanceTime(0);
		this.earlyMnTime = new AttendanceTime(0);
	}
}
