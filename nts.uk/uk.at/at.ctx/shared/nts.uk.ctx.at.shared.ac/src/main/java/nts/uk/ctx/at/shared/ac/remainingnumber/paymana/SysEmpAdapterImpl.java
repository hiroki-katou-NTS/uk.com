package nts.uk.ctx.at.shared.ac.remainingnumber.paymana;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SysEmpAdapter;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SyEmployeeImport;
import nts.uk.ctx.bs.employee.pub.person.IPersonInfoPub;
import nts.uk.ctx.bs.employee.pub.person.PersonInfoExport;

@Stateless
public class SysEmpAdapterImpl implements SysEmpAdapter {
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

