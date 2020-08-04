/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.company;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComNormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComNormalSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company.KshstComNormalSet;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company.KshstComNormalSetPK;

/**
 * The Class JpaComNormalSettingRepository.
 */
@Stateless
public class JpaComNormalSettingRepository extends JpaRepository
		implements ComNormalSettingRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * ComNormalSettingRepository#create(nts.uk.ctx.at.shared.dom.statutory.
	 * worktime.companyNew.ComNormalSetting)
	 */
	@Override
	public void create(ComNormalSetting setting) {
		KshstComNormalSet entity = new KshstComNormalSet();
		setting.saveToMemento(new JpaComNormalSettingSetMemento(entity));
		this.commandProxy().insert(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * ComNormalSettingRepository#update(nts.uk.ctx.at.shared.dom.statutory.
	 * worktime.companyNew.ComNormalSetting)
	 */
	@Override
	public void update(ComNormalSetting setting) {
		KshstComNormalSet entity = this.queryProxy()
				.find(new KshstComNormalSetPK(setting.getCompanyId().v(), setting.getYear().v()),
						KshstComNormalSet.class)
				.get();
		setting.saveToMemento(new JpaComNormalSettingSetMemento(entity));
		this.commandProxy().update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * ComNormalSettingRepository#remove(java.lang.String, int)
	 */
	@Override
	public void remove(String companyId, int year) {
		this.commandProxy().remove(KshstComNormalSet.class,
				new KshstComNormalSetPK(companyId, year));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * ComNormalSettingRepository#find(java.lang.String, int)
	 */
	@Override
	public Optional<ComNormalSetting> find(String companyId, int year) {
		// Get info
		Optional<KshstComNormalSet> optEntity = this.queryProxy()
				.find(new KshstComNormalSetPK(companyId, year), KshstComNormalSet.class);

		// Check exist
		if (!optEntity.isPresent()) {
			return Optional.empty();
		}

		// Return
		return Optional
				.of(new ComNormalSetting(new JpaComNormalSettingGetMemento(optEntity.get())));
	}

}
