/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.workplace;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpNormalWorkingTimeRepository;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpRegLaborTime;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpRegLaborTimePK;

/**
 * The Class JpaWkpNormalWorkingTimeRepository.
 */
@Stateless
public class JpaWkpNormalWorkingTimeRepository extends JpaRepository implements WkpNormalWorkingTimeRepository {

	@Override
	public void delete(String cid, String wkpId) {
		commandProxy().remove(KshstWkpRegLaborTime.class, new KshstWkpRegLaborTimePK(cid, wkpId));
		
	}

}
