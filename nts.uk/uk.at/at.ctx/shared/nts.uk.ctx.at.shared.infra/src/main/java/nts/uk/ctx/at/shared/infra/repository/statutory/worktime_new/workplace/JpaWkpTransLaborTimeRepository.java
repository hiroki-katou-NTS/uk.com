/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.workplace;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpTransLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpTransLaborTimeRepository;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpTransLabTime;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpTransLabTimePK;

/**
 * The Class JpaWkpTransLaborTimeRepository.
 */
@Stateless
public class JpaWkpTransLaborTimeRepository extends JpaRepository
		implements WkpTransLaborTimeRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.
	 * WkpTransLaborTimeRepository#find(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<WkpTransLaborTime> find(String cid, String wkpId) {
		// Get info
		Optional<KshstWkpTransLabTime> optEntity = this.queryProxy()
				.find(new KshstWkpTransLabTimePK(cid, wkpId), KshstWkpTransLabTime.class);

		// Check exist
		if (!optEntity.isPresent()) {
			return Optional.empty();
		}

		// Return
		return Optional
				.of(new WkpTransLaborTime(new JpaWkpTransLaborTimeGetMemento(optEntity.get())));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.
	 * WkpTransLaborTimeRepository#add(nts.uk.ctx.at.shared.dom.statutory.
	 * worktime.workplaceNew.WkpTransLaborTime)
	 */
	@Override
	public void add(WkpTransLaborTime domain) {
		KshstWkpTransLabTime entity = new KshstWkpTransLabTime();
		domain.saveToMemento(new JpaWkpTransLaborTimeSetMemento(entity));
		this.commandProxy().insert(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.
	 * WkpTransLaborTimeRepository#remove(java.lang.String, java.lang.String)
	 */
	@Override
	public void remove(String cid, String wkpId) {
		this.commandProxy().remove(KshstWkpTransLabTime.class,
				new KshstWkpTransLabTimePK(cid, wkpId));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.
	 * WkpTransLaborTimeRepository#update(nts.uk.ctx.at.shared.dom.statutory.
	 * worktime.workplaceNew.WkpTransLaborTime)
	 */
	@Override
	public void update(WkpTransLaborTime domain) {
		KshstWkpTransLabTime entity = this.queryProxy().find(
				new KshstWkpTransLabTimePK(domain.getCompanyId().v(), domain.getWorkplaceId().v()),
				KshstWkpTransLabTime.class).get();
		domain.saveToMemento(new JpaWkpTransLaborTimeSetMemento(entity));
		this.commandProxy().update(entity);
	}
}
