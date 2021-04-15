package nts.uk.ctx.at.shared.app.command.specialholiday.grantinformation;

import lombok.Value;

@Value
public class PeriodGrantDateCommand {
	
	/** 期間 */
	private DatePeriodCommand period;
	
	/** 付与日数 */
	private int grantDays;
}
