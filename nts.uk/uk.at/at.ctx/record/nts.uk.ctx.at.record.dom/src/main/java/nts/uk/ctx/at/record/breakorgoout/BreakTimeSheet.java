package nts.uk.ctx.at.record.breakorgoout;

import java.math.BigDecimal;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.AttendanceTime;

/**
 * 
 * @author nampt
 * 休憩時間帯
 *
 */
@Getter
public class BreakTimeSheet {
	
	//休憩枠NO - primitive value
	private String breakFrameNo;
	
	private AttendanceTime breakTime;
	
	//勤怠打刻(実打刻付き) - primitive value
	private BigDecimal startTime;
	
	//勤怠打刻(実打刻付き) - primitive value
	private BigDecimal endTime;

}
