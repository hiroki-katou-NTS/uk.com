package nts.uk.ctx.pr.report.dom.payment.refundsetting.refundlayout;

import lombok.AllArgsConstructor;

/** 部門出力 */
@AllArgsConstructor
public enum ShowDepartment {
	/**
	 * 0.部門コードを出力する
	 */
	DISPLAY_CODE(0),
	/**
	 * 1.部門名称を出力する
	 */
	DISPLAY_NAME(1),
	/**
	 * 2.出力しない
	 */
	NOT_DISPLAY(2);

	public final int value;
}
