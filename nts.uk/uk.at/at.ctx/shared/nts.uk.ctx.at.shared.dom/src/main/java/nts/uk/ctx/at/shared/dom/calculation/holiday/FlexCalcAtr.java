package nts.uk.ctx.at.shared.dom.calculation.holiday;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum FlexCalcAtr {

	/** フレックス時間を計算する*/
	WORKING_HOURS_DURING_HOLIDAYS(0),
	/** フレックス時間を計算しない*/
	WORKING_HOURS_DURING_WEEKDAY(1);

	public final int value;
}