package nts.uk.ctx.at.record.dom.attendanceitem;

import java.util.List;
import java.util.Map;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

public interface StoredProcdureProcess {

	public List<IntegrationOfDaily> dailyProcessing(List<IntegrationOfDaily> dailies);
	
	public List<IntegrationOfDaily> dailyProcessing(List<IntegrationOfDaily> dailies, Map<WorkTypeCode, WorkType> workTypes);
	
	public void monthlyProcessing(String companyId, String employeeId, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate);
}