package nts.uk.ctx.bs.employee.app.find.employee.deletemanagement;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.app.find.employee.EmployeeToDeleteDetailDto;
import nts.uk.ctx.bs.employee.app.find.employee.EmployeeToDeleteDto;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmployeeDeleteFinder {

	@Inject
	private EmployeeDataMngInfoRepository empDataMngRepo;

	@Inject
	private PersonRepository personRepo;

	public EmployeeToDeleteDto getEmployeeInfo(String employeeId) {

		List<EmployeeDataMngInfo> listEmpData = empDataMngRepo.findByEmployeeId(employeeId);
		if (!listEmpData.isEmpty()) {
			EmployeeDataMngInfo empInfo = listEmpData.get(0);
			return EmployeeToDeleteDto.fromDomain(empInfo.getEmployeeCode().v(), empInfo.getRemoveReason().v());
		} else {
			return null;
		}
	}

	public List<EmployeeToDeleteDto> getAllEmployeeInfoToDelete() {

		String loginCID = AppContexts.user().companyId();
		List<EmployeeToDeleteDto> listResult = new ArrayList<>();
		List<EmployeeDataMngInfo> listEmpData = empDataMngRepo.getListEmpToDelete(loginCID);
		if (!listEmpData.isEmpty()) {
			for (EmployeeDataMngInfo employeeDataMngInfo : listEmpData) {
				Person person = personRepo.getByPersonId(employeeDataMngInfo.getPersonId()).get();
				listResult.add(EmployeeToDeleteDto.fromDomain(employeeDataMngInfo.getEmployeeCode().v(), "",
						person.getPersonNameGroup().getPersonName().getFullName().v().trim(),
						employeeDataMngInfo.getEmployeeId().toString()));
			}
		} else {
			return null;
		}
		return listResult;
	}

	public EmployeeToDeleteDetailDto getDetailEmployeeInfoToDelete(String employeeId) {

		EmployeeToDeleteDetailDto result = null;
		List<EmployeeDataMngInfo> listEmpData = empDataMngRepo.findByEmployeeId(employeeId);
		if (!listEmpData.isEmpty()) {
			EmployeeDataMngInfo empInfo = listEmpData.get(0);
			Person person = personRepo.getByPersonId(empInfo.getPersonId()).get();
			
			return EmployeeToDeleteDetailDto.fromDomain(
					empInfo.getEmployeeCode().v(),
					person.getPersonNameGroup().getPersonName().getFullName().v(), 
					empInfo.getRemoveReason().v(),
					empInfo.getDeleteDateTemporary().toString("yyyy/MM/dd HH:mm:ss"));
		} else {
			return null;
		}
	}
}
