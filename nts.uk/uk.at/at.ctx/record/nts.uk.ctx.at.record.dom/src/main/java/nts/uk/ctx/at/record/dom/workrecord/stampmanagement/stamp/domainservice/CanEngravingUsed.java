package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import lombok.AllArgsConstructor;

/**
 * 打刻利用可否
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻設定.打刻入力の機能設定.打刻機能が利用できるか.打刻利用可否
 * 
 * @author chungnt
 *
 */

@AllArgsConstructor
public enum CanEngravingUsed {

	// 0 利用できる
	AVAILABLE(0, "利用できる"),

	// 1 打刻オプション未購入
	NOT_PURCHASED_STAMPING_OPTION(1, "打刻オプション未購入"),

	// 2 打刻機能利用不可
	ENGTAVING_FUNCTION_CANNOT_USED(2, "打刻機能利用不可"),

	// 3 打刻カード未登録
	UNREGISTERED_STAMP_CARD(3, "打刻カード未登録");

	public final int value;

	public final String name;

	/** The Constant values. */
	private final static CanEngravingUsed[] values = CanEngravingUsed.values();

	public static CanEngravingUsed valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (CanEngravingUsed val : CanEngravingUsed.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
