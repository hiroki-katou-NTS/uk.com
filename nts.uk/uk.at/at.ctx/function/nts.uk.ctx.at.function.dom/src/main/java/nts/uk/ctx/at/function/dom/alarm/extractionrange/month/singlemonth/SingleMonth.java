package nts.uk.ctx.at.function.dom.alarm.extractionrange.month.singlemonth;

import lombok.Getter;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.PreviousClassification;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.EndSpecify;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.StartSpecify;
import nts.uk.ctx.at.function.dom.alarmworkplace.RangeToExtract;

/**
 * 単月
 * @author phongtq
 *
 */
@Getter
public class SingleMonth implements RangeToExtract {

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
