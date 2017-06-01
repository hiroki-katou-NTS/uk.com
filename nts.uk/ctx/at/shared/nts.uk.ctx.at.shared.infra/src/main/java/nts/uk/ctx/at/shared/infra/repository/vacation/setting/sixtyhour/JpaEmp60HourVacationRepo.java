/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.sixtyhour;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Emp60HourVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Emp60HourVacationRepository;

/**
 * The Class JpaEmpSubstVacationRepo.
 */
@Stateless
public class JpaEmp60HourVacationRepo extends JpaRepository implements Emp60HourVacationRepository {

	@Override
	public void update(Emp60HourVacation setting) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<Emp60HourVacation> findById(String companyId, String contractTypeCode) {
		// TODO Auto-generated method stub
		return null;
	}

}
