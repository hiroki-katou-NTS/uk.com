package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class WithinPredeterminePremiumTimeSheet{
	private final TimeSpanForCalc span;
	
	public WithinPredeterminePremiumTimeSheet(TimeWithDayAttr start, TimeWithDayAttr end) {
		this.span = new TimeSpanForCalc(start, end);
	}
}
