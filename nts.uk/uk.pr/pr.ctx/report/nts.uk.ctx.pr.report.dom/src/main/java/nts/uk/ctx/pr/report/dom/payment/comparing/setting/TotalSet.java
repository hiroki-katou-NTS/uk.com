package nts.uk.ctx.pr.report.dom.payment.comparing.setting;

import lombok.AllArgsConstructor;

/**
 * 総合計
 */
@AllArgsConstructor
public enum TotalSet {

	/**
	 *0.チェックしない
	 */
	NOT_TOTAL_SET(0),

	/**
	 * 1.チェックする
	 */
	TOTAL_SET(1);

	public final int value;
}