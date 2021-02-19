package nts.uk.ctx.at.function.app.command.resultsperiod.optionalaggregationperiod;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.AnyAggrPeriodRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class RemoveOptionalAggregationPeriodCommandHandler.
 */
@Stateless
public class RemoveOptionalAggregationPeriodCommandHandler {
	
	/** The repository. */
	@Inject
	private AnyAggrPeriodRepository repository;
	
	/**
	 * Delete optional aggr period.
	 *	ドメインモデル「任意集計期間」を削除する
	 * @param companyId the company id
	 * @param aggrFrameCode the aggr frame code
	 */
	public void delete(String code) {
		String companyId = AppContexts.user().companyId();
		this.repository.deleteAnyAggrPeriod(companyId, code);
	}

}
