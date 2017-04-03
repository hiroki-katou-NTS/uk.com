package nts.uk.ctx.pr.report.dom.payment.comparing.setting;

import lombok.AllArgsConstructor;

/**
 * 階層インデックス4 チェックのついている階層の数値を保持
 */
@AllArgsConstructor
public enum HrchyIndex4 {

	/**
	 * 0.設定なし
	 */
	CLASS0(0),
	/**
	 * 1..9.設定
	 */
	CLASS1(1), CLASS2(2), CLASS3(3), CLASS4(4), CLASS5(5), CLASS6(6), CLASS7(7), CLASS8(8), CLASS9(9);

	public final int value;
}
