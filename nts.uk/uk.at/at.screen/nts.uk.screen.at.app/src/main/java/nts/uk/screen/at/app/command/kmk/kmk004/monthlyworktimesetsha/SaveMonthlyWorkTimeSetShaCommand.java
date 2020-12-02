package nts.uk.screen.at.app.command.kmk.kmk004.monthlyworktimesetsha;

import java.util.List;

import lombok.Value;

/**
 * 
 * @author sonnlb
 *
 */
@Value
public class SaveMonthlyWorkTimeSetShaCommand {
	// 社員別月単位労働時間（List）
	private List<MonthlyWorkTimeSetShaCommand> workTimeSetShas;
}
