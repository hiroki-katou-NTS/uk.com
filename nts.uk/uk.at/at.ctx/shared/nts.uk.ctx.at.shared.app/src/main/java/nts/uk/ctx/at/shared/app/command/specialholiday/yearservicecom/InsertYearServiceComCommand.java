package nts.uk.ctx.at.shared.app.command.specialholiday.yearservicecom;

import lombok.Getter;
import lombok.Setter;
/**
 * insert length Service Year Atr
 * @author yennth
 *
 */
@Getter
@Setter
public class InsertYearServiceComCommand {
	/**コード**/
	private int specialHolidayCode;
	/** 勤続年数 **/
	private int lengthServiceYearAtr;
}
