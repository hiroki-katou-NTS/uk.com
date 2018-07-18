package nts.uk.ctx.at.shared.app.command.specialholidaynew.grantcondition;

import lombok.Value;
import nts.uk.shr.com.time.calendar.MonthDay;

@Value
public class AgeStandardCommand {
	/** 年齢基準年区分 */
	private int ageCriteriaCls;
	
	/** 年齢基準日 */
	private MonthDay ageBaseDate;
}
