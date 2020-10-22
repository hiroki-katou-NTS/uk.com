package nts.uk.ctx.at.record.pubimp.resultsperiod.optionalaggregationperiod;

import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.AnyAggrPeriod;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.AnyAggrPeriodRepository;
import nts.uk.ctx.at.record.pub.resultsperiod.optionalaggregationperiod.AnyAggrPeriodExport;
import nts.uk.ctx.at.record.pub.resultsperiod.optionalaggregationperiod.AnyAggrPeriodPub;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The class AnyAggrPeriodPubImpl.
 *
 * @author nws-minhnb
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AnyAggrPeriodPubImpl implements AnyAggrPeriodPub {

	/**
	 * The Any aggr period repository.
	 */
	@Inject
	private AnyAggrPeriodRepository anyAggrPeriodRepository;

	/**
	 * Finds all.
	 *
	 * @param companyId the company id
	 * @return the <code>AnyAggrPeriodExport</code> list
	 */
	@Override
	public List<AnyAggrPeriodExport> findAll(String companyId) {
		return this.anyAggrPeriodRepository.findAll(companyId)
										   .stream()
										   .map(AnyAggrPeriodExport::fromDomain)
										   .collect(Collectors.toList());
	}

	/**
	 * Finds by company id.
	 *
	 * @param companyId the company id
	 * @return the optional <code>AnyAggrPeriodExport</code>
	 */
	@Override
	public Optional<AnyAggrPeriodExport> findByCompanyId(String companyId) {
		return this.anyAggrPeriodRepository.findByCompanyId(companyId)
										   .map(AnyAggrPeriodExport::fromDomain);
	}

	/**
	 * Finds one.
	 *
	 * @param companyId     the company id
	 * @param aggrFrameCode the aggr frame code
	 * @return the optional <code>AnyAggrPeriodExport</code>
	 */
	@Override
	public Optional<AnyAggrPeriodExport> findOne(String companyId, String aggrFrameCode) {
		return this.anyAggrPeriodRepository.findOne(companyId, aggrFrameCode)
										   .map(AnyAggrPeriodExport::fromDomain);
	}

	/**
	 * Check existed.
	 *
	 * @param companyId     the company id
	 * @param aggrFrameCode the aggr frame code
	 * @return {@code true} if existed, otherwise {@code false}
	 */
	@Override
	public boolean checkExisted(String companyId, String aggrFrameCode) {
		return this.anyAggrPeriodRepository.checkExisted(companyId, aggrFrameCode);
	}

	/**
	 * Add any aggr period.
	 *
	 * @param aggrPeriodExport the export 任意集計期間
	 */
	@Override
	public void addAnyAggrPeriod(AnyAggrPeriodExport aggrPeriodExport) {
		this.anyAggrPeriodRepository.addAnyAggrPeriod(AnyAggrPeriod.createFromMemento(aggrPeriodExport.getCompanyId(),
																					  aggrPeriodExport));
	}

	/**
	 * Update any aggr period.
	 *
	 * @param aggrPeriodExport the export 任意集計期間
	 */
	@Override
	public void updateAnyAggrPeriod(AnyAggrPeriodExport aggrPeriodExport) {
		this.anyAggrPeriodRepository.updateAnyAggrPeriod(AnyAggrPeriod.createFromMemento(aggrPeriodExport.getCompanyId(),
																						 aggrPeriodExport));
	}

	/**
	 * Delete any aggr period.
	 *
	 * @param companyId     the company id
	 * @param aggrFrameCode the aggr frame code
	 */
	@Override
	public void deleteAnyAggrPeriod(String companyId, String aggrFrameCode) {
		this.anyAggrPeriodRepository.deleteAnyAggrPeriod(companyId, aggrFrameCode);
	}

}
