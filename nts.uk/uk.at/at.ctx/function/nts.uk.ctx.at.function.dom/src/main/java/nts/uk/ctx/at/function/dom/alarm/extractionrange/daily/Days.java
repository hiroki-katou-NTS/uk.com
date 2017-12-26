package nts.uk.ctx.at.function.dom.alarm.extractionrange.daily;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
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

	public Days(int strPreviousClassification, int day, boolean makeToDay) {
		super();
		this.strPreviousClassification = EnumAdaptor.valueOf(strPreviousClassification, PreviousClassification.class);
		this.day = day;
		this.makeToDay = makeToDay;
	}
}
