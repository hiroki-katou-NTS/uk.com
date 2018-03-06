package nts.uk.ctx.at.record.dom.calculationattribute.enums;

import lombok.AllArgsConstructor;

/**
 * 
 * @author nampt
 * 時間外の自動計算区分
 *
 */
@AllArgsConstructor
public enum AutoCalOverTimeAttr {
	
	/* 打刻から計算する */
	CALCULATION_FROM_STAMP(0),
	/* 申請または手入力 */
	APPLY_OR_MANUAL_INPUT(1),
	/* タイムレコーダーで選択 */
	SELECT_TIME_RECORDER(2);
	
	public final int value;
	
	/**
	 * 打刻から計算するであるか判定する
	 * @return　打刻から計算する
	 */
	public boolean isCalculateEmbossing() {
		return CALCULATION_FROM_STAMP.equals(this);
	}
	
	/**
	 * 申請または手入力であるか判定する
	 * @return　申請または手入力である
	 */
	public boolean isApplyOrManuallyEnter() {
		return APPLY_OR_MANUAL_INPUT.equals(this);
	}
	
	
	/**
	 * タイムレコーダーで選択するであるか判定する
	 * @return　タイムレコーダーで選択する
	 */
	public boolean isSelectTimeRecorder() {
		return SELECT_TIME_RECORDER.equals(this);
	}

}
