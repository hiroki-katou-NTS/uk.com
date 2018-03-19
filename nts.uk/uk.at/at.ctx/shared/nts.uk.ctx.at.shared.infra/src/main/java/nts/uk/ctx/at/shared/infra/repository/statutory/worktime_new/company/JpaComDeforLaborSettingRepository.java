/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.company;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComDeforLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComDeforLaborSettingRepository;

/**
 * The Class JpaCompanyWtSettingRepository.
 */
@Stateless
public class JpaComDeforLaborSettingRepository extends JpaRepository
		implements ComDeforLaborSettingRepository {

	@Override
	public void create(ComDeforLaborSetting setting) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(ComDeforLaborSetting setting) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(String companyId, int year) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<ComDeforLaborSetting> find(String companyId, int year) {
		// TODO Auto-generated method stub
		return null;
	}

}
