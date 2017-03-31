package nts.uk.ctx.pr.report.dom.payment.comparing.setting;

import lombok.AllArgsConstructor;

/**
 * 階層インデックス3 チェックのついている階層の数値を保持
 */
@AllArgsConstructor
public enum HrchyIndex3 {

	/**
	 * 0.設定なし
	 */
	NOT_SETTING(0),

	/**
	 * 1.設定
	 */
	SETTING(1);

	public final int value;
}