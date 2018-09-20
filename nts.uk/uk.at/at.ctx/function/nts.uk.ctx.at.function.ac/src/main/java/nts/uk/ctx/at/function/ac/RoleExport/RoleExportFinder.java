package nts.uk.ctx.at.function.ac.RoleExport;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.RoleLogin.LoginRoleAdapter;
import nts.uk.ctx.at.function.dom.adapter.RoleLogin.RoleWhetherLoginPubExportDto;
import nts.uk.ctx.sys.auth.pub.role.RoleExportRepo;
import nts.uk.ctx.sys.auth.pub.role.RoleWhetherLoginPubExport;

/**
 * The Class RoleExportFinder.
 */
@Stateless
public class RoleExportFinder implements LoginRoleAdapter {

	/** The role export repo. */
	@Inject
	RoleExportRepo roleExportRepo;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.adapter.RoleLogin.LoginRoleAdapter#getCurrentLoginerRole()
	 */
	@Override
	public RoleWhetherLoginPubExportDto getCurrentLoginerRole() {
		RoleWhetherLoginPubExport loginRole = roleExportRepo.getCurrentLoginerRole();
		return new RoleWhetherLoginPubExportDto(loginRole.isEmployeeCharge(), loginRole.isSalaryProfessional(),
				loginRole.isHumanResOfficer(), loginRole.isOfficeHelperPersonne(), loginRole.isPersonalInformation());
	}
}
