/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.company;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTimeRepository;

/**
 * The Class JpaCompanyWtSettingRepository.
 */
@Stateless
public class JpaComRegularLaborTimeRepository extends JpaRepository
		implements ComRegularLaborTimeRepository {

	@Override
	public void create(ComRegularLaborTime setting) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(ComRegularLaborTime setting) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(String companyId, int year) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<ComRegularLaborTime> find(String companyId, int year) {
		// TODO Auto-generated method stub
		return null;
	}

}
