package nts.uk.ctx.at.function.ac.resultsperiod.optionalaggregationperiod;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.uk.ctx.at.function.dom.resultsperiod.optionalaggregationperiod.ExecuteAggrPeriodDomainAdapter;
import nts.uk.ctx.at.record.pub.resultsperiod.optionalaggregationperiod.ExecuteAggrPeriodPub;

/**
 * The Class ExecuteAggrPeriodAdapterImpl.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ExecuteAggrPeriodAdapterImpl implements ExecuteAggrPeriodDomainAdapter {

	/** The pub. */
	@Inject
	private ExecuteAggrPeriodPub pub;
	
	/**
	 * Excute optional period.
	 *
	 * @param <C> the generic type
	 * @param companyId the company id
	 * @param excuteId the excute id
	 * @param asyn the asyn
	 */
	@Override
	public <C> void excuteOptionalPeriod(String companyId, String excuteId, AsyncCommandHandlerContext<C> asyn) {
		this.pub.excuteOptionalPeriod(companyId, excuteId, asyn);
	}

}
