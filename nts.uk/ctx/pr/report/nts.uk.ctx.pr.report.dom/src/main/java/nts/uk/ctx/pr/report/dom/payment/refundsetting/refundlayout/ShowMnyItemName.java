package nts.uk.ctx.pr.report.dom.payment.refundsetting.refundlayout;

import lombok.AllArgsConstructor;

/** 金額項目名も表示しない */
@AllArgsConstructor
public enum ShowMnyItemName {
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
