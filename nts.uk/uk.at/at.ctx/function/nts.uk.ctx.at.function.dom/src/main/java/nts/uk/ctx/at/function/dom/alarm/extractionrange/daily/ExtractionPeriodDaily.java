package nts.uk.ctx.at.function.dom.alarm.extractionrange.daily;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.EndDate;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.ExtractionRangeBase;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.StartDate;

/**
 * @author thanhpv
 * 抽出期間（日単位）
 */

@Getter
@Setter
@NoArgsConstructor
public class ExtractionPeriodDaily extends ExtractionRangeBase{
	
	/**終了日*/
	private StartDate startDate;
	
	/**開始日*/
	private EndDate endDate;
	
	public ExtractionPeriodDaily(String extractionId, int extractionRange, StartDate startDate, EndDate endDate) {
		super(extractionId, extractionRange);
		this.startDate = startDate;
		this.endDate = endDate;
	}
}
