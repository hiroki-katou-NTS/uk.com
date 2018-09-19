package nts.uk.ctx.at.function.ac.role;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.role.RoleExportRepoAdapter;
import nts.uk.ctx.at.function.dom.adapter.role.RoleWhetherLoginImport;
import nts.uk.ctx.sys.auth.pub.role.RoleExportRepo;
import nts.uk.ctx.sys.auth.pub.role.RoleWhetherLoginPubExport;

@Stateless
public class RoleExportRepoAcFinder implements RoleExportRepoAdapter {

	@Inject
	private RoleExportRepo roleExportRepo;

	@Override
	public RoleWhetherLoginImport getCurrentLoginerRole() {
		RoleWhetherLoginPubExport role = roleExportRepo.getCurrentLoginerRole();
		return new RoleWhetherLoginImport(role.isEmployeeCharge(), role.isSalaryProfessional(),
				role.isHumanResOfficer(), role.isOfficeHelperPersonne(), role.isPersonalInformation());
	}

}
