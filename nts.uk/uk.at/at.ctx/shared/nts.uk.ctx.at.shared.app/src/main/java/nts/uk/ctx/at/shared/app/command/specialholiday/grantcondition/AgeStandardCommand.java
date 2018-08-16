package nts.uk.ctx.at.shared.app.command.specialholiday.grantcondition;

import lombok.Value;

@Value
public class AgeStandardCommand {
	/** 年齢基準年区分 */
	private int ageCriteriaCls;
	
	/** 年齢基準日 */
	private int ageBaseDate;
}
