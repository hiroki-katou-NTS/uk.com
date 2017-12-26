package nts.uk.ctx.at.function.dom.alarm.extractionrange.daily;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.PreviousClassification;

/**
 * @author thanhpv
 * 締め日指定
 */
@Getter
@Setter
public class ClosingDate {

	/**Specify number of days*/	
	/**日数指定*/
	private PreviousClassification previousClassification;
		
	/** Day*/
	// 日
	private int day;
	
	/** Make it the day*/
	// 当日とする
	private boolean makeToDay;

	public ClosingDate(PreviousClassification previousClassification, int day, boolean makeToDay) {
		super();
		this.previousClassification = previousClassification;
		this.day = day;
		this.makeToDay = makeToDay;
	}
	
	
	
}
