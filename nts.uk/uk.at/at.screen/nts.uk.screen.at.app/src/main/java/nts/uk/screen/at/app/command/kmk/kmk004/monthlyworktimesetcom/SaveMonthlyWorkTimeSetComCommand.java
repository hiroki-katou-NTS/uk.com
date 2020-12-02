package nts.uk.screen.at.app.command.kmk.kmk004.monthlyworktimesetcom;

import java.util.List;

import lombok.Value;

/**
 * 
 * @author sonnlb
 *
 */
@Value
public class SaveMonthlyWorkTimeSetComCommand {
	// 会社別月単位労働時間（List）
	private List<MonthlyWorkTimeSetComCommand> workTimeSetComs;
}
