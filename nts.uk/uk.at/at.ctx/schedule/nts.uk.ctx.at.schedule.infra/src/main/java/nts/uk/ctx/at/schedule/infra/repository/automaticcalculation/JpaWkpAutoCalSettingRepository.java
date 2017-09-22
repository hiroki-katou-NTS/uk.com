/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.automaticcalculation;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpAutoCalSetting;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpAutoCalSettingRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.autocalsetting.KshmtAutoWkpCalSet;
import nts.uk.ctx.at.schedule.infra.entity.shift.autocalsetting.KshmtAutoWkpCalSetPK;

/**
 * The Class JpaWkpAutoCalSettingRepository.
 */
@Stateless
public class JpaWkpAutoCalSettingRepository extends JpaRepository implements WkpAutoCalSettingRepository{

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpAutoCalSettingRepository#update(nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpAutoCalSetting)
	 */
	@Override
	public void update(WkpAutoCalSetting wkpAutoCalSetting) {
		Optional<KshmtAutoWkpCalSet> optional = this.queryProxy().find(
				new KshmtAutoWkpCalSetPK(wkpAutoCalSetting.getCompanyId().v(), wkpAutoCalSetting.getWkpId().v()),
				KshmtAutoWkpCalSet.class);

		if (!optional.isPresent()) {
			throw new RuntimeException("Auto wkp not existed.");
		}

		KshmtAutoWkpCalSet entity = optional.get();
		wkpAutoCalSetting.saveToMemento(new JpaWkpAutoCalSettingSetMemento(entity));
		this.commandProxy().update(entity);
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpAutoCalSettingRepository#getAllWkpAutoCalSetting(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<WkpAutoCalSetting> getAllWkpAutoCalSetting(String companyId, String wkpId) {
		KshmtAutoWkpCalSetPK kshmtAutoWkpCalSetPK = new KshmtAutoWkpCalSetPK(companyId, wkpId);

		Optional<KshmtAutoWkpCalSet> optKshmtAutoWkpCalSet = this.queryProxy().find(kshmtAutoWkpCalSetPK, KshmtAutoWkpCalSet.class);

		if (!optKshmtAutoWkpCalSet.isPresent()) {
			return Optional.empty();
		}
		
		return Optional.of(new WkpAutoCalSetting(new JpaWkpAutoCalSettingGetMemento(optKshmtAutoWkpCalSet.get())));
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpAutoCalSettingRepository#delete(java.lang.String, java.lang.String)
	 */
	@Override
	public void delete(String cid, String wkpId) {
		this.commandProxy().remove(KshmtAutoWkpCalSet.class, new KshmtAutoWkpCalSetPK(cid, wkpId));

	}

}
