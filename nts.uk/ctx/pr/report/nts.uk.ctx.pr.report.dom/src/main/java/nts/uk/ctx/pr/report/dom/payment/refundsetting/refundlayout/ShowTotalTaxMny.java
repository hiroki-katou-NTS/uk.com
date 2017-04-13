package nts.uk.ctx.pr.report.dom.payment.refundsetting.refundlayout;

import lombok.AllArgsConstructor;

/** 課税対象累計出力 */
@AllArgsConstructor
public enum ShowTotalTaxMny {
	/**
	 * 1.表示する
	 */
	DISPLAY(1),
	/**
	 * 2.表示しない
	 */
	NOT_DISPLAY(2);

	public final int value;
}
