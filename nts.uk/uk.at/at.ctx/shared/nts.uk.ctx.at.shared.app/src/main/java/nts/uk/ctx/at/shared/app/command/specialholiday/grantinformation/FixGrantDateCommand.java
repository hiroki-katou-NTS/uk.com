package nts.uk.ctx.at.shared.app.command.specialholiday.grantinformation;

import lombok.Value;

@Value
public class FixGrantDateCommand {
	
	/** 	付与日数 */
	private int grantDays;
	
	/** 	期限 */
	private GrantDeadlineCommand grantPeriodic;
	
	/** 	付与月日 */
	private Integer grantMonthDay;
}
