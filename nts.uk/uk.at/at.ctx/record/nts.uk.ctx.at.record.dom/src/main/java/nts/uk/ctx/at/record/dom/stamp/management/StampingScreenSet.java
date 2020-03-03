package nts.uk.ctx.at.record.dom.stamp.management;

import lombok.Getter;

/**
 * 打刻画面の表示設定
 * @author phongtq
 *
 */

public class StampingScreenSet {
	
	/** 打刻履歴表示方法 */
	@Getter
	private HistoryDisplayMethod historyDisplayMethod;
	
	/** 打刻画面のサーバー時刻補正間隔 */
	@Getter
	private CorrectionInterval correctionInterval;
	
	/** 打刻画面の日時の色設定 */
	@Getter
	private ColorSetting colorSetting;
	
	/** 打刻結果自動閉じる時間 */
	@Getter
	private ResultDisplayTime resultDisplayTime;

	public StampingScreenSet(HistoryDisplayMethod historyDisplayMethod, CorrectionInterval correctionInterval,
			ColorSetting colorSetting, ResultDisplayTime resultDisplayTime) {
		this.historyDisplayMethod = historyDisplayMethod;
		this.correctionInterval = correctionInterval;
		this.colorSetting = colorSetting;
		this.resultDisplayTime = resultDisplayTime;
	}
}
