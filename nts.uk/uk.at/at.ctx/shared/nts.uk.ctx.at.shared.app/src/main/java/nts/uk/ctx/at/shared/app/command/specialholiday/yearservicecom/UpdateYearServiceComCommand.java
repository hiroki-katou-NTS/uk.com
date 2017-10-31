package nts.uk.ctx.at.shared.app.command.specialholiday.yearservicecom;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * update length Service Year Atr
 * @author yennth
 *
 */
@Data
@AllArgsConstructor
public class UpdateYearServiceComCommand {
	/**コード**/
	private String specialHolidayCode;
	/** 勤続年数 **/
	private int lengthServiceYearAtr;
	private List<YearServiceCommand> yearServiceSets;
}
