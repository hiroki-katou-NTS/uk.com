package nts.uk.ctx.at.function.dom.alarm.extractionrange.daily;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.EndDate;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.ExtractionRange;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.ExtractionRangeBase;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.StartDate;

/**
 * @author thanhpv
 * 抽出期間（日単位）
 */

@Getter
@Setter
public class ExtractionPeriodDaily extends ExtractionRangeBase{
	
	/**日数指定*/
	private EndDate endDate;
	
	/**開始日の指定方法*/
	private StartDate startDate;
	
	/**終了日の指定方法*/
	private SpecifyEndDate specifyEndDate;

	public ExtractionPeriodDaily(String extractionId, ExtractionRange extractionRange, EndDate endDate,
			StartDate startDate, SpecifyEndDate specifyEndDate) {
		super(extractionId, extractionRange);
		this.endDate = endDate;
		this.startDate = startDate;
		this.specifyEndDate = specifyEndDate;
	}
}
