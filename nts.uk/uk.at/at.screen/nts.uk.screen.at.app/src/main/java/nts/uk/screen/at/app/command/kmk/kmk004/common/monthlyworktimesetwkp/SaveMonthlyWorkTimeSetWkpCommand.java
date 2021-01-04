package nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetwkp;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * @author sonnlb
 *
 */
@AllArgsConstructor
@Data
public class SaveMonthlyWorkTimeSetWkpCommand {
	// 職場別月単位労働時間（List）
	private List<MonthlyWorkTimeSetWkpCommand> workTimeSetWkps;
}
