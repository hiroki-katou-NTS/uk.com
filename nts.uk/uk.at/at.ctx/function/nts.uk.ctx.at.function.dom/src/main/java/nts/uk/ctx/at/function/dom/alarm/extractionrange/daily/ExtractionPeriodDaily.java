package nts.uk.ctx.at.function.dom.alarm.extractionrange.daily;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.ExtractionRange;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.ExtractionRangeBase;

/**
 * @author thanhpv
 * 抽出期間（日単位）
 */

@Getter
@Setter
public class ExtractionPeriodDaily extends ExtractionRangeBase{
	
	
	private int NumberOfDays;
	
	private StartDate startDate;
	
	private EndDate endDate;

	public ExtractionPeriodDaily(String extractionId, ExtractionRange extractionRange, int numberOfDays,
			StartDate startDate, EndDate endDate) {
		super(extractionId, extractionRange);
		NumberOfDays = numberOfDays;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	
	
	
}
