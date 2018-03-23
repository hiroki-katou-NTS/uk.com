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
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpRegLaborTime;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpRegLaborTimePK;

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
	public void update(WkpNormalSetting setting) {
		KshstWkpRegLaborTime entity = this.queryProxy()
				.find(new KshstWkpRegLaborTimePK(setting.getCompanyId().v(),
						setting.getWorkplaceId().v(), setting.getYear().v()),
						KshstWkpRegLaborTime.class)
				.get();
		setting.saveToMemento(new JpaWkpNormalSettingSetMemento(entity));
		this.commandProxy().update(entity);
	}

	@Override
	public Optional<WkpNormalSetting> find(String cid, String wkpId, int year) {
		// Get info
		Optional<KshstWkpRegLaborTime> optEntity = this.queryProxy()
				.find(new KshstWkpRegLaborTimePK(companyId, year), KshstWkpRegLaborTime.class);

		// Check exist
		if (!optEntity.isPresent()) {
			return Optional.empty();
		}

		// Return
		return Optional
				.of(new WkpNormalSetting(new JpaWkpNormalSettingGetMemento(optEntity.get())));
	}

	@Override
	public void add(WkpNormalSetting wkpNormalSetting) {
		KshstWkpRegLaborTime entity = new KshstWkpRegLaborTime();
		setting.saveToMemento(new JpaWkpNormalSettingSetMemento(entity));
		this.commandProxy().insert(entity);
	}

	@Override
	public void remove(String cid, String wkpId, int year) {
		this.commandProxy().remove(KshstWkpRegLaborTimePK.class,
				new KshstWkpRegLaborTimePK(companyId, year));
	}

}
