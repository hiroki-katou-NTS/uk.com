/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.workplace;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpDeforLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpDeforLaborSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpDeforLarSet;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpDeforLarSetPK;

/**
 * The Class JpaWkpDeforLaborSettingRepository.
 */
@Stateless
public class JpaWkpDeforLaborSettingRepository extends JpaRepository
		implements WkpDeforLaborSettingRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * WkpDeforLaborSettingRepository#create(nts.uk.ctx.at.shared.dom.statutory.
	 * worktime.companyNew.WkpDeforLaborSetting)
	 */
	@Override
	public void add(WkpDeforLaborSetting setting) {
		KshstWkpDeforLarSet entity = new KshstWkpDeforLarSet();
		setting.saveToMemento(new JpaWkpDeforLaborSettingSetMemento(entity));
		this.commandProxy().insert(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * WkpDeforLaborSettingRepository#update(nts.uk.ctx.at.shared.dom.statutory.
	 * worktime.companyNew.WkpDeforLaborSetting)
	 */
	@Override
	public void update(WkpDeforLaborSetting setting) {
		KshstWkpDeforLarSet entity = this.queryProxy()
				.find(new KshstWkpDeforLarSetPK(setting.getCompanyId().v(),
						setting.getWorkplaceId().v(), setting.getYear().v()),
						KshstWkpDeforLarSet.class)
				.get();
		setting.saveToMemento(new JpaWkpDeforLaborSettingSetMemento(entity));
		this.commandProxy().update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.
	 * WkpDeforLaborSettingRepository#find(java.lang.String, java.lang.String,
	 * int)
	 */
	@Override
	public Optional<WkpDeforLaborSetting> find(String cid, String wkpId, int year) {
		// Get info
		Optional<KshstWkpDeforLarSet> optEntity = this.queryProxy()
				.find(new KshstWkpDeforLarSetPK(cid, wkpId, year), KshstWkpDeforLarSet.class);

		// Check exist
		if (!optEntity.isPresent()) {
			return Optional.empty();
		}

		// Return
		return Optional.of(
				new WkpDeforLaborSetting(new JpaWkpDeforLaborSettingGetMemento(optEntity.get())));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.
	 * WkpDeforLaborSettingRepository#remove(java.lang.String, java.lang.String,
	 * int)
	 */
	@Override
	public void remove(String cid, String wkpId, int year) {
		this.commandProxy().remove(KshstWkpDeforLarSet.class,
				new KshstWkpDeforLarSetPK(cid, wkpId, year));
	}

}
