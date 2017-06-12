package nts.uk.ctx.basic.app.find.company.organization.employee;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.basic.dom.company.organization.employee.EmployeeRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmployeeFinder {
	@Inject 
	private EmployeeRepository EmpRepo;
	
	public Optional<EmployeeDto> getPersonIdByEmployeeCode(String employeeCode){
		String companyId = AppContexts.user().companyId();
		Optional<EmployeeDto> test =  this.EmpRepo.getPersonIdByEmployeeCode(companyId, employeeCode)
				.map(item ->EmployeeDto.fromDomain(item));
		return test;	
	}
}