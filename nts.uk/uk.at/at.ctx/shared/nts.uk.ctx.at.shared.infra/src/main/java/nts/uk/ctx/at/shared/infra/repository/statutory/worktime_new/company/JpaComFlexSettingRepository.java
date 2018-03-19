/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.company;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComFlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComFlexSettingRepository;

/**
 * The Class JpaCompanyWtSettingRepository.
 */
@Stateless
public class JpaComFlexSettingRepository extends JpaRepository implements ComFlexSettingRepository {

	@Override
	public void create(ComFlexSetting setting) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(ComFlexSetting setting) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(String companyId, int year) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<ComFlexSetting> find(String companyId, int year) {
		// TODO Auto-generated method stub
		return null;
	}

}
