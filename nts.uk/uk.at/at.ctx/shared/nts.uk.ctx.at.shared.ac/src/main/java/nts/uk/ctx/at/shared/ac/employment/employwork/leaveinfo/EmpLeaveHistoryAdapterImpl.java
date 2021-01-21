package nts.uk.ctx.at.shared.ac.employment.employwork.leaveinfo;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmpLeaveHistoryAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmployeeLeaveJobPeriodImport;
import nts.uk.ctx.bs.employee.pub.temporaryabsence.EmpLeavePeriodExport;
import nts.uk.ctx.bs.employee.pub.temporaryabsence.EmployeeLeaveHistoryPublish;
/**
 * 社員の休職履歴Adapter Implement
 * @author Hieult
 *
 */
@Stateless
public class EmpLeaveHistoryAdapterImpl implements EmpLeaveHistoryAdapter {
	
	@Inject
	public EmployeeLeaveHistoryPublish publish;

	@Override
	public List<EmployeeLeaveJobPeriodImport> getLeaveBySpecifyingPeriod(List<String> listEmpId,
			DatePeriod datePeriod) {
		
		List<EmpLeavePeriodExport> data = publish.getBySpecifyingPeriod(listEmpId, datePeriod); 
		List<EmployeeLeaveJobPeriodImport> result = data.stream().map(c -> new EmployeeLeaveJobPeriodImport(c.getEmpID(), c.getDatePeriod())).collect(Collectors.toList());
		
		return result;
	}

}
