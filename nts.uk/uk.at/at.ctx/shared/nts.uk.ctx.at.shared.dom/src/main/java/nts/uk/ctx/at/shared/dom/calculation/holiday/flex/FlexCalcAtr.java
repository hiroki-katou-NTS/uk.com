package nts.uk.ctx.at.shared.dom.calculation.holiday.flex;

import lombok.AllArgsConstructor;

/**
 * 
 * @author phongtq
 *非勤務日のフレックス時間計算方法
 */
@AllArgsConstructor
public enum FlexCalcAtr {

	/** 所定時間を半日分とする */
	PREDETERMINED_TIME_HALF_DAY(0),
	/** 所定時間を1日分とする */
	PREDETERMINED_TIME_ONE_DAY(1);
	public final int value;
}