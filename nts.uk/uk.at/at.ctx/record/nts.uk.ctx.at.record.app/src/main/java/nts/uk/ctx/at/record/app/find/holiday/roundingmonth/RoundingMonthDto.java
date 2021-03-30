package nts.uk.ctx.at.record.app.find.holiday.roundingmonth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author phongtq
 *  月別実績の項目丸め設定
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoundingMonthDto {

	/**勤怠項目ID*/
	private int timeItemId;

	/** 丸め単位*/
	public int unit;
	
	/** 端数処理 */
	public int rounding;
}
