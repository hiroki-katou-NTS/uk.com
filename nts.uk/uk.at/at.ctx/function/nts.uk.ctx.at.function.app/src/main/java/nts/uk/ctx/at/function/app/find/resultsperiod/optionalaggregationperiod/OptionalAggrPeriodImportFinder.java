package nts.uk.ctx.at.function.app.find.resultsperiod.optionalaggregationperiod;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.resultsperiod.optionalaggregationperiod.OptionalAggrPeriodAdapter;
import nts.uk.ctx.at.function.dom.resultsperiod.optionalaggregationperiod.OptionalAggrPeriodImport;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class OptionalAggrPeriodFinder.
 */
@Stateless
public class OptionalAggrPeriodImportFinder {

	/** The optional aggr period adapter. */
	@Inject
	private OptionalAggrPeriodAdapter optionalAggrPeriodAdapter;
	
	/**
	 * Find by cid.
	 * 	ドメインモデル「任意集計期間」を取得する
	 *
	 * @return the aggr period dto
	 */
	public AggrPeriodDto findByCid() {
		String companyId = AppContexts.user().companyId();
		// Step ドメインモデル「任意集計期間」を取得する
		Optional<OptionalAggrPeriodImport> optionalAggrPeriod = this.optionalAggrPeriodAdapter.findByCid(companyId);
		//	「任意集計期間」を取得できたかチェックする
		if (!optionalAggrPeriod.isPresent()) {
			return null;
		}
		return this.optionalAggrPeriodAdapter.findByCid(companyId)
				.map(item -> AggrPeriodDto.builder()
					.aggrFrameCode(item.getAggrFrameCode())
					.companyId(item.getCompanyId())
					.endDate(item.getEndDate().toString("yyyy/MM/dd"))
					.startDate(item.getStartDate().toString("yyyy/MM/dd"))
					.optionalAggrName(item.getOptionalAggrName())
					.build()
				).get();
	}
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<AggrPeriodDto> findAll() {
		String companyId = AppContexts.user().companyId();
		return this.optionalAggrPeriodAdapter.findAll(companyId).stream()
				.map(item -> AggrPeriodDto.builder()
						.aggrFrameCode(item.getAggrFrameCode())
						.companyId(item.getCompanyId())
						.endDate(item.getEndDate().toString())
						.startDate(item.getStartDate().toString())
						.optionalAggrName(item.getOptionalAggrName())
						.build())
				.collect(Collectors.toList());
	}
}
