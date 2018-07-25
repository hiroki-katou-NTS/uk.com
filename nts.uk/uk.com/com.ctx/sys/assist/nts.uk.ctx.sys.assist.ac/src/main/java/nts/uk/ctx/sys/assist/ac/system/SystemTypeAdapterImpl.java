package nts.uk.ctx.sys.assist.ac.system;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.dom.system.SystemTypeAdapter;
import nts.uk.ctx.sys.assist.dom.system.SystemTypeImport;
import nts.uk.ctx.sys.auth.pub.role.RoleExportRepo;
import nts.uk.ctx.sys.auth.pub.role.RoleWhetherLoginPubExport;

@Stateless
public class SystemTypeAdapterImpl implements SystemTypeAdapter {

	/** The RoleExportRepo pub. */
	@Inject
	private RoleExportRepo roleExportRepo;

	@Override
	public SystemTypeImport getSystemTypeByEmpId() {
		RoleWhetherLoginPubExport temp = roleExportRepo.getWhetherLoginerCharge();

		return new SystemTypeImport(temp.isEmployeeCharge(),temp.isSalaryProfessional(), temp.isHumanResOfficer(),
				temp.isOfficeHelperPersonne(), temp.isPersonalInformation());
	}

}
