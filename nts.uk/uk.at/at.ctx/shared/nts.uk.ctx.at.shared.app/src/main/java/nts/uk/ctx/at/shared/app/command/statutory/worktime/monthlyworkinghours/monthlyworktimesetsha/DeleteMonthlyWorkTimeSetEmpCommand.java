package nts.uk.ctx.at.shared.app.command.statutory.worktime.monthlyworkinghours.monthlyworktimesetsha;

import lombok.Value;

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
	private int ym;
}
