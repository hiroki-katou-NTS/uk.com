package nts.uk.ctx.at.function.dom.alarm.extractionrange.month.amonth;

import lombok.Getter;
import lombok.Setter;
/**
 * 単年
 * @author phongtq
 *
 */
@Getter
@Setter
public class AMonth {

	/** 年 */
	private int year;
	
	/** 本年度とする */
	private boolean toBeThisYear;

	public AMonth(int year, boolean toBeThisYear) {
		super();
		this.year = year;
		this.toBeThisYear = toBeThisYear;
	}
	
	
}
