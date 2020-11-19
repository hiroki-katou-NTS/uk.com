package nts.uk.ctx.sys.assist.dom.datarestoration;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.dom.role.RoleImportAdapter;
import nts.uk.ctx.sys.assist.dom.storage.SystemType;

@Stateless
public class LoginPersonInChargeService {
	@Inject 
	private RoleImportAdapter roleAdapter;
	
	public LoginPersonInCharge getPic() {
		return roleAdapter.getInChargeInfo();
	}
	
	public List<SystemType> getSystemTypes(LoginPersonInCharge pic) {
		List<SystemType> systemTypes = new ArrayList<>();
		if (pic.isAttendance())
			systemTypes.add(SystemType.ATTENDANCE_SYSTEM);
		if (pic.isPersonnel())
			systemTypes.add(SystemType.PERSON_SYSTEM);
		if (pic.isOfficeHelper())
			systemTypes.add(SystemType.OFFICE_HELPER);
		if (pic.isPayroll())
			systemTypes.add(SystemType.PAYROLL_SYSTEM);
		return systemTypes;
	}
}
