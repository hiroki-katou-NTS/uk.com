package nts.uk.ctx.at.function.dom.alarm.extractionrange.month.mutilmonth;

import lombok.Getter;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.ExtractionRangeBase;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.StandardMonth;

@Getter
public class AverageMonth extends ExtractionRangeBase{
	
	/** 月前 */
	private StandardMonth month;
	
	public AverageMonth(String extractionId, int extractionRange, StandardMonth month) {
		super(extractionId, extractionRange);
		this.month = month;
	}
}
