package nts.uk.ctx.at.shared.dom.specialholiday.yearservice;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum LengthServiceYearAtr {
	
	/* 一律基準日 */
	UniformDate(0),
	
	/* 入社日を付与基準日とする */
	EMPGrantDate(1),
	
	/* 特別休暇付与基準日を付与基準日とする */
	SpecialLeaveDate(2);

	public final int value;
}
