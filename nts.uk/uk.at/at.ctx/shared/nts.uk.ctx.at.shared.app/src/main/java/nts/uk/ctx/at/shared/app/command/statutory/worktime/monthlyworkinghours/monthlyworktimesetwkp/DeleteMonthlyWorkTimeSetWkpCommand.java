package nts.uk.ctx.at.shared.app.command.statutory.worktime.monthlyworkinghours.monthlyworktimesetwkp;

import lombok.Value;

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
	private int ym;
}
