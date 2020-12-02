package nts.uk.screen.at.app.command.kmk.kmk004.monthlyworktimesetwkp;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * 
 * @author sonnlb
 *
 */
@Value
@AllArgsConstructor
public class SaveMonthlyWorkTimeSetWkpCommand {
	// 職場別月単位労働時間（List）
	private List<MonthlyWorkTimeSetWkpCommand> workTimeSetWkps;
}
