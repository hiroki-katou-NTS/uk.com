package nts.uk.screen.at.app.command.kmk.kmk004.monthlyworktimesetemp;

import java.util.List;

import lombok.Value;

/**
 * 
 * @author sonnlb
 *
 */
@Value
public class SaveMonthlyWorkTimeSetEmpCommand {
	// 雇用別月単位労働時間（List）
	private List<MonthlyWorkTimeSetEmpCommand> workTimeSetEmps;
}
