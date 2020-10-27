/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.aggregateset;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.AggregateSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.AggregateSettingRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.aggregateset.KscmtEstAggregate;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaAggregateSettingRepository.
 */
@Stateless
public class JpaAggregateSettingRepository extends JpaRepository implements AggregateSettingRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.
	 * AggregateSettingRepository#findByCID(nts.uk.ctx.at.shared.dom.common.
	 * CompanyId)
	 */
	@Override
	public Optional<AggregateSetting> findByCID(CompanyId companyId) {
		return this.queryProxy().find(companyId, KscmtEstAggregate.class).map(e -> toDomain(e));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.
	 * AggregateSettingRepository#update(nts.uk.ctx.at.schedule.dom.shift.
	 * estimate.aggregateset.AggregateSetting)
	 */
	@Override
	public void save(AggregateSetting domain) {
		// find entity
		Optional<KscmtEstAggregate> opt = this.queryProxy().find(domain.getCompanyId().v(),
				KscmtEstAggregate.class);

		// update mode
		if (opt.isPresent()) {
			this.commandProxy().update(toEntity(domain, opt.get()));
		}
		// add mode
		else {
			this.commandProxy().insert(toEntity(domain, new KscmtEstAggregate()));
		}
	}

	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @param entity the entity
	 * @return the kscst est aggregate set
	 */
	private KscmtEstAggregate toEntity(AggregateSetting domain, KscmtEstAggregate entity) {
		domain.saveToMemento(new JpaAggregateSettingSetMemento(entity));
		return entity;
	}

	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the aggregate setting
	 */
	private AggregateSetting toDomain(KscmtEstAggregate entity) {
		AggregateSetting domain = new AggregateSetting(new JpaAggregateSettingGetMemento(entity));
		return domain;
	}
}
