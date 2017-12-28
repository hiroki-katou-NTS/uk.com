package nts.uk.ctx.at.function.dom.alarm.extractionrange;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.Days;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.EndSpecify;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.Month;

/**
 * @author thanhpv
 * 終了日
 */

@Getter
@Setter
@NoArgsConstructor
public class EndDate {

	/**終了日の指定方法*/
	private EndSpecify endSpecify;
	
	/**Closing Date*/
	// 締め日指定
	private Optional<Month> endMonth = Optional.empty();
	
	/**Specify number of days*/
	// 日数指定
	private Optional<Days> endDays = Optional.empty();

	public EndDate(int endSpecify) {
		this.endSpecify = EnumAdaptor.valueOf(endSpecify, EndSpecify.class);
	}
	
	public void setEndDay(PreviousClassification monthPrevious, int day, boolean makeToDay) {
		this.endDays = Optional.of(new Days(monthPrevious, day, makeToDay));
	}
	
	public void setEndMonth(PreviousClassification monthPrevious, int month, boolean currentMonth) {
		this.endMonth = Optional.of(new Month(monthPrevious, month, currentMonth));
	}
}
