/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.company;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComDeforLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComDeforLaborSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company.KshstComDeforLarSet;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company.KshstComDeforLarSetPK;

/**
 * The Class JpaComDeforLaborSettingRepository.
 */
@Stateless
public class JpaComDeforLaborSettingRepository extends JpaRepository
		implements ComDeforLaborSettingRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * ComDeforLaborSettingRepository#create(nts.uk.ctx.at.shared.dom.statutory.
	 * worktime.companyNew.ComDeforLaborSetting)
	 */
	@Override
	public void insert(ComDeforLaborSetting setting) {
		KshstComDeforLarSet entity = new KshstComDeforLarSet();
		setting.saveToMemento(new JpaComDeforLaborSettingSetMemento(entity));
		this.commandProxy().insert(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * ComDeforLaborSettingRepository#update(nts.uk.ctx.at.shared.dom.statutory.
	 * worktime.companyNew.ComDeforLaborSetting)
	 */
	@Override
	public void update(ComDeforLaborSetting setting) {
		KshstComDeforLarSet entity = this.queryProxy()
				.find(new KshstComDeforLarSetPK(setting.getCompanyId().v(), setting.getYear().v()),
						KshstComDeforLarSet.class)
				.get();
		setting.saveToMemento(new JpaComDeforLaborSettingSetMemento(entity));
		this.commandProxy().update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * ComDeforLaborSettingRepository#remove(java.lang.String, int)
	 */
	@Override
	public void remove(String companyId, int year) {
		this.commandProxy().remove(KshstComDeforLarSet.class,
				new KshstComDeforLarSetPK(companyId, year));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * ComDeforLaborSettingRepository#find(java.lang.String, int)
	 */
	@Override
	public Optional<ComDeforLaborSetting> find(String companyId, int year) {
		// Get info
		Optional<KshstComDeforLarSet> optEntity = this.queryProxy()
				.find(new KshstComDeforLarSetPK(companyId, year), KshstComDeforLarSet.class);

		// Check exist
		if (!optEntity.isPresent()) {
			return Optional.empty();
		}

		// Return
		return Optional.of(
				new ComDeforLaborSetting(new JpaComDeforLaborSettingGetMemento(optEntity.get())));
	}

}
