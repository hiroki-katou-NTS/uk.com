package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.declare;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 申告深夜範囲
 * @author shuichi_ishida
 */
@Getter
@Setter
@AllArgsConstructor
public class DeclareMidnightRange {

	/** 開始時刻 */
	private Optional<TimeWithDayAttr> start;
	/** 時間 */
	private AttendanceTime time;
	
	public DeclareMidnightRange(){
		this.start = Optional.empty();
		this.time = new AttendanceTime(0);
	}
}
