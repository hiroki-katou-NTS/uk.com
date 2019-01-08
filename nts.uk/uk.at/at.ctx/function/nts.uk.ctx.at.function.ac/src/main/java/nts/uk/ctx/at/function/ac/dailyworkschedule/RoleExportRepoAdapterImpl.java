/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.ac.dailyworkschedule;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.dailyworkschedule.scrA.RoleExportRepoAdapter;
import nts.uk.ctx.at.function.dom.dailyworkschedule.scrA.RoleWhetherLoginPubImported;
import nts.uk.ctx.sys.auth.pub.role.RoleExportRepo;
import nts.uk.ctx.sys.auth.pub.role.RoleWhetherLoginPubExport;

/**
 * The Class RoleExportRepoAdapterImpl.
 */
// @author HoangDD
@Stateless
public class RoleExportRepoAdapterImpl implements RoleExportRepoAdapter{

	@Inject
	private RoleExportRepo roleExportRepo;
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.scrA.RoleExportRepoAdapter#getRoleWhetherLogin()
	 */
	@Override
	public RoleWhetherLoginPubImported getRoleWhetherLogin() {
		RoleWhetherLoginPubExport roleWhetherLoginPubExport = roleExportRepo.getCurrentLoginerRole();
		return new RoleWhetherLoginPubImported(roleWhetherLoginPubExport.isEmployeeCharge(), 
												roleWhetherLoginPubExport.isSalaryProfessional(), 
												roleWhetherLoginPubExport.isHumanResOfficer(), 
												roleWhetherLoginPubExport.isOfficeHelperPersonne(), 
												roleWhetherLoginPubExport.isPersonalInformation());
	}

}
