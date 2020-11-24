package nts.uk.ctx.at.function.dom.alarmworkplace.month;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.MonthNo;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.SpecifyEndMonth;

import java.util.Optional;

/**
 * 終了月
 */
@Getter
public class EndMonth {
	
	/** 終了月の指定方法 */
	private SpecifyEndMonth specifyEndMonth;
	
	/** 月数指定 */
	private Optional<MonthNo> endMonthNo = Optional.empty();

	public EndMonth(int specifyEndMonth, Optional<MonthNo> endMonthNo) {
		this.specifyEndMonth = EnumAdaptor.valueOf(specifyEndMonth, SpecifyEndMonth.class);
		this.endMonthNo = endMonthNo;
	}
	public boolean isSpecifyPeriod() {
		return this.specifyEndMonth == SpecifyEndMonth.SPECIFY_PERIOD_START_MONTH;
	}
	public boolean isSpecifyClose() {
		return this.specifyEndMonth == SpecifyEndMonth.SPECIFY_CLOSE_END_MONTH;
	}
	
}
