package nts.uk.ctx.pr.report.dom.payment.refundsetting.refundlayout;

import lombok.AllArgsConstructor;

/** 金額項目名も表示しない */
@AllArgsConstructor
public enum ShowMnyItemName {
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
