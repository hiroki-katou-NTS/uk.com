package nts.uk.ctx.at.shared.ac.employment.employwork.leaveinfo;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

//import nts.uk.ctx.bs.employee.pub.temporaryabsence.EmployeeLeaveHistoryPublish;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmploymentHistoryAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmploymentPeriodImported;
import nts.uk.ctx.bs.employee.pub.temporaryabsence.EmployeeLeaveHistoryPublish;
/**
 * 社員の雇用履歴Adapter Impl
 * @author HieuLt
 *
 */
@Stateless
public class EmploymentHistoryAdapterImpl implements EmploymentHistoryAdapter  {
	
//	@Inject
//	private EmploymentHistoryPublish EmployeeLeaveHistoryPublish;
	@Override
	public List<EmploymentPeriodImported> getEmploymentPeriod(List<String> listEmpId, DatePeriod datePeriod) {
		// TODO Auto-generated method stub
		//nts.uk.ctx.bs.employee.pub.temporaryabsence
		//EmploymentHistoryPublish
		// 
		return null;
	}

}
