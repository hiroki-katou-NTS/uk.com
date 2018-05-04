package nts.uk.ctx.at.function.dom.alarm.extractionrange.month.singlemonth;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.PreviousClassification;

/**
 * 単月
 * @author phongtq
 *
 */
@Getter
@Setter
public class SingleMonth {

	/** 前・先区分  */
	private PreviousClassification monthPrevious;

	/** 月数 */
	private int monthNo;

	/** 当月とする */
	private boolean curentMonth;

	public SingleMonth(PreviousClassification monthPrevious, int monthNo, boolean curentMonth) {
		super();
		this.monthPrevious = monthPrevious;
		this.monthNo = monthNo;
		this.curentMonth = curentMonth;
	}
}
