package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.declare;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 申告時間外範囲
 * @author shuichi_ishida
 */
@Getter
@Setter
public class DeclareOvertimeRange {

	/** 終了 */
	private Optional<TimeWithDayAttr> end;
	/** 深夜開始 */
	private Optional<TimeWithDayAttr> midnightStart;
	/** 時間外 */
	private AttendanceTime overtime;
	/** 時間外深夜 */
	private AttendanceTime overtimeMidnight;
	
	public DeclareOvertimeRange(){
		this.end = Optional.empty();
		this.midnightStart = Optional.empty();
		this.overtime = new AttendanceTime(0);
		this.overtimeMidnight = new AttendanceTime(0);
	}
}
