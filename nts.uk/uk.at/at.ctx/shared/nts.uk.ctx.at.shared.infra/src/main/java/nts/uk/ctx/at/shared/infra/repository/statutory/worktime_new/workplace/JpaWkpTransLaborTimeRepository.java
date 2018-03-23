/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.workplace;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.WkpTransLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.WkpTransLaborTimeRepository;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company.KshstWkpTransLabTime;

/**
 * The Class JpaWkpTransLaborTimeRepository.
 */
@Stateless
public class JpaWkpTransLaborTimeRepository extends JpaRepository
		implements WkpTransLaborTimeRepository {

	/*
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * WkpTransLaborTimeRepository#create(nts.uk.ctx.at.shared.dom.statutory.
	 * worktime.companyNew.WkpTransLaborTime)
	 */
	@Override
	public void create(WkpTransLaborTime setting) {
		KshstWkpTransLabTime entity = new KshstWkpTransLabTime();
		setting.saveToMemento(new JpaWkpTransLaborTimeSetMemento(entity));
		this.commandProxy().insert(entity);
	}

	/*
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * WkpTransLaborTimeRepository#update(nts.uk.ctx.at.shared.dom.statutory.
	 * worktime.companyNew.WkpTransLaborTime)
	 */
	@Override
	public void update(WkpTransLaborTime setting) {
		KshstWkpTransLabTime entity = this.queryProxy().find(setting.getCompanyId().v(), KshstWkpTransLabTime.class).get();
		setting.saveToMemento(new JpaWkpTransLaborTimeSetMemento(entity));
		this.commandProxy().update(this.toEntity(setting));
	}

	/*
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * WkpTransLaborTimeRepository#remove(java.lang.String)
	 */
	@Override
	public void remove(String companyId) {
		this.commandProxy().remove(KshstWkpTransLabTime.class, companyId);
	}

	/*
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * WkpTransLaborTimeRepository#find(java.lang.String)
	 */
	@Override
	public Optional<WkpTransLaborTime> find(String companyId) {

		Optional<KshstWkpTransLabTime> optEntity = this.queryProxy().find(companyId,
				KshstWkpTransLabTime.class);

		// Check exist
		if (!optEntity.isPresent()) {
			return Optional.empty();
		}

		return Optional.ofNullable(this.toDomain(optEntity.get()));
	}

	/**
	 * To entity.
	 *
	 * @param domain
	 *            the domain
	 * @return the kshst com trans lab time
	 */
	private KshstWkpTransLabTime toEntity(WkpTransLaborTime domain) {
		KshstWkpTransLabTime entity = new KshstWkpTransLabTime();
		domain.saveToMemento(new JpaWkpTransLaborTimeSetMemento(entity));
		return entity;
	}

	/**
	 * To domain.
	 *
	 * @param entity
	 *            the entities
	 * @return the com trans labor time
	 */
	private WkpTransLaborTime toDomain(KshstWkpTransLabTime entity) {
		return new WkpTransLaborTime(new JpaWkpTransLaborTimeGetMemento(entity));
	}
}
