package nts.uk.ctx.at.function.ac.employment;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.employment.EmploymentAdapter;
import nts.uk.ctx.at.function.dom.adapter.employment.EmploymentHistoryImported;
import nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub;
import nts.uk.ctx.bs.employee.pub.employment.SyEmploymentPub;

@Stateless
public class EmploymentAcFinder implements EmploymentAdapter {

	@Inject
	private SyEmployeePub syEmployeePub;
	
	/** The emp pub. */
	@Inject
	private SyEmploymentPub empPub;

	
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
			return this.empPub.findSEmpHistBySid(companyId, employeeId, baseDate)
					.map(f -> new EmploymentHistoryImported(f.getEmployeeId(), f.getEmploymentCode(), f.getPeriod()));
	}

}
