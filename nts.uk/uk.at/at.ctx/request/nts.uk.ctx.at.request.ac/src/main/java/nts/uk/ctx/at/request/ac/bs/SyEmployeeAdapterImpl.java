package nts.uk.ctx.at.request.ac.bs;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.common.adapter.bs.SyEmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.SyEmployeeImport;
import nts.uk.ctx.bs.employee.pub.person.IPersonInfoPub;
import nts.uk.ctx.bs.employee.pub.person.PersonInfoExport;
@Stateless
public class SyEmployeeAdapterImpl implements SyEmployeeAdapter{
	@Inject
	private IPersonInfoPub personInfoPub;

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

}

