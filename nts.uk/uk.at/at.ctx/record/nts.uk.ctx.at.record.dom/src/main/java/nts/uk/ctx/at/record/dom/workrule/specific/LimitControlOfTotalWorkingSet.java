package nts.uk.ctx.at.record.dom.workrule.specific;

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
}
