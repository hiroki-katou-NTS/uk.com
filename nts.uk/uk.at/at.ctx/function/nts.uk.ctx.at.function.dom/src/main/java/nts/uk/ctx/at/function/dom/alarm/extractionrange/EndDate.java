package nts.uk.ctx.at.function.dom.alarm.extractionrange;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.Days;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.EndSpecify;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.Month;

/**
 * @author thanhpv
 * 終了日
 */

@Getter
@Setter
public class EndDate {

	/**終了日の指定方法*/
	private EndSpecify endSpecify;
	
	/**Closing Date*/
	// 締め日指定
	private Month endMonth;
	
	/**Specify number of days*/
	// 日数指定
	private Days endDays;

	public EndDate(EndSpecify endSpecify, Month endMonth, Days endDays) {
		super();
		this.endSpecify = endSpecify;
		this.endMonth = endMonth;
		this.endDays = endDays;
	}
}
