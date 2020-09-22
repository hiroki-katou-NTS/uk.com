package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.outsideworktime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;
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
	private TimeSpanForDailyCalc timeSpan;
	
	//残業枠No
	private OverTimeFrameNo frameNo;
	
}
