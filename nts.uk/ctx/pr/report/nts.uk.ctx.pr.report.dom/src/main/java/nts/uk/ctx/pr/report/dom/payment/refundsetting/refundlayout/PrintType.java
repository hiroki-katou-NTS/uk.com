package nts.uk.ctx.pr.report.dom.payment.refundsetting.refundlayout;

import lombok.AllArgsConstructor;

/** 印刷タイプ  */
@AllArgsConstructor
public enum PrintType {
	/**
	 * 1.はがき
	 */
	POSTCARD(1),
	/**
	 * 2.はがき以外
	 */
	NOT_POSTCARD(2);

	public final int value;
}
