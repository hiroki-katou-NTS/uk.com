package nts.uk.ctx.at.function.ac.employment;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.employment.EmploymentAdapter;
import nts.uk.ctx.at.function.dom.adapter.employment.EmploymentHistoryImported;
import nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub;

@Stateless
public class EmploymentAcFinder implements EmploymentAdapter {

	@Inject
	private SyEmployeePub syEmployeePub;
	
	@Override
	public String getClosure(String employeeId, GeneralDate baseDate) {
		
		List<String> employmentCodes = syEmployeePub.getEmployeeCode(employeeId, baseDate);
		
		if (employmentCodes == null || employmentCodes.isEmpty())
			throw new RuntimeException("Not exist employment code of login employee!");
		return employmentCodes.get(0);
	}

	@Override
	public Optional<EmploymentHistoryImported> getEmpHistBySid(String companyId, String employeeId,
			GeneralDate baseDate) {
		// TODO Auto-generated method stub
		return null;
	}

}
