package nts.uk.ctx.at.record.pub.resultsperiod.optionalaggregationperiod;

import nts.arc.layer.app.command.AsyncCommandHandlerContext;

/**
 * The Interface ExecuteAggrPeriodPub.
 */
public interface ExecuteAggrPeriodPub {
	
	/**
	 * Excute optional period.
	 *
	 * @param <C> the generic type
	 * @param companyId the company id
	 * @param excuteId the excute id
	 * @param asyn the asyn
	 */
	<C> void excuteOptionalPeriod(String companyId, String excuteId, AsyncCommandHandlerContext<C> asyn);

}
