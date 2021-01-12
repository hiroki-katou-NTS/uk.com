package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.declare;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 申告早出範囲
 * @author shuichi_ishida
 */
@Getter
@Setter
public class DeclareEarlyRange {

	/** 開始 */
	private Optional<TimeWithDayAttr> start;
	/** 深夜開始 */
	private Optional<TimeWithDayAttr> midnightStart;
	/** 早出 */
	private AttendanceTime early;
	/** 早出深夜 */
	private AttendanceTime earlyMidnight;
	
	public DeclareEarlyRange(){
		this.start = Optional.empty();
		this.midnightStart = Optional.empty();
		this.early = new AttendanceTime(0);
		this.earlyMidnight = new AttendanceTime(0);
	}
}
