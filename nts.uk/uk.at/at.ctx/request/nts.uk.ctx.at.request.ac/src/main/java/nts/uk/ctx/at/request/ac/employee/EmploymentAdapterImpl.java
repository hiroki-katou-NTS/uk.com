package nts.uk.ctx.at.request.ac.employee;

import java.util.Optional;

import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.employee.EmploymentAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.EmploymentHistoryImported;
import nts.uk.ctx.bs.employee.pub.employment.SyEmploymentPub;

public class EmploymentAdapterImpl implements EmploymentAdapter{

	@Inject
	private SyEmploymentPub empPub;
	
	@Override
	public Optional<EmploymentHistoryImported> getEmpHistBySid(String companyId, String employeeId,
			GeneralDate baseDate) {
		return this.empPub.findSEmpHistBySid(companyId, employeeId, baseDate).map(f -> 
			new EmploymentHistoryImported(f.getEmployeeId(), f.getEmploymentCode(), f.getPeriod())
		);
	}

}
