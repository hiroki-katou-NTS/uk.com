/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.estcomparison;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.estimate.estcomparison.EstimateComparison;
import nts.uk.ctx.at.schedule.dom.shift.estimate.estcomparison.EstimateComparisonRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.estcomparison.KscstEstComparison;

/**
 * The Class JpaEstComparisonRepository.
 */
@Stateless
public class JpaEstComparisonRepository extends JpaRepository implements EstimateComparisonRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.estcomparison.
	 * EstimateComparisonRepository#add(nts.uk.ctx.at.schedule.dom.shift.
	 * estimate.estcomparison.EstimateComparison)
	 */
	@Override
	public void add(EstimateComparison estimateComparison) {
		KscstEstComparison entity = new KscstEstComparison();
		estimateComparison.saveToMemento(new JpaEstComparisonSetMemento(entity));
		this.commandProxy().insert(entity);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.estcomparison.
	 * EstimateComparisonRepository#update(nts.uk.ctx.at.schedule.dom.shift.
	 * estimate.estcomparison.EstimateComparison)
	 */
	@Override
	public void update(EstimateComparison estimateComparison) {
		Optional<KscstEstComparison> opt = this.queryProxy().find(estimateComparison.getCompanyId(),
				KscstEstComparison.class);
		KscstEstComparison entity = new KscstEstComparison();
		if (opt.isPresent()) {
			entity = opt.get();
		}
		estimateComparison.saveToMemento(new JpaEstComparisonSetMemento(entity));
		this.commandProxy().update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.estcomparison.
	 * EstimateComparisonRepository#findByCompanyId(java.lang.String)
	 */
	@Override
	public Optional<EstimateComparison> findByCompanyId(String companyId) {
		Optional<KscstEstComparison> optKscstEstComparison = this.queryProxy().find(companyId,
				KscstEstComparison.class);

		// Check exist
		if (!optKscstEstComparison.isPresent()) {
			return Optional.empty();
		}

		return Optional.of(new EstimateComparison(
				new JpaEstComparisonGetMemento(optKscstEstComparison.get())));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.estcomparison.EstimateComparisonRepository#remove(java.lang.String)
	 */
	@Override
	public void remove(String companyId) {
		this.commandProxy().remove(KscstEstComparison.class, companyId);
		this.getEntityManager().flush();
	}

}
