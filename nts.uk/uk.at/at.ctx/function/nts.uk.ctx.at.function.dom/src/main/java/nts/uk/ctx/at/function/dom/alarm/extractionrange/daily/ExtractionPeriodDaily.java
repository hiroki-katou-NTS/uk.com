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
	
	/**日数指定*/
	private NumberOfDays NumberOfDays;
	
	/**開始日の指定方法*/
	private SpecifyStartDate specifyStartDate;
	
	/**終了日の指定方法*/
	private SpecifyEndDate specifyEndDate;

	public ExtractionPeriodDaily(String extractionId, ExtractionRange extractionRange, NumberOfDays numberOfDays,
			SpecifyStartDate specifyStartDate, SpecifyEndDate specifyEndDate) {
		super(extractionId, extractionRange);
		NumberOfDays = numberOfDays;
		this.specifyStartDate = specifyStartDate;
		this.specifyEndDate = specifyEndDate;
	}
}
