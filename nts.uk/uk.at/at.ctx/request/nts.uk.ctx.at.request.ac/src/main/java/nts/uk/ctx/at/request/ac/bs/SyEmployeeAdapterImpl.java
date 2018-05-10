package nts.uk.ctx.at.request.ac.bs;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.common.adapter.bs.SyEmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.SyEmployeeImport;
import nts.uk.ctx.bs.employee.pub.employee.EmployeeBasicInfoExport;
import nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub;
@Stateless
public class SyEmployeeAdapterImpl implements SyEmployeeAdapter{
	@Inject
	private SyEmployeePub employeePub;

	@Override
	public SyEmployeeImport getPersonInfor(String employeeId) {
		EmployeeBasicInfoExport infor = employeePub.findBySId(employeeId);
		SyEmployeeImport data = new SyEmployeeImport(infor.getPId(),
				infor.getPName(),
				infor.getEntryDate(),
				infor.getGender(),
				infor.getBirthDay(),
				infor.getEmployeeId(),
				infor.getEmployeeCode(),
				infor.getRetiredDate());
		
		return data;
	}

}

