package nts.uk.ctx.at.record.dom.attendanceitem;

import java.util.List;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

public interface StoredProcdureProcess {

	public List<IntegrationOfDaily> dailyProcessing(List<IntegrationOfDaily> dailies);
	
	public void monthlyProcessing(String companyId, String employeeId, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate);
}
