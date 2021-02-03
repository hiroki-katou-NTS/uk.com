package nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetemp;

import lombok.Value;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetcom.YearMonthPeriodCommand;

/**
 * 
 * @author sonnlb
 * 
 *         雇用別月単位労働時間を削除する
 */
@Value
public class DeleteMonthlyWorkTimeSetEmpCommand {
	// 雇用コード
	private String employmentCode;
	// 勤務区分
	private int laborAttr;
	// 年月期間
	private YearMonthPeriodCommand yearMonths;
}
