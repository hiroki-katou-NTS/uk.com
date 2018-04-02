/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.company;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComFlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComFlexSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company.KshstComFlexSet;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company.KshstComFlexSetPK;

/**
 * The Class JpaComFlexSettingRepository.
 */
@Stateless
public class JpaComFlexSettingRepository extends JpaRepository implements ComFlexSettingRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * ComFlexSettingRepository#create(nts.uk.ctx.at.shared.dom.statutory.
	 * worktime.companyNew.ComFlexSetting)
	 */
	@Override
	public void create(ComFlexSetting setting) {
		KshstComFlexSet entity = new KshstComFlexSet();
		setting.saveToMemento(new JpaComFlexSettingSetMemento(entity));
		commandProxy().insert(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * ComFlexSettingRepository#update(nts.uk.ctx.at.shared.dom.statutory.
	 * worktime.companyNew.ComFlexSetting)
	 */
	@Override
	public void update(ComFlexSetting setting) {
		KshstComFlexSet entity = this.queryProxy()
				.find(new KshstComFlexSetPK(setting.getCompanyId().v(), setting.getYear().v()),
						KshstComFlexSet.class)
				.get();
		setting.saveToMemento(new JpaComFlexSettingSetMemento(entity));
		this.commandProxy().update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * ComFlexSettingRepository#remove(java.lang.String, int)
	 */
	@Override
	public void remove(String companyId, int year) {
		this.commandProxy().remove(KshstComFlexSet.class, new KshstComFlexSetPK(companyId, year));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * ComFlexSettingRepository#find(java.lang.String, int)
	 */
	@Override
	public Optional<ComFlexSetting> find(String companyId, int year) {
		// Get info
		Optional<KshstComFlexSet> optEntity = this.queryProxy()
				.find(new KshstComFlexSetPK(companyId, year), KshstComFlexSet.class);

		// Check exist
		if (!optEntity.isPresent()) {
			return Optional.empty();
		}

		// Return
		return Optional.of(new ComFlexSetting(new JpaComFlexSettingGetMemento(optEntity.get())));
	}

}
