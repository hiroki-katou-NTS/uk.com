/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.workplace;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpNormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpNormalSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpNormalSet;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpNormalSetPK;

/**
 * The Class JpaWkpNormalSettingRepository.
 */
@Stateless
public class JpaWkpNormalSettingRepository extends JpaRepository
		implements WkpNormalSettingRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * WkpNormalSettingRepository#update(nts.uk.ctx.at.shared.dom.statutory.
	 * worktime.companyNew.WkpNormalSetting)
	 */
	@Override
	public void update(WkpNormalSetting domain) {
		KshstWkpNormalSet entity = this.queryProxy()
				.find(new KshstWkpNormalSetPK(domain.getCompanyId().v(),
						domain.getWorkplaceId().v(), domain.getYear().v()), KshstWkpNormalSet.class)
				.get();
		domain.saveToMemento(new JpaWkpNormalSettingSetMemento(entity));
		this.commandProxy().update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.
	 * WkpNormalSettingRepository#find(java.lang.String, java.lang.String, int)
	 */
	@Override
	public Optional<WkpNormalSetting> find(String cid, String wkpId, int year) {
		// Get info
		Optional<KshstWkpNormalSet> optEntity = this.queryProxy()
				.find(new KshstWkpNormalSetPK(cid, wkpId, year), KshstWkpNormalSet.class);

		// Check exist
		if (!optEntity.isPresent()) {
			return Optional.empty();
		}

		// Return
		return Optional
				.of(new WkpNormalSetting(new JpaWkpNormalSettingGetMemento(optEntity.get())));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.
	 * WkpNormalSettingRepository#add(nts.uk.ctx.at.shared.dom.statutory.
	 * worktime.workplaceNew.WkpNormalSetting)
	 */
	@Override
	public void add(WkpNormalSetting domain) {
		KshstWkpNormalSet entity = new KshstWkpNormalSet();
		domain.saveToMemento(new JpaWkpNormalSettingSetMemento(entity));
		this.commandProxy().insert(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.
	 * WkpNormalSettingRepository#remove(java.lang.String, java.lang.String,
	 * int)
	 */
	@Override
	public void remove(String cid, String wkpId, int year) {
		this.commandProxy().remove(KshstWkpNormalSet.class,
				new KshstWkpNormalSetPK(cid, wkpId, year));
	}

}
