/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.ac.role.workplace;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.access.role.SyRoleAdapter;
import nts.uk.ctx.bs.employee.dom.access.role.WorkplaceIDImport;
import nts.uk.ctx.sys.auth.pub.role.RoleExportRepo;
import nts.uk.ctx.sys.auth.pub.role.WorkplaceIdExport;

/**
 * The Class SyRoleAdapterImpl.
 */
@Stateless
public class SyRoleAdapterImpl implements SyRoleAdapter {

	/** The role export repo. */
	@Inject
	private RoleExportRepo roleExportRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.dom.access.role.workplace.SyRoleWorkplaceAdapter#
	 * findListWkpIdByRoleId(java.lang.Integer)
	 */
	@Override
	public WorkplaceIDImport findListWkpIdByRoleId(Integer systemType) {

		WorkplaceIDImport workplaceIDImport = new WorkplaceIDImport();

		WorkplaceIdExport workplaceIdExport = roleExportRepo.findWorkPlaceIdByRoleId(systemType);
		workplaceIDImport.setIsAllEmp(workplaceIdExport.getIsAllEmp());
		workplaceIDImport.setListWorkplaceIds(workplaceIdExport.getListWorkplaceIds());

		return workplaceIDImport;
	}

}
