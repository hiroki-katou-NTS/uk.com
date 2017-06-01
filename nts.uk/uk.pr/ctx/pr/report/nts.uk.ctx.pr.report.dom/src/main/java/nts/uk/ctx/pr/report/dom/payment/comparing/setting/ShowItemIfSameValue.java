package nts.uk.ctx.pr.report.dom.payment.comparing.setting;

import lombok.AllArgsConstructor;

/**
 * 差額なし項目表示区分
 */
@AllArgsConstructor
public enum ShowItemIfSameValue {

	/**
	 * 0.表示しない
	 */
	NOT_DISPLAY(0),

	/**
	 * 1.表示する
	 */
	DISPLAY(1);

	public final int value;
}
