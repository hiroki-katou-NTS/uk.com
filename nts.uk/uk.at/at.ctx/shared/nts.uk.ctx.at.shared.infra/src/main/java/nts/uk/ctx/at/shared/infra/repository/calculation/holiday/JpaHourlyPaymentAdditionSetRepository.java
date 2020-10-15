/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.calculation.holiday;

import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HourlyPaymentAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HourlyPaymentAdditionSetRepository;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshstHourPayAaddSet;

/**
 * The Class JpaHourlyPaymentAdditionSetRepository.
 */
public class JpaHourlyPaymentAdditionSetRepository extends JpaRepository implements HourlyPaymentAdditionSetRepository{

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.calculation.holiday.HourlyPaymentAdditionSetRepository#findByCid(java.lang.String)
	 */
	@Override
	public Optional<HourlyPaymentAdditionSet> findByCid(String companyId) {
		Optional<KshstHourPayAaddSet> optEntity = this.queryProxy().find(companyId, KshstHourPayAaddSet.class);
		if (optEntity.isPresent()) {
			JpaHolidayAddtionRepository holidayAddtionRepository = new JpaHolidayAddtionRepository();
			HourlyPaymentAdditionSet domain = holidayAddtionRepository.convertToDomainHourlyPaymentAddSet(optEntity.get());
			return Optional.of(domain);
		}
		return Optional.empty();
	}

}

