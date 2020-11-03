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
public class Month {

	/** 前・先区分 */
	private PreviousClassification monthPrevious;

	/** 月数 */
	private int month;

	/** 当日とする */
	private boolean currentMonth;

	public Month(PreviousClassification monthPrevious, int month, boolean currentMonth) {
		super();
		this.monthPrevious = monthPrevious;
		this.month = month;
		this.currentMonth = currentMonth;
	}

}
