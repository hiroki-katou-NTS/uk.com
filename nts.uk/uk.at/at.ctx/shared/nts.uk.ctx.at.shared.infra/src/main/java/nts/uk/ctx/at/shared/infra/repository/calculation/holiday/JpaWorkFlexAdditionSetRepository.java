/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.calculation.holiday;

import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkFlexAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkFlexAdditionSetRepository;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshmtCalcCAddHdFle;

/**
 * The Class JpaWorkFlexAdditionSetRepository.
 */
public class JpaWorkFlexAdditionSetRepository extends JpaRepository implements WorkFlexAdditionSetRepository{

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.calculation.holiday.WorkFlexAdditionSetRepository#findByCid(java.lang.String)
	 */
	@Override
	public Optional<WorkFlexAdditionSet> findByCid(String companyID) {
		Optional<KshmtCalcCAddHdFle> optEntity = this.queryProxy().find(companyID, KshmtCalcCAddHdFle.class);
		if (optEntity.isPresent()) {
			JpaHolidayAddtionRepository holidayAddtionRepository = new JpaHolidayAddtionRepository();
			WorkFlexAdditionSet domain = holidayAddtionRepository.convertToDomainFlexWork(optEntity.get());
			return Optional.of(domain);
		}
		return Optional.empty();
	}
}

