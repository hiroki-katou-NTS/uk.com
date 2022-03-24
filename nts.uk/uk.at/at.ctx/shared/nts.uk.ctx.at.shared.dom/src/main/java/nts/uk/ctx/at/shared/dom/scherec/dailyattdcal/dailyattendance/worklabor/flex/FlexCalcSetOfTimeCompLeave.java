package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worklabor.flex;

import lombok.AllArgsConstructor;

/**
 * フレックスでの時間代休時の計算設定
 * @author shuichi_ishida
 */
@AllArgsConstructor
public enum FlexCalcSetOfTimeCompLeave {
	/** 使用した時間全てを所定時間から控除する */
	DEDUCT_ON_COMPENSATORY_TIME(0, "使用した時間全てを所定時間から控除する"),
	/** 遅刻・早退・外出の時間分だけ所定時間から除く */
	DEDUCT_ON_LEAVEEARLY_LATE_OUTING_TIME_ONLY(1, "遅刻・早退・外出の時間分だけ所定時間から除く");
	
	// The value
	public final int value;
	
	// The time holiday calc set
	public final String calcSet;
}
