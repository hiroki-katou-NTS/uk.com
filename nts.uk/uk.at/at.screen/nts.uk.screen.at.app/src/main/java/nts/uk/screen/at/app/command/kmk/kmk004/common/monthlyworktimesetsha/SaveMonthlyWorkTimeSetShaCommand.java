package nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetsha;

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
public class SaveMonthlyWorkTimeSetShaCommand {
	// 社員別月単位労働時間（List）
	private List<MonthlyWorkTimeSetShaCommand> workTimeSetShas;
}
