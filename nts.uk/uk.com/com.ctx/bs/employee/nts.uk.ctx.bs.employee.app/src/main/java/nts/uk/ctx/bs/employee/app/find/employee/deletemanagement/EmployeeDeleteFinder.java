package nts.uk.ctx.bs.employee.app.find.employee.deletemanagement;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.app.find.employee.EmployeeToDeleteDto;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;

@Stateless
public class EmployeeDeleteFinder {
	
	@Inject
	private EmployeeDataMngInfoRepository EmpDataMngRepo;
	
	
	public EmployeeToDeleteDto getEmployeeInfo(String employeeId) {
		
		List<EmployeeDataMngInfo> listEmpData = EmpDataMngRepo.findByEmployeeId(employeeId);
		if (!listEmpData.isEmpty()) {
			EmployeeDataMngInfo empInfo =  EmpDataMngRepo.findByEmployeeId(employeeId).get(0);
			return EmployeeToDeleteDto.fromDomain(empInfo.getEmployeeCode().v(), empInfo.getRemoveReason().v());
		} else {
			return null;
		}
	}

}
