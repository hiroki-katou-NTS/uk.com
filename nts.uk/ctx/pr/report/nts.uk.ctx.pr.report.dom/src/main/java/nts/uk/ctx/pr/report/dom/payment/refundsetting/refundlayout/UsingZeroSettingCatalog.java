package nts.uk.ctx.pr.report.dom.payment.refundsetting.refundlayout;

import lombok.AllArgsConstructor;

/** ゼロ項目設定区分  */
@AllArgsConstructor
public enum UsingZeroSettingCatalog {
	/**
	 * 1.項目名の登録の設定を優先する
	 */
	SET_ITEM_NAME(1),
	/**
	 * 2.個別に設定する
	 */
	SET_INDIVIDUALLY(2);

	public final int value;
}
