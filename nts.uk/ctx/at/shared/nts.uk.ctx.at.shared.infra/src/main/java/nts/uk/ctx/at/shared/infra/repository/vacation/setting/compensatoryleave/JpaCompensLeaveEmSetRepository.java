/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.compensatoryleave;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveEmSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveEmSetting;

@Stateless
public class JpaCompensLeaveEmSetRepository extends JpaRepository implements CompensLeaveEmSetRepository {

	@Override
	public void insert(CompensatoryLeaveEmSetting setting) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(CompensatoryLeaveEmSetting setting) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CompensatoryLeaveEmSetting find(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

}
