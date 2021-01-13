package nts.uk.ctx.at.record.pubimp.resultsperiod.optionalaggregationperiod;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.ExecuteAggrPeriodDomainService;
import nts.uk.ctx.at.record.pub.resultsperiod.optionalaggregationperiod.ExecuteAggrPeriodPub;

/**
 * The Class ExecuteAggrPeriodPubImpl.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ExecuteAggrPeriodPubImpl implements ExecuteAggrPeriodPub {

	/** The service. */
	@Inject
	private ExecuteAggrPeriodDomainService service;
	
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
		this.service.excuteOptionalPeriod(companyId, excuteId, asyn);
	}

}
