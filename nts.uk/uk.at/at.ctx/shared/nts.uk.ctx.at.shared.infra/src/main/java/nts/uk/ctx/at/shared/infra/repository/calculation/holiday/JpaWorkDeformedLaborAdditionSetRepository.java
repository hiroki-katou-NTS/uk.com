/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.calculation.holiday;

import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkDeformedLaborAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkDeformedLaborAdditionSetRepository;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshstWorkDepLaborSet;

/**
 * The Class JpaWorkDeformedLaborAdditionSetRepository.
 */
public class JpaWorkDeformedLaborAdditionSetRepository extends JpaRepository implements WorkDeformedLaborAdditionSetRepository{

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.calculation.holiday.WorkDeformedLaborAdditionSetRepository#findByCid(java.lang.String)
	 */
	@Override
	public Optional<WorkDeformedLaborAdditionSet> findByCid(String companyID) {
		Optional<KshstWorkDepLaborSet> optEntity = this.queryProxy().find(companyID, KshstWorkDepLaborSet.class);
		if (optEntity.isPresent()) {
			JpaHolidayAddtionRepository holidayAddtionRepository = new JpaHolidayAddtionRepository();
			WorkDeformedLaborAdditionSet domain = holidayAddtionRepository.convertToDomainIrregularWork(optEntity.get());
			return Optional.of(domain);
		}
		return Optional.empty();
	}

}

