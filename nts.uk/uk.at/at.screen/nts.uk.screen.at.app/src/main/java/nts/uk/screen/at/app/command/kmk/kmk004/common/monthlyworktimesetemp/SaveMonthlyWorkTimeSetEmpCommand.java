package nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetemp;

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
public class SaveMonthlyWorkTimeSetEmpCommand {
	// 雇用別月単位労働時間（List）
	private List<MonthlyWorkTimeSetEmpCommand> workTimeSetEmps;
}
