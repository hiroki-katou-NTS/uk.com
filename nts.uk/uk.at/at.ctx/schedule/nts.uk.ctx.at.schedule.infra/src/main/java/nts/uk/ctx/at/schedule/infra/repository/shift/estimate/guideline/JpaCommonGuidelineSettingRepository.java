/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.guideline;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.estimate.guideline.CommonGuidelineSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.guideline.CommonGuidelineSettingRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.guideline.KscstEstGuideSetting;

/**
 * The Class JpaCommonGuidelineSettingRepository.
 */
@Stateless
public class JpaCommonGuidelineSettingRepository extends JpaRepository
		implements CommonGuidelineSettingRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.guideline.
	 * CommonGuidelineSettingRepository#add(nts.uk.ctx.at.schedule.dom.shift.
	 * estimate.guideline.CommonGuidelineSetting)
	 */
	@Override
	public void add(CommonGuidelineSetting domain) {
		// Create entity
		KscstEstGuideSetting entity = new KscstEstGuideSetting();

		// Push data
		domain.saveToMemento(new JpaCommonGuidelineSettingSetMemento(entity));

		// Insert into db
		this.commandProxy().insert(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.guideline.
	 * CommonGuidelineSettingRepository#update(nts.uk.ctx.at.schedule.dom.shift.
	 * estimate.guideline.CommonGuidelineSetting)
	 */
	@Override
	public void update(CommonGuidelineSetting domain) {
		// Create entity
		KscstEstGuideSetting entity = new KscstEstGuideSetting();

		// Push data
		domain.saveToMemento(new JpaCommonGuidelineSettingSetMemento(entity));

		// Update into db
		this.commandProxy().update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.guideline.
	 * CommonGuidelineSettingRepository#findByCompanyId(java.lang.String)
	 */
	@Override
	public Optional<CommonGuidelineSetting> findByCompanyId(String companyId) {
		// Find records.
		Optional<KscstEstGuideSetting> optKscstEstGuideSetting = this.queryProxy().find(companyId,
				KscstEstGuideSetting.class);

		// Check exist.
		if (!optKscstEstGuideSetting.isPresent()) {
			return Optional.empty();
		}

		// Return domain.
		return Optional.of(new CommonGuidelineSetting(
				new JpaCommonGuidelineSettingGetMemento(optKscstEstGuideSetting.get())));
	}

}
