package nts.uk.ctx.pr.report.dom.payment.refundsetting.refundlayout;

import lombok.AllArgsConstructor;

/** 支払枠出力名称指定  */
@AllArgsConstructor
public enum PaymentCellNameCatalog {
	/**
	 * 1.個人情報より取得する
	 */
	FROM_PERSONAL(1),
	/**
	 * 2.項目名より取得する
	 */
	FROM_ITEM(2);

	public final int value;
}
