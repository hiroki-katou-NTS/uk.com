package nts.uk.ctx.at.function.dom.alarm.extractionrange.daily;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.PreviousClassification;

@Getter
@Setter
public class NumberOfDays {

	/** Day*/
	// 日
	private int day;
	
	/** Make it the day*/
	// 当日とする
	private boolean makeToDay;
	
	//Specify number of days
	//日数指定
	private PreviousClassification previousClassification;
	
}
