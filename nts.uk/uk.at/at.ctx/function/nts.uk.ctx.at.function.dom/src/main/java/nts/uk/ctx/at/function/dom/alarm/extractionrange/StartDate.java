package nts.uk.ctx.at.function.dom.alarm.extractionrange;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.Month;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.Days;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.StartSpecify;

/**
 * @author thanhpv
 * 開始日
 */
@Getter
@Setter
public class StartDate {

	/** 開始日の指定方法 */
	private StartSpecify startSpecify;
	
	/** 締め日指定の月数 */
	private Optional<Month> startMonth = Optional.empty();
	
	/** 日数指定の日 */
	private Optional<Days> startDays = Optional.empty(); // Number of days specified

	public StartDate(int startSpecify) {
		this.startSpecify = EnumAdaptor.valueOf(startSpecify, StartSpecify.class);
	}
	
	public void setStartDays(PreviousClassification dayPrevious, int day, boolean makeToday) {
		this.startDays = Optional.of(new Days(dayPrevious, day, makeToday));
	}
	
	public void setStartMonth(PreviousClassification monthPrevious, int month, boolean currentMonth) {
		this.startMonth = Optional.of(new Month(monthPrevious, month, currentMonth));
	}

}
