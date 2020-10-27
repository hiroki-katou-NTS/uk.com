/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.usagesetting;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.estimate.usagesetting.UsageSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.usagesetting.UsageSettingRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.usagesetting.KscmtEstUsage;

/**
 * The Class JpaUsageSettingRepository.
 */
@Stateless
public class JpaUsageSettingRepository extends JpaRepository implements UsageSettingRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.guideline.
	 * UsageSettingRepository#add(nts.uk.ctx.at.schedule.dom.shift.
	 * estimate.guideline.UsageSetting)
	 */
	@Override
	public void add(UsageSetting domain) {
		// Create entity
		KscmtEstUsage entity = new KscmtEstUsage();

		// Push data
		domain.saveToMemento(new JpaUsageSettingSetMemento(entity));

		// Insert into db
		this.commandProxy().insert(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.guideline.
	 * UsageSettingRepository#update(nts.uk.ctx.at.schedule.dom.shift.
	 * estimate.guideline.UsageSetting)
	 */
	@Override
	public void update(UsageSetting domain) {
		// Create entity
		KscmtEstUsage entity = new KscmtEstUsage();

		// Push data
		domain.saveToMemento(new JpaUsageSettingSetMemento(entity));

		// Update into db
		this.commandProxy().update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.guideline.
	 * UsageSettingRepository#findByCompanyId(java.lang.String)
	 */
	@Override
	public Optional<UsageSetting> findByCompanyId(String companyId) {
		// Find records.
		Optional<KscmtEstUsage> optKscmtEstUsage = this.queryProxy().find(companyId,
				KscmtEstUsage.class);

		// Check exist.
		if (!optKscmtEstUsage.isPresent()) {
			return Optional.empty();
		}

		// Return domain.
		return Optional
				.of(new UsageSetting(new JpaUsageSettingGetMemento(optKscmtEstUsage.get())));
	}

}
