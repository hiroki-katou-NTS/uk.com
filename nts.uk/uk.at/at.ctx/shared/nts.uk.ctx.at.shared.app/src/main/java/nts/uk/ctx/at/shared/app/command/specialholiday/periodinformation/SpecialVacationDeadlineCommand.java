package nts.uk.ctx.at.shared.app.command.specialholiday.periodinformation;

import lombok.Value;

@Value
public class SpecialVacationDeadlineCommand {
	/** 月数 */
	private int months;
	
	/** 年数 */
	private int years;
}
