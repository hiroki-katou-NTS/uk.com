package nts.uk.ctx.bs.employee.app.find.empfilemanagement;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.app.find.empfilemanagement.dto.EmployeeFileManagementDto;
import nts.uk.ctx.bs.employee.dom.empfilemanagement.EmpFileManagementRepository;

@Stateless
public class EmployeeFileManagementFinder {
	
	@Inject
	private EmpFileManagementRepository empFileManagementRepository;
	
	public EmployeeFileManagementDto getAvaOrMap(String employeeId, int fileType){
		List<EmployeeFileManagementDto> lst = empFileManagementRepository.getDataByParams(employeeId, fileType).stream()
				.map(x -> new EmployeeFileManagementDto(x.getSId(), x.getFileID(), x.getTypeFile()))
				.collect(Collectors.toList());
		return lst.size() > 0 ? lst.get(0) : null;
	}
	
	public boolean checkEmpFileMnExist(String employeeId, int fileType){
		return empFileManagementRepository.checkObjectExist(employeeId, fileType);
	}
	
	public List<Object> getListDocumentFile(String employeeId){
		return empFileManagementRepository.getListDocumentFile(employeeId, 2);
	}
}
