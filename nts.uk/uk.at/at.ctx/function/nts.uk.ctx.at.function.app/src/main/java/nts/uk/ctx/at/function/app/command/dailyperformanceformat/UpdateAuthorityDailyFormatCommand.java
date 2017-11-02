package nts.uk.ctx.at.function.app.command.dailyperformanceformat;

import lombok.Data;

/**
 * @author nampt
 *
 */
@Data
public class UpdateAuthorityDailyFormatCommand {

	private UpdateAuthorityDailyCommand authorityDailyCommand;
	
	private UpdateAuthorityMonthlyCommand authorityMonthlyCommand;
	
}
