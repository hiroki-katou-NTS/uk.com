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
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedWorkSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedWorkSetPK;

/**
 * The Class JpaFixedWorkSettingRepository.
 */
@Stateless
public class JpaFixedWorkSettingRepository extends JpaRepository
		implements FixedWorkSettingRepository {

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository#find(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<FixedWorkSetting> find(String companyId, String workTimeCode) {
		//TODO 
		return null;
		//return this.findFixedSettingGroup(companyId, workTimeCode).map(entity -> this.toDomain(entity));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository#add(nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting)
	 */
	@Override
	public void add(FixedWorkSetting domain) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository#update(nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting)
	 */
	@Override
	public void update(FixedWorkSetting domain) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository#remove(java.lang.String, java.lang.String)
	 */
	@Override
	public void remove(String companyId, String workTimeCode) {
		this.commandProxy().remove(KshmtFixedWorkSet.class, new KshmtFixedWorkSetPK(companyId, workTimeCode));
	}

}
