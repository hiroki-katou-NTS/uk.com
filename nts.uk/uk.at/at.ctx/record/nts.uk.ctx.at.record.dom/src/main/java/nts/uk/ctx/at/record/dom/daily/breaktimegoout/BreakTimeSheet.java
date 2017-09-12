package nts.uk.ctx.at.record.dom.daily.breaktimegoout;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;

/**
 * 休�?時間帯
 * @author keisuke_hoshina
 *
 */
@Value
public class BreakTimeSheet {
	
	private TimeSpanForCalc timeSheet;
	
	private AttendanceTime breakTime;
//	
	private BreakClassification breakClassification;

	private BreakCategory breakClassification
	
	
}
