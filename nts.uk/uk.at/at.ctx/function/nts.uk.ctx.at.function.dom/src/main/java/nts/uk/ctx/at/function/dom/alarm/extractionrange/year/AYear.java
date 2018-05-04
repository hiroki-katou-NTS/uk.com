package nts.uk.ctx.at.function.dom.alarm.extractionrange.year;

import lombok.Getter;

@Getter
public class AYear {
	/** 年 */
	private int year;
	
	/** 本年度とする */
	private boolean toBeThisYear;

	public AYear(int year, boolean toBeThisYear) {
		super();
		this.year = year;
		this.toBeThisYear = toBeThisYear;
	}
	
}
