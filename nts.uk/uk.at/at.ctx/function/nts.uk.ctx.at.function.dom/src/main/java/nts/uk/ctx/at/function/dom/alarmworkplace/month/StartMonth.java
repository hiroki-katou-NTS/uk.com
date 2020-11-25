package nts.uk.ctx.at.function.dom.alarmworkplace.month;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.MonthNo;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.SpecifyStartMonth;

import java.util.Optional;

/**
 * 開始月
 */  
@Getter
public class StartMonth {

	/** 開始月の指定方法 */
	private SpecifyStartMonth specifyStartMonth;

	/** 月数指定 */
	private Optional<MonthNo> strMonthNo = Optional.empty();
	
	public StartMonth(int specifyStartMonth,Optional<MonthNo> strMonthNo) {
		this.specifyStartMonth = EnumAdaptor.valueOf(specifyStartMonth, SpecifyStartMonth.class);
		this.strMonthNo = strMonthNo;
	}
	
	public boolean isFixedMomnth() {
		return this.specifyStartMonth == SpecifyStartMonth.SPECIFY_FIXED_MOON_DEGREE;
	}
	
	public boolean isDesignateMonth() {
		return this.specifyStartMonth == SpecifyStartMonth.DESIGNATE_CLOSE_START_MONTH;
	}
}
