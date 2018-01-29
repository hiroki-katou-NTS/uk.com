package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.aggregateset;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.AggregateSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.AggregateSettingRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.aggregateset.KscstEstAggregateSet;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class JpaAggregateSettingRepository.
 */
@Stateless
public class JpaAggregateSettingRepository extends JpaRepository implements AggregateSettingRepository{

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.AggregateSettingRepository#findByCID(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public Optional<AggregateSetting> findByCID(CompanyId companyId) {
		return this.queryProxy().find(companyId, KscstEstAggregateSet.class).map(e -> toDomain(e));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.AggregateSettingRepository#update(nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.AggregateSetting)
	 */
	@Override
	public void update(AggregateSetting domain) {
		Optional<AggregateSetting> opt = this.findByCID(new CompanyId(AppContexts.user().companyId()));
		if(opt.isPresent()){
			this.commandProxy().remove(toEntity(domain));
		}
		this.commandProxy().insert(toEntity(domain));
	}
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kscst est aggregate set
	 */
	private KscstEstAggregateSet toEntity(AggregateSetting domain){
		KscstEstAggregateSet entity = new KscstEstAggregateSet();
		domain.saveToMemento(new JpaAggregateSettingSetMemento(entity));
		return entity;
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the aggregate setting
	 */
	private AggregateSetting toDomain(KscstEstAggregateSet entity){
		AggregateSetting domain = new AggregateSetting(new JpaAggregateSettingGetMemento(entity));
		return domain;
	}
}
