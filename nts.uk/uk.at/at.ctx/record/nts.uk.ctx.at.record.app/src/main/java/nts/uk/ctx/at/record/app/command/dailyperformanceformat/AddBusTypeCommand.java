package nts.uk.ctx.at.record.app.command.dailyperformanceformat;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nampt
 *
 */
@Data
@NoArgsConstructor
public class AddBusTypeCommand {
	
	private AddBusinessTypeDailyCommand busTypeDailyCommand;
	
	private AddBusinessTypeMonthlyCommand busTypeMonthlyCommand;

}
