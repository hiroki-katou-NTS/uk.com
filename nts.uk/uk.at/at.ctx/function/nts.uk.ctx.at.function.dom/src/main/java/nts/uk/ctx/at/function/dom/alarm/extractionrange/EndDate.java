package nts.uk.ctx.at.function.dom.alarm.extractionrange;

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
	private Month endMonth;
	
	/**Specify number of days*/
	// 日数指定
	private Days endDays;

	public EndDate(int endSpecify, int monthPrevious, int month, boolean curentMonth, int dayPrevious, int day, boolean makeToDay) {
		super();
		this.endSpecify = EnumAdaptor.valueOf(endSpecify, EndSpecify.class);
		this.endMonth = new Month(monthPrevious, month, curentMonth);
		this.endDays = new Days(dayPrevious, day, makeToDay);
	}
}
