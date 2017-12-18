/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository;

/**
 * The Class JpaFixedWorkSettingRepository.
 */

@Stateless
public class JpaFixedWorkSettingRepository extends JpaRepository
		implements FixedWorkSettingRepository {

	@Override
	public Optional<FixedWorkSetting> find(String companyId, String sworkTimeCode) {
		// TODO Auto-generated method stub
		return null;
	}

}
