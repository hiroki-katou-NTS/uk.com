package nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetwkp;

import lombok.Value;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetcom.YearMonthPeriodCommand;

/**
 * 
 * @author sonnlb
 * 
 *         職場別月単位労働時間を削除する
 */
@Value
public class DeleteMonthlyWorkTimeSetWkpCommand {
	// 職場ID
	private String workplaceId;
	// 勤務区分
	private int laborAttr;
	// 年月期間
	private YearMonthPeriodCommand yearMonths;
}
