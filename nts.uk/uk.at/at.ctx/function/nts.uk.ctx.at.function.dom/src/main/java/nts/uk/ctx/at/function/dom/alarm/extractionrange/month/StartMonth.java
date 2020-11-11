package nts.uk.ctx.at.function.dom.alarm.extractionrange.month;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.PreviousClassification;
import nts.uk.ctx.at.function.dom.alarmworkplace.ExtractionEndMonth;
import nts.uk.ctx.at.function.dom.alarmworkplace.ExtractionStartMonth;

/**
 * 抽出期間（月単位）
 * @author phongtq
 *
 */  
@Getter
public class StartMonth implements ExtractionStartMonth {

	/** 開始月の指定方法 */
	private SpecifyStartMonth specifyStartMonth;

	/** 月数指定 */
	private Optional<MonthNo> strMonthNo = Optional.empty();
	
	/** 固定月度 */
	private Optional<FixedMonthly> fixedMonthly = Optional.empty();
	
	public StartMonth(int specifyStartMonth) {
		this.specifyStartMonth = EnumAdaptor.valueOf(specifyStartMonth, SpecifyStartMonth.class);
	}
	
	public void setStartMonth(PreviousClassification monthPrevious, int strMonthNo, boolean currentMonth) {
		this.strMonthNo = Optional.of(new MonthNo(monthPrevious, strMonthNo, currentMonth));
	}
	
	public void setFixedMonth(YearSpecifiedType yearSpecifiedType, int designatedMonth) {
		this.fixedMonthly = Optional.of(new FixedMonthly(yearSpecifiedType, designatedMonth));
	}
	
	public boolean isFixedMomnth() {
		return this.specifyStartMonth == SpecifyStartMonth.SPECIFY_FIXED_MOON_DEGREE;
	}
	
	public boolean isDesignateMonth() {
		return this.specifyStartMonth == SpecifyStartMonth.DESIGNATE_CLOSE_START_MONTH;
	}
}
