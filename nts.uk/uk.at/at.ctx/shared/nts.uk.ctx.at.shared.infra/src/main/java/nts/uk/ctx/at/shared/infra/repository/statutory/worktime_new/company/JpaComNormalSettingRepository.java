/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.company;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComNormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComNormalSettingRepository;

/**
 * The Class JpaCompanyWtSettingRepository.
 */
@Stateless
public class JpaComNormalSettingRepository extends JpaRepository
		implements ComNormalSettingRepository {

	@Override
	public void create(ComNormalSetting setting) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(ComNormalSetting setting) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(String companyId, int year) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<ComNormalSetting> find(String companyId, int year) {
		// TODO Auto-generated method stub
		return null;
	}

}
