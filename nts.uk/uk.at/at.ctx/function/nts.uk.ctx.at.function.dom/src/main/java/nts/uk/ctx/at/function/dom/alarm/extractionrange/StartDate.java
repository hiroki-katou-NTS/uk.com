package nts.uk.ctx.at.function.dom.alarm.extractionrange;
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
	private Month strMonth;
	
	/**Specify number of days*/
	// 日数指定
	private Days strDays;

	public StartDate(int startSpecify,  int monthPrevious, int month, boolean curentMonth, int dayPrevious, int day, boolean makeToDay) {
		super();
		this.startSpecify = EnumAdaptor.valueOf(startSpecify, StartSpecify.class);
		this.strMonth = new Month(monthPrevious, month, curentMonth);
		this.strDays = new Days(dayPrevious, day, makeToDay);
	}

}
