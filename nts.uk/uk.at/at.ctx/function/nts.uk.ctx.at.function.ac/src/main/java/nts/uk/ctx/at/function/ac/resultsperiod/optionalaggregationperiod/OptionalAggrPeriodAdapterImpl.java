package nts.uk.ctx.at.function.ac.resultsperiod.optionalaggregationperiod;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.resultsperiod.optionalaggregationperiod.OptionalAggrPeriodAdapter;
import nts.uk.ctx.at.function.dom.resultsperiod.optionalaggregationperiod.OptionalAggrPeriodImport;
import nts.uk.ctx.at.record.pub.resultsperiod.optionalaggregationperiod.OptionalAggrPeriodPub;

/**
 * The Class OptionalAggrPeriodAdapterImpl.
 */
public class OptionalAggrPeriodAdapterImpl implements OptionalAggrPeriodAdapter {

	/** The pub. */
	@Inject
	private OptionalAggrPeriodPub pub;
	
	/**
	 * Find all.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	@Override
	public List<OptionalAggrPeriodImport> findAll(String companyId) {
		return this.pub.findAll(companyId).stream()
				.map(domain -> OptionalAggrPeriodImport.builder()
					.aggrFrameCode(domain.getAggrFrameCode().v())
					.companyId(domain.getCompanyId())
					.endDate(domain.getEndDate())
					.startDate(domain.getStartDate())
					.optionalAggrName(domain.getOptionalAggrName().v())
					.build())
				.collect(Collectors.toList());
	}

	/**
	 * Find by cid.
	 *
	 * @param companyId the company id
	 * @return the optional
	 */
	@Override
	public Optional<OptionalAggrPeriodImport> findByCid(String companyId) {
		return this.pub.findByCid(companyId)
				.map(domain -> OptionalAggrPeriodImport.builder()
					.aggrFrameCode(domain.getAggrFrameCode().v())
					.companyId(domain.getCompanyId())
					.endDate(domain.getEndDate())
					.startDate(domain.getStartDate())
					.optionalAggrName(domain.getOptionalAggrName().v())
					.build());
	}

	/**
	 * Find.
	 *
	 * @param companyId the company id
	 * @param aggrFrameCode the aggr frame code
	 * @return the optional
	 */
	@Override
	public Optional<OptionalAggrPeriodImport> find(String companyId, String aggrFrameCode) {
		return this.pub.find(companyId, aggrFrameCode)
				.map(domain -> OptionalAggrPeriodImport.builder()
					.aggrFrameCode(domain.getAggrFrameCode().v())
					.companyId(domain.getCompanyId())
					.endDate(domain.getEndDate())
					.startDate(domain.getStartDate())
					.optionalAggrName(domain.getOptionalAggrName().v())
					.build());
	}

	/**
	 * Check exit.
	 *
	 * @param companyId the company id
	 * @param aggrFrameCode the aggr frame code
	 * @return true, if successful
	 */
	@Override
	public boolean checkExit(String companyId, String aggrFrameCode) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Adds the optional aggr period.
	 *
	 * @param optionalAggrPeriod the optional aggr period
	 */
	@Override
	public void addOptionalAggrPeriod(OptionalAggrPeriodImport optionalAggrPeriod) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Update optional aggr period.
	 *
	 * @param optionalAggrPeriod the optional aggr period
	 */
	@Override
	public void updateOptionalAggrPeriod(OptionalAggrPeriodImport optionalAggrPeriod) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Delete optional aggr period.
	 *
	 * @param companyId the company id
	 * @param aggrFrameCode the aggr frame code
	 */
	@Override
	public void deleteOptionalAggrPeriod(String companyId, String aggrFrameCode) {
		// TODO Auto-generated method stub
		
	}

}
