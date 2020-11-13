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

	/**開始日の指定方法*/
	private StartSpecify startSpecify;
	
	/**Closing Date*/
	// 締め日指定
	private Optional<Month> strMonth = Optional.empty();
	
	/**Specify number of days*/
	// 日数指定
	private Optional<Days> strDays = Optional.empty();

	public StartDate(int startSpecify) {
		this.startSpecify = EnumAdaptor.valueOf(startSpecify, StartSpecify.class);
	}
	
	public void setStartDay(PreviousClassification dayPrevious, int day, boolean makeToDay) {
		this.strDays = Optional.of(new Days(dayPrevious, day, makeToDay));
	}
	
	public void setStartMonth(PreviousClassification monthPrevious, int month, boolean currentMonth) {
		this.strMonth = Optional.of(new Month(monthPrevious, month, currentMonth));
	}
}
