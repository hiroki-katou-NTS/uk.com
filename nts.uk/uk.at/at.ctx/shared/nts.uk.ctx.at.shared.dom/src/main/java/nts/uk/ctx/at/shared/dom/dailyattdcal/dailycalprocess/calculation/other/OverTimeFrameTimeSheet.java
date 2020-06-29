package nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;

/**
 * 残業時間枠時間帯
 * @author keisuke_hoshina
 *
 */
@Getter
@AllArgsConstructor
public class OverTimeFrameTimeSheet {

	//時間帯
	private TimeSpanForCalc timeSpan;
	
	//残業枠No
	private OverTimeFrameNo frameNo;
	
}
