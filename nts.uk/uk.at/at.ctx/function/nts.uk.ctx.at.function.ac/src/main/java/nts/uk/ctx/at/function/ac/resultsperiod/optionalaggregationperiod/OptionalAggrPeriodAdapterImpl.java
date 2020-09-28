package nts.uk.ctx.at.function.ac.resultsperiod.optionalaggregationperiod;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.resultsperiod.optionalaggregationperiod.OptionalAggrPeriodAdapter;
import nts.uk.ctx.at.function.dom.resultsperiod.optionalaggregationperiod.OptionalAggrPeriodImport;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.OptionalAggrPeriod;
import nts.uk.ctx.at.record.pub.resultsperiod.optionalaggregationperiod.OptionalAggrPeriodPub;

/**
 * The Class OptionalAggrPeriodAdapterImpl.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
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
	 * Check exist.
	 *
	 * @param companyId the company id
	 * @param aggrFrameCode the aggr frame code
	 * @return true, if successful
	 */
	@Override
	public boolean checkExist(String companyId, String aggrFrameCode) {
		return this.pub.checkExist(companyId, aggrFrameCode);
	}

	/**
	 * Adds the optional aggr period.
	 *
	 * @param optionalAggrPeriod the optional aggr period
	 */
	@Override
	public void addOptionalAggrPeriod(OptionalAggrPeriodImport optionalAggrPeriod) {
		this.pub.addOptionalAggrPeriod(OptionalAggrPeriod.createFromJavaType(
				optionalAggrPeriod.getCompanyId(), 
				optionalAggrPeriod.getAggrFrameCode(), 
				optionalAggrPeriod.getOptionalAggrName(), 
				optionalAggrPeriod.getStartDate(), 
				optionalAggrPeriod.getEndDate()));
	}

	/**
	 * Update optional aggr period.
	 *
	 * @param optionalAggrPeriod the optional aggr period
	 */
	@Override
	public void updateOptionalAggrPeriod(OptionalAggrPeriodImport optionalAggrPeriod) {
		this.pub.updateOptionalAggrPeriod(OptionalAggrPeriod.createFromJavaType(
				optionalAggrPeriod.getCompanyId(), 
				optionalAggrPeriod.getAggrFrameCode(), 
				optionalAggrPeriod.getOptionalAggrName(), 
				optionalAggrPeriod.getStartDate(), 
				optionalAggrPeriod.getEndDate()));
		
	}

	/**
	 * Delete optional aggr period.
	 *
	 * @param companyId the company id
	 * @param aggrFrameCode the aggr frame code
	 */
	@Override
	public void deleteOptionalAggrPeriod(String companyId, String aggrFrameCode) {
		this.pub.deleteOptionalAggrPeriod(companyId, aggrFrameCode);
	}

}
