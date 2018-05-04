package nts.uk.ctx.at.function.dom.alarm.extractionrange.month;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.ExtractionRangeBase;

/**
 * @author 
 * 抽出期間（月単位）
 */

@Getter
@Setter
@NoArgsConstructor
public class ExtractionPeriodMonth extends ExtractionRangeBase{
	
	/**終了日*/
	private StartMonth startMonth;
	
	/**開始日*/
	private EndMonth endMonth;
	
	public ExtractionPeriodMonth(String extractionId, int extractionRange, StartMonth startMonth, EndMonth endMonth) {
		super(extractionId, extractionRange);
		this.startMonth = startMonth;
		this.endMonth = endMonth;
	}
}
