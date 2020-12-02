package nts.uk.screen.at.app.command.kmk.kmk004.monthlyworktimesetwkp;

import java.util.List;

import lombok.Value;

/**
 * 
 * @author sonnlb
 *
 */
@Value
public class SaveMonthlyWorkTimeSetWkpCommand {
	// 職場別月単位労働時間（List）
	private List<MonthlyWorkTimeSetWkpCommand> workTimeSetWkps;
}
