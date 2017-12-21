package nts.uk.ctx.at.function.dom.alarm.extractionrange.daily;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.ExtractionRange;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.ExtractionRangeAbs;

/**
 * @author thanhpv
 * 抽出期間（日単位）
 */

@Getter
@Setter
public class ExtractionPeriodDaily extends ExtractionRangeAbs{
	
	/** Day*/
	// 日
	private int day;
	
	/** Make it the day*/
	// 当日とする
	private boolean makeToDay;

	public ExtractionPeriodDaily(String extractionId, ExtractionRange extractionRange, int day, boolean makeToDay) {
		super(extractionId, extractionRange);
		this.day = day;
		this.makeToDay = makeToDay;
	}
	
	
}
