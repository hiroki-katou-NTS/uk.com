package nts.uk.ctx.pereg.app.find.filemanagement;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.storage.FileStorage;
import nts.uk.ctx.bs.employee.app.find.empfilemanagement.dto.EmployeeFileManagementDto;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.pereg.dom.filemanagement.EmpFileManagementRepository;

@Stateless
public class EmployeeFileManagementFinder {

	@Inject
	private EmpFileManagementRepository empFileManagementRepository;

	@Inject
	private EmployeeDataMngInfoRepository emplRepo;

	@Inject
	private FileStorage fileStorage;

	public EmployeeFileManagementDto getAvaOrMap(String employeeId, int fileType) {
		Optional<EmployeeDataMngInfo> empOpt = emplRepo.findByEmpId(employeeId);
		if (empOpt.isPresent()) {
			EmployeeDataMngInfo emp= empOpt.get();
			List<EmployeeFileManagementDto> lst = empFileManagementRepository.getDataByParams(emp.getPersonId(), fileType)
					.stream().map(x -> new EmployeeFileManagementDto(x.getPId(), x.getFileID(), x.getTypeFile().value))
					.collect(Collectors.toList());
			return lst.size() > 0 ? lst.get(0) : new EmployeeFileManagementDto();
		} else {
			return null;
		}

	}

	public boolean checkEmpFileMnExist(String employeeId, int fileType) {
		EmployeeDataMngInfo employee = emplRepo.findByEmpId(employeeId).get();
		boolean v = empFileManagementRepository.checkObjectExist(employee.getPersonId(), fileType);
		return v;
	}

	public List<EmployeeFileManagementDto> getListDocumentFile(String employeeId) {

		List<EmployeeFileManagementDto> s = empFileManagementRepository.getListDocumentFile(employeeId, 2).stream()
				.map(x -> new EmployeeFileManagementDto(x[0].toString(), x[1].toString(),
						fileStorage.getInfo(x[1].toString()).get().getOriginalName(),
						Integer.parseInt(x[2].toString()), fileStorage.getInfo(x[1].toString()).get().getOriginalSize()))
				.collect(Collectors.toList());
		return s;

	}

}
