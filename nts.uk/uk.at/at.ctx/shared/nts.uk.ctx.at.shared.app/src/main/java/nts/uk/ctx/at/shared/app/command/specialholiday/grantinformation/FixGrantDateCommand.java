package nts.uk.ctx.at.shared.app.command.specialholiday.grantinformation;

import lombok.Value;

@Value
public class FixGrantDateCommand {
	/** 周期*/
	private int interval;
	
	/** 固定付与日数 */
	private int grantDays;
}
