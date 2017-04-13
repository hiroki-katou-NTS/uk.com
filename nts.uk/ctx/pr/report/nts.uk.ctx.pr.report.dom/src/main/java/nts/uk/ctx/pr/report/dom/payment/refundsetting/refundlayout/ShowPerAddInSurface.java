package nts.uk.ctx.pr.report.dom.payment.refundsetting.refundlayout;

import lombok.AllArgsConstructor;

/** 表面に会社住所を出力 */
@AllArgsConstructor
public enum ShowPerAddInSurface {
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
