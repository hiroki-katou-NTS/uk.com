package nts.uk.ctx.at.function.dom.alarm.extractionrange.daily;

import lombok.Getter;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.ExtractionRange;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.ExtractionRangeBase;

/**
 * @author thanhpv
 * 抽出期間（日単位）
 */

@Getter
public class ExtractionPeriodDaily extends ExtractionRangeBase{
	
	private int day;
	
	private boolean makeToDay;

	public ExtractionPeriodDaily(String extractionId, ExtractionRange extractionRange, int day, boolean makeToDay) {
		super(extractionId, extractionRange);
		this.day = day;
		this.makeToDay = makeToDay;
	}
	

	
	
	
}
