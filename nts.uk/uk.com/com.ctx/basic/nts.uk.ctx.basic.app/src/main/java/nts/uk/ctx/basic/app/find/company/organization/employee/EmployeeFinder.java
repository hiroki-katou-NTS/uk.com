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
	
	/** Get person ID by EmployeeCode */
	public Optional<EmployeeDto> getPersonIdByEmployeeCode(String employeeCode) {
		return this.EmpRepo.getPersonIdByEmployeeCode(companyId, employeeCode)
				.map(item -> EmployeeDto.fromDomain(item));
	}
	/** Get person ID by LIST EmployeeCode */
	public List<EmployeeDto> getListPersonIdByEmployeeCode(List<String> listEmployeeCode) {
		return this.EmpRepo.getListPersonByListEmployee(companyId, listEmployeeCode)
				.stream()
				.map(item -> EmployeeDto.fromDomain(item))
				.collect(Collectors.toList());
	}
}