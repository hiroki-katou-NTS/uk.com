package nts.uk.ctx.at.shared.app.command.statutory.worktime.monthlyworkinghours.monthlyworktimesetwkp;

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
