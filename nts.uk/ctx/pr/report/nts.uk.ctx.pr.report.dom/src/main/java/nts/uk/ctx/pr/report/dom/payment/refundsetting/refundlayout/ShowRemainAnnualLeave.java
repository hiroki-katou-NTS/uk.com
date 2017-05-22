package nts.uk.ctx.pr.report.dom.payment.refundsetting.refundlayout;

import lombok.AllArgsConstructor;

/** 年休残数出力 */
@AllArgsConstructor
public enum ShowRemainAnnualLeave {
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
