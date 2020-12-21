package nts.uk.ctx.at.request.ac.bs;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.SyEmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.SyEmployeeImport;
import nts.uk.ctx.bs.employee.pub.person.IPersonInfoPub;
import nts.uk.ctx.bs.employee.pub.person.PersonInfoExport;
import nts.uk.ctx.sys.auth.pub.employee.EmployeePublisher;
@Stateless
public class SyEmployeeAdapterImpl implements SyEmployeeAdapter{
	@Inject
	private IPersonInfoPub personInfoPub;
	
	@Inject
	private EmployeePublisher employeePublisher;

	@Override
	public SyEmployeeImport getPersonInfor(String employeeId) {
		PersonInfoExport infor = personInfoPub.getPersonInfo(employeeId);
		SyEmployeeImport data = new SyEmployeeImport(infor.getPid(),
				infor.getBusinessName(),
				infor.getEntryDate(),
				infor.getGender(),
				infor.getBirthDay(),
				infor.getEmployeeId(),
				infor.getEmployeeCode(),
				infor.getRetiredDate());
		
		return data;
	}

	@Override
	public List<SyEmployeeImport> getPersonInfor(List<String> employeeIds) {
		return personInfoPub.listPersonInfor(employeeIds).stream()
				.map(infor -> new SyEmployeeImport(infor.getPid(), infor.getBusinessName(), infor.getEntryDate(),
						infor.getGender(), infor.getBirthDay(), infor.getEmployeeId(), infor.getEmployeeCode(),
						infor.getRetiredDate()))
				.collect(Collectors.toList());
	}

	@Override
	public Map<String, String> getListEmpInfo(String companyID, GeneralDate referenceDate, List<String> workplaceID) {
		return employeePublisher.getListEmpInfo(companyID, referenceDate, workplaceID);
	}

}

