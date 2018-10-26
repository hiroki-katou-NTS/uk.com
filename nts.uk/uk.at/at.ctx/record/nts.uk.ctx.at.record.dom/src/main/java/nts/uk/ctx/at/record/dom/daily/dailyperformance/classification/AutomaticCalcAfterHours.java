package nts.uk.ctx.at.record.dom.daily.dailyperformance.classification;

import lombok.AllArgsConstructor;

/**
 * @author thanhnx
 * 時間外の自動計算区分
 */
@AllArgsConstructor
public enum AutomaticCalcAfterHours {
	
	/** The applymanuallyenter. */
	// 申請または手入力
	APPLYMANUALLYENTER(0, "申請または手入力"),
	
	/** The timerecorder. */
	// タイムレコーダーで選択
	TIMERECORDER(1, "タイムレコーダーで選択"),
	
	/** The calculatemboss. */
	// 打刻から計算する
	CALCULATEMBOSS(2, "打刻から計算する");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String description;

	private final static AutomaticCalcAfterHours[] values = AutomaticCalcAfterHours.values();
	
	public static AutomaticCalcAfterHours valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (AutomaticCalcAfterHours val : AutomaticCalcAfterHours.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
