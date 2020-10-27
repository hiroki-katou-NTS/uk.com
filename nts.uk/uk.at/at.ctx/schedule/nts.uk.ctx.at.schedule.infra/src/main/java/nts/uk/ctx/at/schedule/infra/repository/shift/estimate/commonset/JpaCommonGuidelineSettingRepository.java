/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.commonset;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.estimate.commonset.CommonGuidelineSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.commonset.CommonGuidelineSettingRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.commonset.KscmtEstCommon;

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
		KscmtEstCommon entity = new KscmtEstCommon();

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
		KscmtEstCommon entity = new KscmtEstCommon();

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
		Optional<KscmtEstCommon> optKscstEstGuideSetting = this.queryProxy().find(companyId,
				KscmtEstCommon.class);

		// Check exist.
		if (!optKscstEstGuideSetting.isPresent()) {
			return Optional.empty();
		}

		// Return domain.
		return Optional.of(new CommonGuidelineSetting(
				new JpaCommonGuidelineSettingGetMemento(optKscstEstGuideSetting.get())));
	}

}
