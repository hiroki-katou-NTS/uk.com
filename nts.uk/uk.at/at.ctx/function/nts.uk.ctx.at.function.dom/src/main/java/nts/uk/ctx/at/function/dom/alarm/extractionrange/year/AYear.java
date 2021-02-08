package nts.uk.ctx.at.function.dom.alarm.extractionrange.year;

import lombok.Getter;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.ExtractionRangeBase;

/**
 * Domain 単年
 */
@Getter
public class AYear extends  ExtractionRangeBase{
	/** 年 */
	private int year;
	
	/** 本年度とする */
	private boolean toBeThisYear;

	public AYear(String extractionId, int extractionRange, int year, boolean toBeThisYear) {
		super(extractionId, extractionRange);
		this.year = year;
		this.toBeThisYear = toBeThisYear;
	}
	
}
