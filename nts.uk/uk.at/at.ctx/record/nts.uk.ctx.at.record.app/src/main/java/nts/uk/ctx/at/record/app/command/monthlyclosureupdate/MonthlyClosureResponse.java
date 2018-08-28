package nts.uk.ctx.at.record.app.command.monthlyclosureupdate;

import java.util.List;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 
 * @author HungTT
 *
 */
@Value
public class MonthlyClosureResponse {
	
	private String monthlyClosureUpdateLogId;
	private List<String> listEmployeeId;
	private Integer closureId;
	private GeneralDateTime startDT;
	private GeneralDateTime endDT;
	private Integer currentMonth;
	private Integer closureDay;
	private Boolean isLastDayOfMonth;
	private GeneralDate periodStart;
	private GeneralDate periodEnd;

}
