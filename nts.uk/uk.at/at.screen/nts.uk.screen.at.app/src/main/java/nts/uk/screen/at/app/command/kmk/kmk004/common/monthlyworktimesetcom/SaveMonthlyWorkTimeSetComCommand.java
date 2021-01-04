package nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetcom;

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
public class SaveMonthlyWorkTimeSetComCommand {
	// 会社別月単位労働時間（List）
	private List<MonthlyWorkTimeSetComCommand> workTimeSetComs;
}
