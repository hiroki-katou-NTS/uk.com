package nts.uk.ctx.at.shared.app.command.specialholiday.yearservicecom;

import lombok.Getter;
import lombok.Setter;
/**
 * update length Service Year Atr
 * @author yennth
 *
 */
@Getter
@Setter
public class UpdateYearServiceComCommand {
	/**コード**/
	private int specialHolidayCode;
	/** 勤続年数 **/
	private int lengthServiceYearAtr;
}
