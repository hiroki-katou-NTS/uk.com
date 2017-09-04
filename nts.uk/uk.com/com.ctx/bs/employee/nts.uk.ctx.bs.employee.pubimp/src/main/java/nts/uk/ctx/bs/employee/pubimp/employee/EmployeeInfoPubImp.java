package nts.uk.ctx.bs.employee.pubimp.employee;

import java.util.Optional;

import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.employee.pub.employee.employeeInfo.EmployeeInfoDto;
import nts.uk.ctx.bs.employee.pub.employee.employeeInfo.EmployeeInfoPub;

public class EmployeeInfoPubImp implements EmployeeInfoPub {

	@Inject
	private EmployeeRepository repo;

	@Override
	public EmployeeInfoDto findByCid(String companyId, String employeeCode) {
		// TODO Auto-generated method stub
		Optional<Employee> opt = repo.findByEmployeeCode(companyId, employeeCode);
		if (opt.isPresent()) {
			return repo.findByEmployeeCode(companyId, employeeCode)
					.map(c -> new EmployeeInfoDto(c.getCompanyId(), c.getSCd().v(), c.getSId(), c.getPId())).get();
		} else {
			return null;
		}
	}

}
