package nts.uk.ctx.bs.employee.app.find.empfilemanagement;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.storage.FileStorage;
import nts.uk.ctx.bs.employee.app.find.empfilemanagement.dto.EmployeeFileManagementDto;
import nts.uk.ctx.bs.employee.dom.empfilemanagement.EmpFileManagementRepository;

@Stateless
public class EmployeeFileManagementFinder {

	@Inject
	private EmpFileManagementRepository empFileManagementRepository;

	@Inject
	private FileStorage fileStorage;

	public EmployeeFileManagementDto getAvaOrMap(String employeeId, int fileType) {
		List<EmployeeFileManagementDto> lst = empFileManagementRepository.getDataByParams(employeeId, fileType).stream()
				.map(x -> new EmployeeFileManagementDto(x.getPId(), x.getFileID(), x.getTypeFile().value))
				.collect(Collectors.toList());
		return lst.size() > 0 ? lst.get(0) : new EmployeeFileManagementDto();
	}

	public boolean checkEmpFileMnExist(String employeeId, int fileType) {
		return empFileManagementRepository.checkObjectExist(employeeId, fileType);
	}

	public List<EmployeeFileManagementDto> getListDocumentFile(String employeeId) {

		return empFileManagementRepository.getListDocumentFile(employeeId, 2).stream()
				.map(x -> new EmployeeFileManagementDto(x[0].toString(), x[1].toString(),
						fileStorage.getInfo(x[1].toString()).get().getOriginalName(), Integer.parseInt(x[2].toString()),
						x[3].toString(), x[4].toString()))
				.collect(Collectors.toList());

	}

}
