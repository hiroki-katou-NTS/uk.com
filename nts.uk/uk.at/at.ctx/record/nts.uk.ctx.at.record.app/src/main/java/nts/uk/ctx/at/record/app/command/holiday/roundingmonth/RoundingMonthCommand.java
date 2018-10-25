/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.holiday.roundingmonth;

import lombok.Value;

/**
 * The Class RoundingMonthCommand.
 */
@Value
public class RoundingMonthCommand {
	/**勤怠項目ID*/
	private int timeItemId;

	/** 丸め単位*/
	public int unit;
	
	/** 端数処理 */
	public int rounding;
}
