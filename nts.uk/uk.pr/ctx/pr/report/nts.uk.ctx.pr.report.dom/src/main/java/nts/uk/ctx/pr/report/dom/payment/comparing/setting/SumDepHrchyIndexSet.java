package nts.uk.ctx.pr.report.dom.payment.comparing.setting;

import lombok.AllArgsConstructor;

/**
 * 部門階層累計
 */
@AllArgsConstructor
public enum SumDepHrchyIndexSet {

	/**
	 * 0.チェックしない
	 */
	NOT_INDEX_SET(0),

	/**
	 * 1.チェックする
	 */
	INDEX_SET(1);

	public final int value;
}