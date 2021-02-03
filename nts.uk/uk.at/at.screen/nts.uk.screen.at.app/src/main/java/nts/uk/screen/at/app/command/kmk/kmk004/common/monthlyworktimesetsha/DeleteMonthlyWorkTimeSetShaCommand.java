package nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetsha;

import lombok.Value;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetcom.YearMonthPeriodCommand;

/**
 * 
 * @author sonnlb
 * 
 *         社員別月単位労働時間を削除する
 */
@Value
public class DeleteMonthlyWorkTimeSetShaCommand {
	// 社員ID
	private String empId;
	// 勤務区分
	private int laborAttr;
	// 年月期間
	private YearMonthPeriodCommand yearMonths;
}
