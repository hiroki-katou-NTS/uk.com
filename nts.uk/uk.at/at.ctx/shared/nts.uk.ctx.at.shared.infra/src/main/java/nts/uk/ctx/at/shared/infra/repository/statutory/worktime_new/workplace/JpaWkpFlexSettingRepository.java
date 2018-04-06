/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.workplace;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpFlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpFlexSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpFlexSet;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpFlexSetPK;

/**
 * The Class JpaWkpFlexSettingRepository.
 */
@Stateless
public class JpaWkpFlexSettingRepository extends JpaRepository implements WkpFlexSettingRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * WkpFlexSettingRepository#create(nts.uk.ctx.at.shared.dom.statutory.
	 * worktime.companyNew.WkpFlexSetting)
	 */
	@Override
	public void add(WkpFlexSetting setting) {
		KshstWkpFlexSet entity = new KshstWkpFlexSet();
		setting.saveToMemento(new JpaWkpFlexSettingSetMemento(entity));
		commandProxy().insert(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * WkpFlexSettingRepository#update(nts.uk.ctx.at.shared.dom.statutory.
	 * worktime.companyNew.WkpFlexSetting)
	 */
	@Override
	public void update(WkpFlexSetting setting) {
		KshstWkpFlexSet entity = this
				.queryProxy().find(
						new KshstWkpFlexSetPK(setting.getCompanyId().v(),
								setting.getWorkplaceId().v(), setting.getYear().v()),
						KshstWkpFlexSet.class)
				.get();
		setting.saveToMemento(new JpaWkpFlexSettingSetMemento(entity));
		this.commandProxy().update(entity);
	}

	@Override
	public Optional<WkpFlexSetting> find(String cid, String wkpId, int year) {
		// Get info
		Optional<KshstWkpFlexSet> optEntity = this.queryProxy()
				.find(new KshstWkpFlexSetPK(cid, wkpId, year), KshstWkpFlexSet.class);

		// Check exist
		if (!optEntity.isPresent()) {
			return Optional.empty();
		}

		// Return
		return Optional.of(new WkpFlexSetting(new JpaWkpFlexSettingGetMemento(optEntity.get())));
	}

	@Override
	public void remove(String cid, String wkpId, int year) {
		this.commandProxy().remove(KshstWkpFlexSet.class,
				new KshstWkpFlexSetPK(cid, wkpId, year));
	}

}
