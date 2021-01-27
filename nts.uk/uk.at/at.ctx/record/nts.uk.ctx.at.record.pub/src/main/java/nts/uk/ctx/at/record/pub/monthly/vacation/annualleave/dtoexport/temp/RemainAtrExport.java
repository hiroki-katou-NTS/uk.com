package nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.dtoexport.temp;
/**
 * 残数分類
 * @author do_dt
 *
 */

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RemainAtrExport {
	/**
	 * 単一
	 */
	SINGLE(0),
	/**
	 * 複合
	 */
	COMPOSITE(1);
	public final Integer value;
}
