package nts.uk.ctx.at.function.dom.alarm.extractionrange.month.mutilmonth;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.ExtractionRangeBase;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.StandardMonth;

/**
 * 複数月平均基準月
 * @author phongtq
 *
 */

@Getter
public class AverageMonth extends ExtractionRangeBase{
	
	/** 基準月指定 */
	private StandardMonth strMonth;
	
	public AverageMonth(String extractionId, int extractionRange, int strMonth) {
		super(extractionId, extractionRange);
		this.strMonth = EnumAdaptor.valueOf(strMonth, StandardMonth.class);
	}
}
