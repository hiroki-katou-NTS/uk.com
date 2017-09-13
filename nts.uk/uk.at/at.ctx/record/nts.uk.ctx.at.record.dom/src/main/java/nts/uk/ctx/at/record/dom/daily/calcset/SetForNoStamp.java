package nts.uk.ctx.at.record.dom.daily.calcset;

/**
 * 休憩未打刻時の休憩設定
 * @author keisuke_hoshina
 *
 */
public enum SetForNoStamp {
	ReferToWorkTimeMasterSet,
	NotReferNotCalcBreakTime;
	

	/**
	 * 就業時間帯マスタの休憩設定か判定する
	 * @return 就業時間帯マスタの休憩設定である。
	 */
	public boolean isReferToWorkTimeMasterSet() {
		return ReferToWorkTimeMasterSet.equals(this);
	}
}
