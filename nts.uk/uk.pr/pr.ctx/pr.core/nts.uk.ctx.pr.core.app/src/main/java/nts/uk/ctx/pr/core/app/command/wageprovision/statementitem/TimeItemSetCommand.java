package nts.uk.ctx.pr.core.app.command.wageprovision.statementitem;

import lombok.Value;

/**
 * 
 * @author thanh.tq 勤怠項目設定
 *
 */
@Value
public class TimeItemSetCommand {

	/**
	 * 平均賃金区分
	 */
	private Integer averageWageAtr;

	/**
	 * 年間所定労働日数区分
	 */
	private Integer workingDaysPerYear;

	/**
	 * 時間回数区分
	 */
	private int timeCountAtr;

	/**
	 * 備考
	 */
	private String note;

}
