package nts.uk.ctx.bs.employee.app.find.empfilemanagement;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.app.find.empfilemanagement.dto.EmployeeFileManagementSimpleDto;
import nts.uk.ctx.bs.employee.dom.empfilemanagement.EmpFileManagementRepository;
import nts.uk.ctx.bs.employee.dom.empfilemanagement.EmployeeFileManagement;

@Stateless
public class EmployeeFileManagementFinder {
	
	@Inject
	private EmpFileManagementRepository empFileManagementRepository;
	
	public EmployeeFileManagementSimpleDto getAvaOrMap(String employeeId){
		return empFileManagementRepository.getDataByParams(employeeId, -1).stream()
				.map(x -> new EmployeeFileManagementSimpleDto(x.getSId(), x.getFileID(), x.getTypeFile()))
				.collect(Collectors.toList()).get(0);
	}
	
	public List<Object> getListDocumentFile(String employeeId){
		return empFileManagementRepository.getListDocumentFile(employeeId, 2);
	}
}
