/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workingform;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.workingform.SettingFlexWork;
import nts.uk.ctx.at.shared.dom.workingform.SettingFlexWorkRepository;
import nts.uk.ctx.at.shared.infra.entity.workingform.KshstSettingFlexWork;

/**
 * The Class JpaSettingFlexWorkRepository.
 */
@Stateless
public class JpaSettingFlexWorkRepository extends JpaRepository implements SettingFlexWorkRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingform.SettingFlexWorkRepository#update(nts
	 * .uk.ctx.at.shared.dom.workingform.SettingFlexWork)
	 */
	@Override
	public void update(SettingFlexWork setting) {
		KshstSettingFlexWork entity = this.queryProxy().find(setting.getCompanyId(), KshstSettingFlexWork.class).get();
		entity.setFlexWorkManaging(setting.isFlexWorkManaging() ? 1 : 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingform.SettingFlexWorkRepository#find(java.
	 * lang.String)
	 */
	@Override
	public SettingFlexWork find(String companyId) {
		KshstSettingFlexWork entity = this.queryProxy().find(companyId, KshstSettingFlexWork.class).get();
		return new SettingFlexWork(entity.getCid(), entity.getFlexWorkManaging() == 1 ? true : false);
	}

}
