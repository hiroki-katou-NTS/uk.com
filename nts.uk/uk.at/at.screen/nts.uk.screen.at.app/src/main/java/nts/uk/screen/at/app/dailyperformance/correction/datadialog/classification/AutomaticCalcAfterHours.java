package nts.uk.screen.at.app.dailyperformance.correction.datadialog.classification;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@SuppressWarnings("unused")
public enum AutomaticCalcAfterHours {
	// タイムレコーダーで選択
	TIMERECORDER(0, "タイムレコーダーで選択"),

	/** The calculatemboss. */
	// 打刻から計算する
	CALCULATEMBOSS(1, "打刻から計算する"),

	/** The applymanuallyenter. */
	// 申請または手入力
	APPLYMANUALLYENTER(2, "申請または手入力");

	/** The value. */
	public final int code;

	/** The name id. */
	public final String name;

	private final static AutomaticCalcAfterHours[] values = AutomaticCalcAfterHours.values();
}
