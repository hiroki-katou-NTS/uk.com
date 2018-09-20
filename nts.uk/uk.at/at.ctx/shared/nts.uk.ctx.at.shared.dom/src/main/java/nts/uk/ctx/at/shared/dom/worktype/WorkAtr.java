package nts.uk.ctx.at.shared.dom.worktype;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum WorkAtr {	
	// 1日
	OneDay(0),

	// 午前
	Monring(1),
	
	// 午後
	Afternoon(2);

	/** The value. */
	public final int value;

	/**
	 * 1日であるか判定する
	 * @return　1日である
	 */
	public boolean isOneDay() {
		return this.equals(OneDay);
	}
	
	/**
	 * 午後であるか判定する
	 * @return 午後である
	 */
	public boolean isAfterNoon() {
		return this.equals(Afternoon);
	}
	
	/**
	 * 午後であるか判定する
	 * @return 午後である
	 */
	public boolean isMorning() {
		return this.equals(Monring);
	}
}
