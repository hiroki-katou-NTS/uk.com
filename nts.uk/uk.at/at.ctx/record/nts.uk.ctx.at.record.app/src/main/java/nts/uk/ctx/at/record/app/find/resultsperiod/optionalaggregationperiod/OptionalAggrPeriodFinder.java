package nts.uk.ctx.at.record.app.find.resultsperiod.optionalaggregationperiod;

import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.AnyAggrPeriod;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.AnyAggrPeriodRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The class Optional aggr period finder.
 */
@Stateless
public class OptionalAggrPeriodFinder {

	/**
	 * The Any aggr period repository.
	 */
	@Inject
	private AnyAggrPeriodRepository repository;


	/**
	 * Finds all.
	 *
	 * @return the <code>AnyAggrPeriodDto</code> list
	 */
	public List<AnyAggrPeriodDto> findAll() {
		String companyId = AppContexts.user().companyId();
		return this.repository.findAll(companyId)
							  .stream()
							  .map(AnyAggrPeriodDto::createFromDomain)
							  .collect(Collectors.toList());
	}

	/**
	 * Finds.
	 *
	 * @param aggrFrameCode the aggr frame code
	 * @return the Any aggr period dto
	 */
	public AnyAggrPeriodDto find(String aggrFrameCode) {
		String companyId = AppContexts.user().companyId();
		AnyAggrPeriod domain = this.repository.findOne(companyId, aggrFrameCode).orElse(null);
		return AnyAggrPeriodDto.createFromDomain(domain);
	}

}
