package nts.uk.ctx.at.function.app.find.resultsperiod.optionalaggregationperiod;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.AnyAggrPeriodRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class OptionalAggrPeriodFinder.
 */
@Stateless
public class OptionalAggrPeriodImportFinder {

	/**
	 * The Any aggr period adapter.
	 */
	@Inject
	private AnyAggrPeriodRepository aggrPeriodRepository;

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<AnyAggrPeriodDto> findAll() {
		String companyId = AppContexts.user().companyId();
		return this.aggrPeriodRepository.findAllByCompanyId(companyId)
				.stream()
				.map(AnyAggrPeriodDto::createFromDomain)
				.collect(Collectors.toList());
	}

}
