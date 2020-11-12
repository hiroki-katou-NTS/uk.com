package nts.uk.ctx.at.function.dom.alarm.extractionrange.daily;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.PreviousClassification;

/**
 * @author thanhpv
 * 日数指定
 */
@Getter
@Setter
public class Days {

	//Previous / previous classification
	/**前・先区分*/
	private PreviousClassification dayPrevious;
		
	/** Day*/
	// 日
	private Day day;
	
	/** Make it the day*/
	// 当日とする
	private boolean makeToDay;

	public Days(PreviousClassification dayPrevious, int day, boolean makeToDay) {
		this.dayPrevious = dayPrevious;
		this.day = new Day(day);
		this.makeToDay = makeToDay;
	}
}
