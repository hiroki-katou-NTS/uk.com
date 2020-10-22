package nts.uk.ctx.at.function.ac.resultsperiod.optionalaggregationperiod;

import nts.uk.ctx.at.function.dom.adapter.resultsperiod.optionalaggregationperiod.AnyAggrPeriodAdapter;
import nts.uk.ctx.at.function.dom.adapter.resultsperiod.optionalaggregationperiod.AnyAggrPeriodImport;
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
 * The class Any aggr period adapter impl.
 *
 * @author nws- minhnb
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AnyAggrPeriodAdapterImpl implements AnyAggrPeriodAdapter {

	/**
	 * The Any aggr period pub
	 */
	@Inject
	private AnyAggrPeriodPub aggrPeriodPub;

	/**
	 * Converts export to import.
	 *
	 * @param export the Any aggr period export.
	 * @return the Any aggr period import
	 */
	private AnyAggrPeriodImport convertExportToImport(AnyAggrPeriodExport export) {
		return new AnyAggrPeriodImport(export.getCompanyId(),
									   export.getAggrFrameCode(),
									   export.getOptionalAggrName(),
									   export.getPeriod());
	}

	/**
	 * Finds all.
	 *
	 * @param companyId the company id
	 * @return the <code>AnyAggrPeriodImport</code> list
	 */
	@Override
	public List<AnyAggrPeriodImport> findAll(String companyId) {
		return this.aggrPeriodPub.findAll(companyId)
								 .stream()
								 .map(this::convertExportToImport)
								 .collect(Collectors.toList());
	}

	/**
	 * Finds by company id.
	 *
	 * @param companyId the company id
	 * @return the optional <code>AnyAggrPeriodImport</code>
	 */
	@Override
	public Optional<AnyAggrPeriodImport> findByCompanyId(String companyId) {
		return this.aggrPeriodPub.findByCompanyId(companyId)
								 .map(this::convertExportToImport);
	}

	/**
	 * Finds one.
	 *
	 * @param companyId     the company id
	 * @param aggrFrameCode the aggr frame code
	 * @return the optional <code>AnyAggrPeriodImport</code>
	 */
	@Override
	public Optional<AnyAggrPeriodImport> findOne(String companyId, String aggrFrameCode) {
		return this.aggrPeriodPub.findOne(companyId, aggrFrameCode)
								 .map(this::convertExportToImport);
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
		return this.aggrPeriodPub.checkExisted(companyId, aggrFrameCode);
	}

	/**
	 * Add any aggr period.
	 *
	 * @param aggrPeriodImport the import 任意集計期間
	 */
	@Override
	public void addAnyAggrPeriod(AnyAggrPeriodImport aggrPeriodImport) {
		AnyAggrPeriodExport export = new AnyAggrPeriodExport(aggrPeriodImport.getCompanyId(),
															 aggrPeriodImport.getAggrFrameCode(),
															 aggrPeriodImport.getOptionalAggrName(),
															 aggrPeriodImport.getPeriod());
		this.aggrPeriodPub.addAnyAggrPeriod(export);
	}

	/**
	 * Update any aggr period.
	 *
	 * @param aggrPeriodImport the import 任意集計期間
	 */
	@Override
	public void updateAnyAggrPeriod(AnyAggrPeriodImport aggrPeriodImport) {
		AnyAggrPeriodExport export = new AnyAggrPeriodExport(aggrPeriodImport.getCompanyId(),
															 aggrPeriodImport.getAggrFrameCode(),
															 aggrPeriodImport.getOptionalAggrName(),
															 aggrPeriodImport.getPeriod());
		this.aggrPeriodPub.updateAnyAggrPeriod(export);
	}

	/**
	 * Delete any aggr period.
	 *
	 * @param companyId     the company id
	 * @param aggrFrameCode the aggr frame code
	 */
	@Override
	public void deleteAnyAggrPeriod(String companyId, String aggrFrameCode) {
		this.aggrPeriodPub.deleteAnyAggrPeriod(companyId, aggrFrameCode);
	}

}
