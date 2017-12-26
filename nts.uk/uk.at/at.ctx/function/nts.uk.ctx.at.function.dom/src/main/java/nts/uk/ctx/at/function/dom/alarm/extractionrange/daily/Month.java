package nts.uk.ctx.at.function.dom.alarm.extractionrange.daily;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.PreviousClassification;

/**
 * @author thanhpv
 * 締め日指定
 */
@Getter
@Setter
public class Month {

	/**Specify number of days*/	
	/**日数指定*/
	private PreviousClassification previousClassification;
		
	/** Month*/
	// 月数: 締め日指定月数
	private int month;
	
	/** CURRENT_MONTH*/
	// 当日とする
	private boolean curentMonth;
	
	public Month(int previousClassification, int month, boolean curentMonth) {
		super();
		this.previousClassification = EnumAdaptor.valueOf(previousClassification, PreviousClassification.class);
		this.month = month;
		this.curentMonth = curentMonth;
	}
	
}
