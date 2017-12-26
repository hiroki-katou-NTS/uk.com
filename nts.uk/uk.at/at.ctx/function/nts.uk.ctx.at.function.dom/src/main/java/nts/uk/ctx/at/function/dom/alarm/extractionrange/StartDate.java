package nts.uk.ctx.at.function.dom.alarm.extractionrange;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.ClosingDate;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.NumberOfDays;
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
	private ClosingDate closingDate;
	
	/**Specify number of days*/
	// 日数指定
	private NumberOfDays numberOfDays;

}
