package nts.uk.ctx.at.function.app.command.dailyperformanceformat;

import lombok.Data;

/**
 * @author anhdt
 *
 */
@Data
public class UpdateAuthorityDailySFormatCommand {
	private UpdateAuthoritySDailyCommand authorityDailyCommand;
	private UpdateAuthorityMonthlyCommand authorityMonthlyCommand;
}
