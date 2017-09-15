package nts.uk.ctx.bs.employee.pubimp.employee.atworkreferdate;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.employee.pub.employee.employeeInfo.EmployeeInfoDtoExport;
import nts.uk.ctx.bs.employee.pub.employee.employeeInfo.atworkreferdate.EmployeeAtworkReferenDatePub;

public class EmployeeAtworkReferenDatePubImp implements EmployeeAtworkReferenDatePub {

	@Inject
	private EmployeeRepository repo;

	/** The Constant DATE_FORMAT FROM Client */
	private static final String DATE_FORMAT = "yyyy/MM/dd";

	@Override
	public List<EmployeeInfoDtoExport> getListEmployeeByStandardDate(String companyId, String standardDate) {
	
		GeneralDate _standardDate = GeneralDate.fromString(standardDate, DATE_FORMAT);
		
		List<Employee> listEmpDomain = repo.getListEmpByStandardDate(companyId, _standardDate);
		
		List<EmployeeInfoDtoExport> result = new ArrayList<>();
		
		if (!listEmpDomain.isEmpty()) {
			listEmpDomain.forEach(c -> {
				EmployeeInfoDtoExport empDto = new EmployeeInfoDtoExport(c.getCompanyId(), c.getSCd().v(), c.getSId(),
						c.getPId());
				result.add(empDto);
			});
		}
		return result;
	}

}
