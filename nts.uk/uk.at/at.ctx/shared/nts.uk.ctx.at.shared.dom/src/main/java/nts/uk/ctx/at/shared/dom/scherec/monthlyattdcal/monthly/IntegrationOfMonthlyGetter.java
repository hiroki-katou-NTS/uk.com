package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

public interface IntegrationOfMonthlyGetter {

	IntegrationOfMonthly get(String sid, YearMonth ym, ClosureId closureId, ClosureDate closureDate);
}
