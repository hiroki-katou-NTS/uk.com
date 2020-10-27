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
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.estcomparison.KscmtEstComparison;

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
		KscmtEstComparison entity = new KscmtEstComparison();
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
		Optional<KscmtEstComparison> opt = this.queryProxy().find(estimateComparison.getCompanyId(),
				KscmtEstComparison.class);
		KscmtEstComparison entity = new KscmtEstComparison();
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
		Optional<KscmtEstComparison> optKscmtEstComparison = this.queryProxy().find(companyId,
				KscmtEstComparison.class);

		// Check exist
		if (!optKscmtEstComparison.isPresent()) {
			return Optional.empty();
		}

		return Optional.of(new EstimateComparison(
				new JpaEstComparisonGetMemento(optKscmtEstComparison.get())));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.estcomparison.EstimateComparisonRepository#remove(java.lang.String)
	 */
	@Override
	public void remove(String companyId) {
		this.commandProxy().remove(KscmtEstComparison.class, companyId);
		this.getEntityManager().flush();
	}

}
