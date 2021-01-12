package nts.uk.ctx.at.record.pubimp.executionstatusmanage.optionalperiodprocess;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodTarget;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodTargetRepository;
import nts.uk.ctx.at.record.pub.executionstatusmanage.optionalperiodprocess.AggrPeriodTargetPub;

/**
 * The Class AggrPeriodTargetPubImpl.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AggrPeriodTargetPubImpl implements AggrPeriodTargetPub {

	/** The repo. */
	@Inject
	private AggrPeriodTargetRepository repo;
	
	/**
	 * Find all.
	 *
	 * @param aggrId the aggr id
	 * @return the list
	 */
	@Override
	public List<AggrPeriodTarget> findAll(String aggrId) {
		return this.repo.findAll(aggrId);
	}

	/**
	 * Adds the target.
	 *
	 * @param target the target
	 */
	@Override
	public void addTarget(List<AggrPeriodTarget> target) {
		this.repo.addTarget(target);
	}

	/**
	 * Find by aggr.
	 *
	 * @param aggrId the aggr id
	 * @return the optional
	 */
	@Override
	public Optional<AggrPeriodTarget> findByAggr(String aggrId) {
		return this.repo.findByAggr(aggrId);
	}

	/**
	 * Update excution.
	 *
	 * @param target the target
	 */
	@Override
	public void updateExcution(AggrPeriodTarget target) {
		this.repo.updateExcution(target);
	}

	/**
	 * Update target.
	 *
	 * @param target the target
	 */
	@Override
	public void updateTarget(List<AggrPeriodTarget> target) {
		this.repo.updateTarget(target);
	}

}
