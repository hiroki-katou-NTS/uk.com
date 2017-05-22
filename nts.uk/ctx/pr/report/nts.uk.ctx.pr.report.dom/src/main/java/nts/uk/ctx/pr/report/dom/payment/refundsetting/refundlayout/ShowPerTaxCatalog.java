package nts.uk.ctx.pr.report.dom.payment.refundsetting.refundlayout;

import lombok.AllArgsConstructor;

/** 所得税区分出力 */
@AllArgsConstructor
public enum ShowPerTaxCatalog {
	/**
	 * 0.表示する
	 */
	DISPLAY(0),
	/**
	 * 1.表示しない
	 */
	NOT_DISPLAY(1);

	public final int value;
}
