package nts.uk.screen.at.app.dailyperformance.correction.datadialog.classification;

import lombok.AllArgsConstructor;

/**
 * @author thanhnx
 * 時間外の自動計算区分
 */
@AllArgsConstructor
@SuppressWarnings("unused")
public enum AutomaticCalcCompact {
	/** The applymanuallyenter. */
	// 申請または手入力
	APPLYMANUALLYENTER(0, "申請または手入力"),
	
	/** The calculatemboss. */
	// 打刻から計算する
	CALCULATEMBOSS(2, "打刻から計算する");

	/** The value. */
	public final int code;

	/** The name id. */
	public final String name;

	private final static AutomaticCalcAfterHours[] values = AutomaticCalcAfterHours.values();
}
