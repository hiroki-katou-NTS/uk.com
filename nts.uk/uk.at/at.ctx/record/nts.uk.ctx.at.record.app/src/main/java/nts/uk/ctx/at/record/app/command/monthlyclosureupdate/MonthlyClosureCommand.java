package nts.uk.ctx.at.record.app.command.monthlyclosureupdate;

import java.util.List;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.YearMonth;

/**
 * 
 * @author HungTT
 *
 */

@Value
public class MonthlyClosureCommand {
	
	private String monthlyClosureUpdateLogId;
	private List<String> listEmployeeId;
	private int closureId;
	private GeneralDateTime startDT;
	private YearMonth currentMonth;
	private int closureDay;
	private boolean isLastDayOfMonth;
	private GeneralDate periodStart;
	private GeneralDate periodEnd;

}
