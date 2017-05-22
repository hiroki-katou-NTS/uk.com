package nts.uk.ctx.pr.report.dom.payment.refundsetting.refundlayout;

import lombok.AllArgsConstructor;

/** 印刷タイプ  */
@AllArgsConstructor
public enum PrintType {
	/**
	 * 0.はがき
	 */
	POSTCARD(0),
	/**
	 * 1.はがき以外
	 */
	NOT_POSTCARD(1);

	public final int value;
}
