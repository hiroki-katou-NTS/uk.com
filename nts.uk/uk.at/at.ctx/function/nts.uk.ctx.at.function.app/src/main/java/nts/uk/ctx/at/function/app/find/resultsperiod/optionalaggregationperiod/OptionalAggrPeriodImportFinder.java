package nts.uk.ctx.at.function.app.find.resultsperiod.optionalaggregationperiod;

import nts.uk.ctx.at.function.dom.adapter.resultsperiod.optionalaggregationperiod.AnyAggrPeriodAdapter;
import nts.uk.ctx.at.function.dom.adapter.resultsperiod.optionalaggregationperiod.AnyAggrPeriodImport;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The Class OptionalAggrPeriodFinder.
 */
@Stateless
public class OptionalAggrPeriodImportFinder {

	/**
	 * The Any aggr period adapter.
	 */
	@Inject
	private AnyAggrPeriodAdapter anyAggrPeriodAdapter;

	/**
	 * Find by cid.
	 * ドメインモデル「任意集計期間」を取得する
	 *
	 * @return the aggr period dto
	 */
	public AnyAggrPeriodDto findByCid() {
		String companyId = AppContexts.user().companyId();
		// Step ドメインモデル「任意集計期間」を取得する
		AnyAggrPeriodImport aggrPeriodImport = this.anyAggrPeriodAdapter.findByCompanyId(companyId)
																		.orElse(null);
		//	「任意集計期間」を取得できたかチェックする
		return AnyAggrPeriodDto.createFromImport(aggrPeriodImport);
	}

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<AnyAggrPeriodDto> findAll() {
		String companyId = AppContexts.user().companyId();
		return this.anyAggrPeriodAdapter.findAll(companyId)
										.stream()
										.map(AnyAggrPeriodDto::createFromImport)
										.collect(Collectors.toList());
	}

}
