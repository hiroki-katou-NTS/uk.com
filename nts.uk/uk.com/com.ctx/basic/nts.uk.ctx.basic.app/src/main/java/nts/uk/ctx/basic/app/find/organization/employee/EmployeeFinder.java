package nts.uk.ctx.basic.app.find.organization.employee;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.basic.dom.organization.employee.EmployeeRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmployeeFinder {
//	@Inject 
//	private EmployeeRepository EmpRepo;
//	
//	public List<EmployeeDto> findByEmployeeCode(String employeeCode){
//		String companyId = AppContexts.user().companyId();
//		return this.EmpRepo.findByEmployeeCode(companyId, employeeCode)
//				.stream()
//				.map(item ->EmployeeDto.toDomain(item))
//				.collect(Collectors.toList());
//	}
}