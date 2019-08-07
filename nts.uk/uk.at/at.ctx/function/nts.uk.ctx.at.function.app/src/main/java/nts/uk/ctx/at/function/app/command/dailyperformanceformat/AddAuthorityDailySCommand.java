package nts.uk.ctx.at.function.app.command.dailyperformanceformat;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author anhdt
 *
 */
@Data
@NoArgsConstructor
public class AddAuthorityDailySCommand {
	private AddAuthorityDailyCommand authorityDailyCommand;
	private AddAuthorityMonthlyCommand authorityMonthlyCommand;
}
