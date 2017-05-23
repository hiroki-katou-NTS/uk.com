package nts.uk.ctx.pr.report.dom.payment.comparing.setting;

import lombok.AllArgsConstructor;

/**
 * 明細出力区分
 */
@AllArgsConstructor
public enum ShowPayment {

	/**
	 * 0.しない
	 */
	NOT_SHOW(0),

	/**
	 * 1.する
	 */
	SHOW(1);

	public final int value;
}
