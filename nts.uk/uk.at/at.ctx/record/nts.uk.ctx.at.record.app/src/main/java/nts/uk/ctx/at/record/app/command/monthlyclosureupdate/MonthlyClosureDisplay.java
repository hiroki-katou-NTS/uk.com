package nts.uk.ctx.at.record.app.command.monthlyclosureupdate;

import java.util.List;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class MonthlyClosureDisplay {
	private String monthlyClosureUpdateLogId;
	private List<String> listEmployeeId;
	private int closureId;
	private GeneralDate periodStart;
	private GeneralDate periodEnd;
}
