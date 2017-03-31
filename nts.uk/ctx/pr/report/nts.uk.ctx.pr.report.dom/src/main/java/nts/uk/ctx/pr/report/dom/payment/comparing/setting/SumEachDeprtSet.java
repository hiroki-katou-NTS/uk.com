package nts.uk.ctx.pr.report.dom.payment.comparing.setting;

import lombok.AllArgsConstructor;

/**
 * 部門計
 */
@AllArgsConstructor
public enum SumEachDeprtSet {

	/**
	 *0.チェックしない
	 */
	NOT_TOTAL_SET_DEPRT(0),

	/**
	 * 1.チェックする
	 */
	TOTAL_SET_DEPRT(1);

	public final int value;
}