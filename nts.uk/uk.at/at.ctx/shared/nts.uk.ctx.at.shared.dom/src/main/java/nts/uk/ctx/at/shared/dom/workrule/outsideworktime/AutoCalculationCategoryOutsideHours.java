package nts.uk.ctx.at.shared.dom.workrule.outsideworktime;

import lombok.AllArgsConstructor;

/**
 * 時間外の自動計算設定
 * @author keisuke_hoshina
 *
 */
@AllArgsConstructor
public enum AutoCalculationCategoryOutsideHours {
	CalculateEmbossing(1),
	ApplyOrManuallyEnter(2),
	SelectTimeRecorder(3);
	
	public final int value;
	/**
	 * 打刻から計算するであるか判定する
	 * @return　打刻から計算する
	 */
	public boolean isCalculateEmbossing() {
		return CalculateEmbossing.equals(this);
	}
	
	/**
	 * 申請または手入力であるか判定する
	 * @return　申請または手入力である
	 */
	public boolean isApplyOrManuallyEnter() {
		return ApplyOrManuallyEnter.equals(this);
	}
	
	
	/**
	 * タイムレコーダーで選択するであるか判定する
	 * @return　タイムレコーダーで選択する
	 */
	public boolean isSelectTimeRecorder() {
		return SelectTimeRecorder.equals(this);
	}
}
