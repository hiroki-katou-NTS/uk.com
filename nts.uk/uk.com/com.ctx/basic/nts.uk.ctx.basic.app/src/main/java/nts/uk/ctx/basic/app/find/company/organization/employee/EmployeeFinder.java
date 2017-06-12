package nts.uk.ctx.basic.app.find.company.organization.employee;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.basic.dom.company.organization.employee.EmployeeRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmployeeFinder {
	@Inject
	private EmployeeRepository EmpRepo;

	private String companyId = AppContexts.user().companyId();

	public Optional<EmployeeDto> getPersonIdByEmployeeCode(String employeeCode) {
		Optional<EmployeeDto> test = this.EmpRepo.getPersonIdByEmployeeCode(companyId, employeeCode)
				.map(item -> EmployeeDto.fromDomain(item));
		return test;
	}

	public List<EmployeeDto> getListPersonIdByEmployeeCode(List<String> listEmployeeCode) {
		return this.EmpRepo.getListPersonByListEmployee(companyId, listEmployeeCode)
				.stream()
				.map(item -> EmployeeDto.fromDomain(item))
				.collect(Collectors.toList());
	}
}