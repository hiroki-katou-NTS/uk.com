package nts.uk.ctx.at.shared.app.command.statutory.worktime.monthlyworkinghours.monthlyworktimesetemp;

import lombok.Value;

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
	private int ym;
}
