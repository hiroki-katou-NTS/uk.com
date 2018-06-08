/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.ac.find.role;

import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.sys.gateway.dom.login.adapter.RoleAdapter;
import nts.uk.ctx.sys.gateway.dom.login.dto.RoleImport;
@Stateless
public class RoleAdapterImpl implements RoleAdapter{

//	@Inject
//	private RoleExportRepo roleExportRepo;
	
	@Override
	public List<RoleImport> getAllById(String roleId) {
		// TODO Auto-generated method stub
		return null;
	}

}
