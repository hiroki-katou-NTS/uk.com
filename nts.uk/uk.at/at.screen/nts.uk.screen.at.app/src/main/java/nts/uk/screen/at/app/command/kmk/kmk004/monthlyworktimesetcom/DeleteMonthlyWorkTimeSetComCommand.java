package nts.uk.screen.at.app.command.kmk.kmk004.monthlyworktimesetcom;

import lombok.Value;

/**
 * 
 * @author sonnlb
 * 
 *         会社別月単位労働時間を削除する
 */
@Value
public class DeleteMonthlyWorkTimeSetComCommand {
	// 勤務区分
	private int laborAttr;
	// 年月期間
	private int ym;
}
