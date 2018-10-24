package nts.uk.ctx.at.record.app.find.holiday.roundingmonth;

import lombok.Data;

/**
 * @author phongtq
 *  月別実績の項目丸め設定
 */

@Data
public class RoundingMonthDto {

	/**勤怠項目ID*/
	private int timeItemId;

	/** 丸め単位*/
	public int unit;
	
	/** 端数処理 */
	public int rounding;
}
