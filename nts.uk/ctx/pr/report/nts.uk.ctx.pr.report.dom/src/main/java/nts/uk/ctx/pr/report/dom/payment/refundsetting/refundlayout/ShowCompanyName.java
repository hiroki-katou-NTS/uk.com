package nts.uk.ctx.pr.report.dom.payment.refundsetting.refundlayout;

import lombok.AllArgsConstructor;

/** 会社名出力 */
@AllArgsConstructor
public enum ShowCompanyName {
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
