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
	private PreviousClassification strPreviousClassification;
		
	/** Day*/
	// 日
	private int day;
	
	/** Make it the day*/
	// 当日とする
	private boolean makeToDay;

	public Days(PreviousClassification strPreviousClassification, int day, boolean makeToDay) {
		super();
		this.strPreviousClassification = strPreviousClassification;
		this.day = day;
		this.makeToDay = makeToDay;
	}
}
