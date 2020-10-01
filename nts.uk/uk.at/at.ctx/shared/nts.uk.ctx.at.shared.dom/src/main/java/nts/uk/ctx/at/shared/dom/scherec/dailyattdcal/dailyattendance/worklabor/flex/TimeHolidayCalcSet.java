package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worklabor.flex;

import lombok.AllArgsConstructor;

/**
 * フレックスでの時間代休時の計算設定
 * @author HoangNDH
 *
 */
@AllArgsConstructor
public enum TimeHolidayCalcSet {
	// 使用した時間全てを所定時間から控除する
	DEDUCT_FROM_REFER_TIME(0, "使用した時間全てを所定時間から控除する"),
	// 所定時間から控除しない
	NO_DEDUCT_FROM_REFER_TIME(1, "所定時間から控除しない"),
	// 遅刻・早退・外出の時間分だけ所定時間から除く
	REMOVE_FROM_REFER_TIME(2, "遅刻・早退・外出の時間分だけ所定時間から除く");
	
	// The value
	public final int value;
	
	// The time holiday calc set
	public final String calcSet;
}
