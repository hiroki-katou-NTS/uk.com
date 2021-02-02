package nts.uk.screen.at.app.command.kmk.kmk004.j;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetsha.MonthlyWorkTimeSetShaCommand;

/**
 * 
 * @author sonnlb
 *
 */
@AllArgsConstructor
@Data
public class UpdateFlexMonthlyWorkTimeSetShaCommand {
	// 社員別月単位労働時間（List）
	private List<MonthlyWorkTimeSetShaCommand> workTimeSetShas;

	private int year;

	private String sId;
}
