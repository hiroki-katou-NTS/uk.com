package nts.uk.ctx.at.function.app.command.dailyperformanceformat;

import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author anhdt
 *
 */
@Data
@NoArgsConstructor
public class AddBusinessTypeMobileCommand {
	private AddBusinessTypeMobileDailyCommand busTypeDailyCommand;
	private AddBusinessTypeSMonthlyCommand busTypeMonthlyCommand;
}
