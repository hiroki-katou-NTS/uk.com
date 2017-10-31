package nts.uk.ctx.at.shared.app.command.specialholiday;

import lombok.Value;

@Value
public class GrantDateSetCommand {
	/* 付与日のID */
	private String specialHolidayCode;
	
	/* 付与日の数 */
	private int grantDateNo;
	
	/* 月数 */
	private int grantDateMonth;

	/* 年数 */
	private int grantDateYear;
}
