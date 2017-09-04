package nts.uk.ctx.at.shared.app.command.specialholiday.yearservicecom;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.command.specialholiday.yearserviceset.UpdateYearServiceSetCommand;
import nts.uk.ctx.at.shared.dom.specialholiday.yearserviceset.YearServiceSet;
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
	private List<YearServiceSet> yearServiceSets;
}
