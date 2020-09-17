package nts.uk.ctx.at.record.pubimp.executionstatusmanage.optionalperiodprocess;

import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodExcution;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodExcutionRepository;
import nts.uk.ctx.at.record.pub.executionstatusmanage.optionalperiodprocess.AggrPeriodExcutionPub;

/**
 * The Class AggrPeriodExcutionPubImpl.
 */
public class AggrPeriodExcutionPubImpl implements AggrPeriodExcutionPub {

	/** The repo. */
	@Inject
	private AggrPeriodExcutionRepository repo;

	/**
	 * Adds the excution.
	 *
	 * @param excution the excution
	 */
	@Override
	public void addExcution(AggrPeriodExcution excution) {
		this.repo.addExcution(excution);
	}
}
