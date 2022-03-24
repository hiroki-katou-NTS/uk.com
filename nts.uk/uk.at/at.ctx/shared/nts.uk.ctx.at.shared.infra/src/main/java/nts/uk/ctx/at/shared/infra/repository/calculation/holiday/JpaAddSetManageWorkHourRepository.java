/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.calculation.holiday;

import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.AddSetManageWorkHour;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.AddSetManageWorkHourRepository;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshstAddSetManWKHour;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshstAddSetManWKHourPK;

import javax.ejb.Stateless;

/**
 * The Class JpaAddSetManageWorkHourRepository.
 */
@Stateless
public class JpaAddSetManageWorkHourRepository extends JpaRepository implements AddSetManageWorkHourRepository{

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.calculation.holiday.AddSetManageWorkHourRepository#findByCid(java.lang.String)
	 */
	@Override
	public Optional<AddSetManageWorkHour> findByCid(String companyId) {
		Optional<KshstAddSetManWKHour> optEntity = this.queryProxy().find(new KshstAddSetManWKHourPK(companyId), KshstAddSetManWKHour.class);
		if (optEntity.isPresent()) {
			return JpaHolidayAddtionRepository.convertToDomainAddSetManageWorkHour(optEntity.get());
		}
		return Optional.empty();
	}
}

