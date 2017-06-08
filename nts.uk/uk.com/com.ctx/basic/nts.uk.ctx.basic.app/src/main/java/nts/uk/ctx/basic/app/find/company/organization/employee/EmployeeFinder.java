package nts.uk.ctx.basic.app.find.company.organization.employee;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.basic.dom.organization.employee.EmployeeRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmployeeFinder {
	@Inject 
	private EmployeeRepository EmpRepo;
	
	public Optional<EmployeeDto> findByEmployeeCode(String employeeCode){
		String companyId = AppContexts.user().companyId();
		return this.EmpRepo.findPersonID(companyId, employeeCode)
				.map(item ->EmployeeDto.toDomain(item));
	}
}