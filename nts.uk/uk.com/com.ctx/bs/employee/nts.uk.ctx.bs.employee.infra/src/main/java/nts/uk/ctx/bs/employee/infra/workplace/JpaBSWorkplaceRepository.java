/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.workplace;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.workplace.Workplace;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceRepository;
@Stateless
public class JpaBSWorkplaceRepository implements WorkplaceRepository {

	@Override
	public List<Workplace> findByStartDate(String companyId, GeneralDate date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String add(Workplace wkp) {
		// TODO Auto-generated method stub
		//return historyId
		return null;
	}

	@Override
	public void updateLatestHistory(Workplace wkp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeLatestHistory(String companyId, String workplaceId) {
		// TODO Auto-generated method stub
		
	}

}
