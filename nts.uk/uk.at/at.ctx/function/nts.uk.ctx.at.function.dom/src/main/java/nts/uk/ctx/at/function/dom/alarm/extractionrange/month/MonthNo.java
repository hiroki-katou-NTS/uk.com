package nts.uk.ctx.at.function.dom.alarm.extractionrange.month;

import lombok.Getter;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.PreviousClassification;
/**
 * 月数指定
 * @author phongtq
 *
 */
@Getter
public class MonthNo {
	/** 前・先区分  */
	private PreviousClassification monthPrevious;

	/** 月数 */
	private int monthNo;

	/** 当月とする */
	private boolean curentMonth;

	public MonthNo(PreviousClassification monthPrevious, int monthNo, boolean curentMonth) {
		super();
		this.monthPrevious = monthPrevious;
		this.monthNo = monthNo;
		this.curentMonth = curentMonth;
	}
}
