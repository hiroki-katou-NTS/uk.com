package nts.uk.ctx.at.shared.dom.workrule.specific;

/**
 * 総労働時間の上限制御設定
 * @author HoangNDH
 *
 */
public enum LimitControlOfTotalWorkingSet {
	// 上限制御しない
	NO_LIMIT_CONTROL(0),
	// 総労働時間を上限にする
	UP_TO_TOTAL_WORKING_TIME(1),
	// 総計算時間を上限にする
	UP_TO_TOTAL_CALC_TIME(2);
	public final int value;
	
	private LimitControlOfTotalWorkingSet(int val) {
		value = val;
	}
	
	/**
	 * 上限制御しないであるか判定する
	 * @return 上限制御しないである
	 */
	public boolean isNoLimitControl() {
		return NO_LIMIT_CONTROL.equals(this);
	}
	
	/**
	 * 総労働時間を上限にするであるか判定する
	 * @return　総労働時間の上限にするである
	 */
	public boolean isUpToTotalWorkingTime() {
		return UP_TO_TOTAL_WORKING_TIME.equals(this);
	}
	
	/**
	 * 総計算時間を上限にする
	 * @return 総計算時間を上限にするである
	 */
	public boolean isUpToTotalCalcTime() {
		return UP_TO_TOTAL_CALC_TIME.equals(this);
	}
}
