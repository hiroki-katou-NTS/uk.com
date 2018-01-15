package nts.uk.ctx.at.function.app.command.dailyperformanceformat;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nampt
 *
 */
@Data
@NoArgsConstructor
public class AddAuthorityDailyFormatCommand {
	
	private AddAuthorityMonthlyCommand authorityMonthlyCommand;

	private AddAuthorityDailyCommand authorityDailyCommand;
	
}
