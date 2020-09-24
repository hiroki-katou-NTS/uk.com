package nts.uk.ctx.at.record.pubimp.resultsperiod.optionalaggregationperiod;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.OptionalAggrPeriod;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.OptionalAggrPeriodRepository;
import nts.uk.ctx.at.record.pub.resultsperiod.optionalaggregationperiod.OptionalAggrPeriodPub;

/**
 * The Class OptionalAggrPeriodPubImpl.
 */
public class OptionalAggrPeriodPubImpl implements OptionalAggrPeriodPub {

	@Inject
	private OptionalAggrPeriodRepository optionalAggrPeriodRepository;
	/**
	 * Find all.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	@Override
	public List<OptionalAggrPeriod> findAll(String companyId) {
		return this.optionalAggrPeriodRepository.findAll(companyId);
	}

	/**
	 * Find by cid.
	 *
	 * @param companyId the company id
	 * @return the optional
	 */
	@Override
	public Optional<OptionalAggrPeriod> findByCid(String companyId) {
		return this.optionalAggrPeriodRepository.findByCid(companyId);
	}

	/**
	 * Find.
	 *
	 * @param companyId the company id
	 * @param aggrFrameCode the aggr frame code
	 * @return the optional
	 */
	@Override
	public Optional<OptionalAggrPeriod> find(String companyId, String aggrFrameCode) {
		return this.optionalAggrPeriodRepository.find(companyId, aggrFrameCode);
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
		return this.optionalAggrPeriodRepository.checkExit(companyId, aggrFrameCode);
	}

	/**
	 * Adds the optional aggr period.
	 *
	 * @param optionalAggrPeriod the optional aggr period
	 */
	@Override
	public void addOptionalAggrPeriod(OptionalAggrPeriod optionalAggrPeriod) {
		this.optionalAggrPeriodRepository.addOptionalAggrPeriod(optionalAggrPeriod);
	}

	/**
	 * Update optional aggr period.
	 *
	 * @param optionalAggrPeriod the optional aggr period
	 */
	@Override
	public void updateOptionalAggrPeriod(OptionalAggrPeriod optionalAggrPeriod) {
		this.optionalAggrPeriodRepository.updateOptionalAggrPeriod(optionalAggrPeriod);
	}

	/**
	 * Delete optional aggr period.
	 *
	 * @param companyId the company id
	 * @param aggrFrameCode the aggr frame code
	 */
	@Override
	public void deleteOptionalAggrPeriod(String companyId, String aggrFrameCode) {
		this.optionalAggrPeriodRepository.deleteOptionalAggrPeriod(companyId, aggrFrameCode);
	}

}
