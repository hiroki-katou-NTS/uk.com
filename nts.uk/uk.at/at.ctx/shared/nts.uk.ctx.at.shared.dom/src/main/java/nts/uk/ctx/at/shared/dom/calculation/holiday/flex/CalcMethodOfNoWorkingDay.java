package nts.uk.ctx.at.shared.dom.calculation.holiday.flex;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CalcMethodOfNoWorkingDay {
	
	// フレックス時間を計算しない
	IS_NOT_CALC_FLEX_TIME(0, "フレックス時間を計算しない"),
	// フレックス時間を計算する
	IS_CALC_FLEX_TIME(1, "フレックス時間を計算する");
	
	// The value
	public final int value;
	
	// The calc method
	public final String method;
}
