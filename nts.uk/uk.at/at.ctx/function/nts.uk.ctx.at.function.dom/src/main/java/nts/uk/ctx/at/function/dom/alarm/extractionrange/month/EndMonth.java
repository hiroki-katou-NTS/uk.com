package nts.uk.ctx.at.function.dom.alarm.extractionrange.month;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.PreviousClassification;
/**
 * 抽出期間（月単位）
 * @author phongtq
 *
 */
@Getter
@Setter
public class EndMonth {
	
	/** 終了月の指定方法 */
	private SpecifyEndMonth specifyEndMonth;
	
	/** 月数指定 */
	private Optional<MonthNo> endMonthNo = Optional.empty();
	
	/** 開始月からの抽出期間 */
	private ExtractFromStartMonth extractFromStartMonth;
	
	public EndMonth(int specifyEndMonth) {
		this.specifyEndMonth = EnumAdaptor.valueOf(specifyEndMonth, SpecifyEndMonth.class);
	}
	
	public void setEndMonthNo(PreviousClassification monthPrevious, int endMonthNo, boolean currentMonth) {
		this.endMonthNo = Optional.of(new MonthNo(monthPrevious, endMonthNo, currentMonth));
	}
}
