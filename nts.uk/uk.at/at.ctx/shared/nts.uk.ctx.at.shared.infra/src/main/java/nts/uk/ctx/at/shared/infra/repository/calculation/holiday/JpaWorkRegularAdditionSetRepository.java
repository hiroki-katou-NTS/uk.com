/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.calculation.holiday;

import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkRegularAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkRegularAdditionSetRepository;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshstWorkRegularSet;

/**
 * The Class JpaWorkRegularAdditionSetRepository.
 */
public class JpaWorkRegularAdditionSetRepository extends JpaRepository implements WorkRegularAdditionSetRepository{

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.calculation.holiday.WorkRegularAdditionSetRepository#findByCID(java.lang.String)
	 */
	@Override
	public Optional<WorkRegularAdditionSet> findByCID(String companyID) {
		Optional<KshstWorkRegularSet> optEntity = this.queryProxy().find(companyID, KshstWorkRegularSet.class);
		if (optEntity.isPresent()) {
			JpaHolidayAddtionRepository holidayAddtionRepository = new JpaHolidayAddtionRepository();
			WorkRegularAdditionSet domain = holidayAddtionRepository.convertToDomainRegularWork(optEntity.get());
			return Optional.of(domain);
		}
		return Optional.empty();
	}

}

