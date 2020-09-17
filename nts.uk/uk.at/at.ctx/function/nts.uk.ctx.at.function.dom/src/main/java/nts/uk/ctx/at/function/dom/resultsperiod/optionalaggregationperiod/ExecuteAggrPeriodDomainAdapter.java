package nts.uk.ctx.at.function.dom.resultsperiod.optionalaggregationperiod;

import nts.arc.layer.app.command.AsyncCommandHandlerContext;

/**
 * The Interface ExecuteAggrPeriodDomainAdapter.
 */
public interface ExecuteAggrPeriodDomainAdapter {
	
	/**
	 * Excute optional period.
	 *	任意期間集計Mgrクラス
	 * @param <C> the generic type
	 * @param companyId the company id
	 * @param excuteId the excute id
	 * @param asyn the asyn
	 */
	<C> void excuteOptionalPeriod(String companyId, String excuteId, AsyncCommandHandlerContext<C> asyn);

}
