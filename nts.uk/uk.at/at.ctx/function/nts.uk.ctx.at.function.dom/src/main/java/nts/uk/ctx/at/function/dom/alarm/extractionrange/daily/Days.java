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

	/** 前・先区分 */
	private PreviousClassification dayPrevious;

	/** 日 */
	private Day day;

	/** 当日とする */
	private boolean today;

	public Days(PreviousClassification dayPrevious, int day, boolean today) {
		this.dayPrevious = dayPrevious;
		this.day = new Day(day);
		this.today = today;
	}

}
