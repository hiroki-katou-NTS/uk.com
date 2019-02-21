package nts.uk.ctx.at.function.dom.alarm.checkcondition.annualholiday;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.MessageDisp;

/**
 * @author thanhnx
 * 年休使用義務チェック条件
 */
@Getter
@NoArgsConstructor
public class AlarmCheckConAgr{
	
	private boolean distByPeriod;
	
	private MessageDisp displayMessage;
	
	private YearlyUsageObDay usageObliDay;
	
	
	public AlarmCheckConAgr(boolean distByPeriod, String displayMessage, int usageObliDay) {
		super();
		this.distByPeriod = distByPeriod;
		this.displayMessage = new MessageDisp(displayMessage);
		this.usageObliDay = new YearlyUsageObDay(usageObliDay);
	}
}
