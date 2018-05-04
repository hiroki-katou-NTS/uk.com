package nts.uk.ctx.at.function.dom.alarm.extractionrange.month;

import lombok.Getter;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.ExtractionRangeBase;

/**
 * @author TruongQuocPhong
 * 抽出期間（月単位）
 */

@Getter
public class ExtractionPeriodMonth extends ExtractionRangeBase{
	
	/**開始日*/
	private StartMonth startMonth;
	
	/**終了日*/
	private EndMonth endMonth;
	
	public ExtractionPeriodMonth(String extractionId, int extractionRange, StartMonth startMonth, EndMonth endMonth) {
		super(extractionId, extractionRange);
		this.startMonth = startMonth;
		this.endMonth = endMonth;
	}
}
