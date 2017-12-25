/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.predset;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtPredTimeSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtPredTimeSetPK;

/**
 * The Class JpaPredetemineTimeSetRepository.
 */
@Stateless
public class JpaPredetemineTimeSetRepository extends JpaRepository
		implements PredetemineTimeSettingRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.
	 * PredetemineTimeSettingRepository#findByWorkTimeCode(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Optional<PredetemineTimeSetting> findByWorkTimeCode(String companyId,
			String workTimeCode) {

		// Query
		Optional<KshmtPredTimeSet> optionalEntityTimeSet = this.queryProxy()
				.find(new KshmtPredTimeSetPK(companyId, workTimeCode), KshmtPredTimeSet.class);

		// Check exist
		if (!optionalEntityTimeSet.isPresent()) {
			return Optional.empty();
		}

		return Optional.ofNullable(new PredetemineTimeSetting(
				new JpaPredetemineTimeSettingGetMemento(optionalEntityTimeSet.get())));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.
	 * PredetemineTimeSettingRepository#save(nts.uk.ctx.at.shared.dom.worktime.
	 * predset.PredetemineTimeSetting)
	 */
	@Override
	public void save(PredetemineTimeSetting domain) {
		// Query
		Optional<KshmtPredTimeSet> optionalEntityTimeSet = this.queryProxy().find(
				new KshmtPredTimeSetPK(domain.getCompanyId(), domain.getWorkTimeCode().v()),
				KshmtPredTimeSet.class);

		KshmtPredTimeSet entity = optionalEntityTimeSet.orElse(new KshmtPredTimeSet());

		// Check exist
		if (optionalEntityTimeSet.isPresent()) {
			// Update into DB
			domain.saveToMemento(new JpaPredetemineTimeSettingSetMemento(entity));
			this.commandProxy().update(entity);
			return;
		}

		// Insert into DB
		domain.saveToMemento(new JpaPredetemineTimeSettingSetMemento(entity));
		this.commandProxy().insert(entity);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.
	 * PredetemineTimeSettingRepository#remove(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void remove(String companyId, String workTimeCode) {
		this.commandProxy().remove(KshmtPredTimeSet.class,
				new KshmtPredTimeSetPK(companyId, workTimeCode));
	}

}
