/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.avgearn;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.insurance.avgearn.AvgEarnLevelMasterSetting;
import nts.uk.ctx.pr.core.dom.insurance.avgearn.AvgEarnLevelMasterSettingRepository;

/**
 * The Class JpaAvgEarnLevelMasterSettingRepository.
 */
@Stateless
public class JpaAvgEarnLevelMasterSettingRepository extends JpaRepository
		implements AvgEarnLevelMasterSettingRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.avgearn.
	 * AvgEarnLevelMasterSettingRepository#findAll(java.lang.String)
	 */
	@Override
	public List<AvgEarnLevelMasterSetting> findAll(String companyCode) {
		List<AvgEarnLevelMasterSetting> listAvgEarnLevelMasterSetting = new ArrayList<AvgEarnLevelMasterSetting>();
		listAvgEarnLevelMasterSetting.add(new AvgEarnLevelMasterSetting(1, 1, 1, 58000L, 63000L));
		listAvgEarnLevelMasterSetting.add(new AvgEarnLevelMasterSetting(2, 2, 1, 68000L, 73000L));
		listAvgEarnLevelMasterSetting.add(new AvgEarnLevelMasterSetting(3, 3, 1, 78000L, 83000L));
		listAvgEarnLevelMasterSetting.add(new AvgEarnLevelMasterSetting(4, 4, 1, 88000L, 93000L));
		listAvgEarnLevelMasterSetting.add(new AvgEarnLevelMasterSetting(5, 5, 1, 98000L, 101000L));
		listAvgEarnLevelMasterSetting.add(new AvgEarnLevelMasterSetting(6, 6, 2, 104000L, 121000L));
		return listAvgEarnLevelMasterSetting;
	}

}
