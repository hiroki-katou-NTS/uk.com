/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.workplace;

import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.bs.employee.dom.workplace.configinfo.WorkplaceConfigInfo;
import nts.uk.ctx.bs.employee.dom.workplace.configinfo.WorkplaceConfigInfoRepository;
@Stateless
public class JpaWorkplaceConfigInfoRepository implements WorkplaceConfigInfoRepository {

	@Override
	public List<WorkplaceConfigInfo> findAll(String companyId, String historyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(WorkplaceConfigInfo workplaceConfigInfo) {
		// TODO Auto-generated method stub
		
	}

}
