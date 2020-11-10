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
public class EndMonth implements ExtractionEndMonth,ExtractionStartMonth {
	
	/** 終了月の指定方法 */
	private SpecifyEndMonth specifyEndMonth;
	
	/** 開始月からの抽出期間 */
	private ExtractFromStartMonth extractFromStartMonth;
	
	/** 月数指定 */
	private Optional<MonthNo> endMonthNo = Optional.empty();
	

	
	public EndMonth(int specifyEndMonth, int extractFromStartMonth) {
		this.specifyEndMonth = EnumAdaptor.valueOf(specifyEndMonth, SpecifyEndMonth.class);
		this.extractFromStartMonth = EnumAdaptor.valueOf(extractFromStartMonth, ExtractFromStartMonth.class);
	}
	
	public void setEndMonthNo(PreviousClassification monthPrevious, int endMonthNo, boolean currentMonth) {
		this.endMonthNo = Optional.of(new MonthNo(monthPrevious, endMonthNo, currentMonth));
	}

	public EndMonth(SpecifyEndMonth specifyEndMonth, ExtractFromStartMonth extractFromStartMonth,
			MonthNo endMonthNo) {
		super();
		this.specifyEndMonth = specifyEndMonth;
		this.extractFromStartMonth = extractFromStartMonth;
		this.endMonthNo = Optional.ofNullable(endMonthNo);
	}
	
	public boolean isSpecifyPeriod() {
		return this.specifyEndMonth == SpecifyEndMonth.SPECIFY_PERIOD_START_MONTH;
	}
	public boolean isSpecifyClose() {
		return this.specifyEndMonth == SpecifyEndMonth.SPECIFY_CLOSE_END_MONTH;
	}
	
}
