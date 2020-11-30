package nts.uk.ctx.at.shared.app.command.statutory.worktime.monthlyworkinghours;

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
