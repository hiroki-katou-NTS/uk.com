/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.employment.statutory.worktime;

import javax.ejb.Stateless;

import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.UsageUnitSetting;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.UsageUnitSettingRepository;

/**
 * The Class JpaUsageUnitSettingRepository.
 */
@Stateless
public class JpaUsageUnitSettingRepository implements UsageUnitSettingRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.
	 * UsageUnitSettingRepository#update(nts.uk.ctx.at.shared.dom.employment.
	 * statutory.worktime.UsageUnitSetting)
	 */
	@Override
	public void update(UsageUnitSetting setting) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.
	 * UsageUnitSettingRepository#find(java.lang.String)
	 */
	@Override
	public UsageUnitSetting find(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

}
