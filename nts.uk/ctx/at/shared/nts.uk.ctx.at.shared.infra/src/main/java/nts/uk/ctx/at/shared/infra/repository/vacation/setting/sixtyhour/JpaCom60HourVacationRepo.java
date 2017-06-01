/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.sixtyhour;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Com60HourVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Com60HourVacationRepository;

/**
 * The Class JpaComSubstVacationRepo.
 */
@Stateless
public class JpaCom60HourVacationRepo extends JpaRepository implements Com60HourVacationRepository {

	@Override
	public void update(Com60HourVacation setting) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<Com60HourVacation> findById(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

}
