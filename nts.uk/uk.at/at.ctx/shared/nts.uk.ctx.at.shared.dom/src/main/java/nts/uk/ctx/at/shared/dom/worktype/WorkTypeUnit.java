/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktype;

import lombok.AllArgsConstructor;

/**
 * The Enum WorkTypeUnit.
 */
@AllArgsConstructor
// 勤務の単位
public enum WorkTypeUnit {

	/** The One day. */
	// 1日
	OneDay(0, "Enum_WorkTypeUnit_OneDay"),

	/** The Monring and afternoon. */
	// 午前と午後
	MonringAndAfternoon(1, "Enum_WorkTypeUnit_MonringAndAfternoon");

	/** The value. */
	public final int value;
	public final String nameId;
	
	/**
	 * 1日であるか判定する
	 * @return　1日である
	 */
	public boolean isOneDay() {
		return OneDay.equals(this);
	}
	
	/**
	 * 午前と午後であるか判定する
	 * @return 午前と午後である
	 */
	public boolean isMonringAndAfternoon() {
		return MonringAndAfternoon.equals(this);
	}
}
