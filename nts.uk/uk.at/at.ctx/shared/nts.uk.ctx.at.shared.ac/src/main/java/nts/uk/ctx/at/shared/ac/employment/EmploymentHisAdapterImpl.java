package nts.uk.ctx.at.shared.ac.employment;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmploymentHisAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmploymentPeriodImported;
/**
 * 
 * @author HieuLt
 *
 */
@Stateless
public class EmploymentHisAdapterImpl implements EmploymentHisAdapter {

	@Override
	public List<EmploymentPeriodImported> getEmploymentPeriod(List<String> listEmpId, DatePeriod datePeriod) {
		// TODO Auto-generated method stub
		return null;
	}

}
