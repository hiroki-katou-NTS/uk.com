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
	
	
	private NumberOfDays NumberOfDays;
	
	private SpecifyStartDate startDate;
	
	private SpecifyEndDate endDate;

	public ExtractionPeriodDaily(String extractionId, ExtractionRange extractionRange, NumberOfDays numberOfDays,
			SpecifyStartDate startDate, SpecifyEndDate endDate) {
		super(extractionId, extractionRange);
		NumberOfDays = numberOfDays;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	
	
	
}
