/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.automaticcalculation;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpJobAutoCalSetting;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpJobAutoCalSettingRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.autocalsetting.KshmtAutoWkpJobCal;
import nts.uk.ctx.at.schedule.infra.entity.shift.autocalsetting.KshmtAutoWkpJobCalPK;

/**
 * The Class JpaWkpJobAutoCalSettingRepository.
 */
@Stateless
public class JpaWkpJobAutoCalSettingRepository extends JpaRepository implements WkpJobAutoCalSettingRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.
	 * WkpJobAutoCalSettingRepository#update(nts.uk.ctx.at.schedule.dom.shift.
	 * autocalsetting.WkpJobAutoCalSetting)
	 */
	@Override
	public void update(WkpJobAutoCalSetting wkpJobAutoCalSetting) {
		Optional<KshmtAutoWkpJobCal> optional = this.queryProxy()
				.find(new KshmtAutoWkpJobCalPK(wkpJobAutoCalSetting.getCompanyId().v(),
						wkpJobAutoCalSetting.getJobId().v(), wkpJobAutoCalSetting.getJobId().v()),
						KshmtAutoWkpJobCal.class);

		if (!optional.isPresent()) {
			throw new RuntimeException("Auto wkp Job not existed.");
		}

		KshmtAutoWkpJobCal entity = optional.get();
		wkpJobAutoCalSetting.saveToMemento(new JpaWkpJobAutoCalSettingSetMemento(entity));
		this.commandProxy().update(entity);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.
	 * WkpJobAutoCalSettingRepository#getAllWkpJobAutoCalSetting(java.lang.
	 * String, java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<WkpJobAutoCalSetting> getAllWkpJobAutoCalSetting(String companyId, String wkpId, String jobId) {
		KshmtAutoWkpJobCalPK kshmtAutoJobCalSetPK = new KshmtAutoWkpJobCalPK(companyId, wkpId, jobId);

		Optional<KshmtAutoWkpJobCal> optKshmtAutoWkpJobCal = this.queryProxy().find(kshmtAutoJobCalSetPK,
				KshmtAutoWkpJobCal.class);

		if (!optKshmtAutoWkpJobCal.isPresent()) {
			return Optional.empty();
		}

		return Optional
				.of(new WkpJobAutoCalSetting(new JpaWkpJobAutoCalSettingGetMemento(optKshmtAutoWkpJobCal.get())));
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpJobAutoCalSettingRepository#delete(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void delete(String cid, String wkpId, String jobId) {
		this.commandProxy().remove(KshmtAutoWkpJobCal.class, new KshmtAutoWkpJobCalPK(cid, wkpId, jobId));

	}

}
