package nts.uk.screen.at.app.dailyperformance.correction.datadialog.classification;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@SuppressWarnings("unused")
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
	public final int code;

	/** The name id. */
	public final String name;

	private final static AutomaticCalcAfterHours[] values = AutomaticCalcAfterHours.values();
}
