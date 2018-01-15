package nts.uk.ctx.at.shared.app.command.specialholiday;

import lombok.Data;

@Data
public class GrantSingleCommand {

	/** 特別休暇コード */
	private String specialHolidayCode;

	/** 種類 */
	private int grantDaySingleType;

	/** 固定付与日数 */
	private Integer fixNumberDays;

	/** 忌引とする */
	private int makeInvitation;

	/** 休日除外区分 */
	private int holidayExclusionAtr;
}
