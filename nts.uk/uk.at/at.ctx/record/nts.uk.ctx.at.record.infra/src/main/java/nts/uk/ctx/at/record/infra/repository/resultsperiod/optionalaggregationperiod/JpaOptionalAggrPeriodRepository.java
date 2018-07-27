package nts.uk.ctx.at.record.infra.repository.resultsperiod.optionalaggregationperiod;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.OptionalAggrPeriod;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.OptionalAggrPeriodRepository;
import nts.uk.ctx.at.record.infra.entity.resultsperiod.optionalaggregationperiod.KrcmtOptionalAggrPeriod;
import nts.uk.ctx.at.record.infra.entity.resultsperiod.optionalaggregationperiod.KrcmtOptionalAggrPeriodPK;

/**
 * 
 * @author phongtq
 *
 */
@Stateless
public class JpaOptionalAggrPeriodRepository extends JpaRepository implements OptionalAggrPeriodRepository {

	private static final String FIND_ALL;
	private static final String FIND;
	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KrcmtOptionalAggrPeriod e");
		builderString.append(" WHERE e.krcmtOptionalAggrPeriodPK.companyId = :companyId");
		builderString.append(" ORDER BY e.krcmtOptionalAggrPeriodPK.aggrFrameCode ASC");
		FIND_ALL = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KrcmtOptionalAggrPeriod e");
		builderString.append(" WHERE e.krcmtOptionalAggrPeriodPK.companyId = :companyId");
		builderString.append(" AND e.krcmtOptionalAggrPeriodPK.aggrFrameCode = :aggrFrameCode");
		FIND = builderString.toString();
	}

	/**
	 * Find all Optional Aggr Period by companyId
	 */
	@Override
	public List<OptionalAggrPeriod> findAll(String companyId) {
		return this.queryProxy().query(FIND_ALL, KrcmtOptionalAggrPeriod.class).setParameter("companyId", companyId)
				.getList(c -> convertToDomainOap(c));
	}

	/**
	 * Convert to Domain Optional Aggr Period
	 * 
	 * @param krcmtOptionalAggrPeriod
	 * @return
	 */
	private OptionalAggrPeriod convertToDomainOap(KrcmtOptionalAggrPeriod krcmtOptionalAggrPeriod) {
		OptionalAggrPeriod optionalAggrPeriod = OptionalAggrPeriod.createFromJavaType(
				krcmtOptionalAggrPeriod.krcmtOptionalAggrPeriodPK.companyId,
				krcmtOptionalAggrPeriod.krcmtOptionalAggrPeriodPK.aggrFrameCode,
				krcmtOptionalAggrPeriod.optionalAggrName, krcmtOptionalAggrPeriod.startDate,
				krcmtOptionalAggrPeriod.endDate);
		return optionalAggrPeriod;
	}

	/**
	 * Add Optional Aggr Period
	 */
	@Override
	public void addOptionalAggrPeriod(OptionalAggrPeriod optionalAggrPeriod) {
		this.commandProxy().insert(convertToDbTypeOap(optionalAggrPeriod));

	}

	/**
	 * Convert to Database Optional Aggr Period
	 * @param optionalAggrPeriod
	 * @return
	 */
	private KrcmtOptionalAggrPeriod convertToDbTypeOap(OptionalAggrPeriod optionalAggrPeriod) {
		KrcmtOptionalAggrPeriod entity = new KrcmtOptionalAggrPeriod();
		entity.krcmtOptionalAggrPeriodPK = new KrcmtOptionalAggrPeriodPK(optionalAggrPeriod.getCompanyId(), optionalAggrPeriod.getAggrFrameCode().v());
		entity.optionalAggrName = optionalAggrPeriod.getOptionalAggrName().v();
		entity.startDate = optionalAggrPeriod.getStartDate();
		entity.endDate = optionalAggrPeriod.getEndDate();
		return entity;
	}

	/**
	 * Update Optional Aggr Period
	 */
	@Override
	public void updateOptionalAggrPeriod(OptionalAggrPeriod optionalAggrPeriod) {
		KrcmtOptionalAggrPeriodPK primaryKey = new KrcmtOptionalAggrPeriodPK(optionalAggrPeriod.getCompanyId(),
				optionalAggrPeriod.getAggrFrameCode().v());
		KrcmtOptionalAggrPeriod period = this.queryProxy().find(primaryKey, KrcmtOptionalAggrPeriod.class).get();
		period.optionalAggrName = optionalAggrPeriod.getOptionalAggrName().v();
		period.startDate = optionalAggrPeriod.getStartDate();
		period.endDate = optionalAggrPeriod.getEndDate();
		this.commandProxy().update(period);
	}

	/**
	 * Delete Optional Aggr Period
	 */
	@Override
	public void deleteOptionalAggrPeriod(String companyId, String aggrFrameCode) {
		KrcmtOptionalAggrPeriodPK aggrPeriodPK = new KrcmtOptionalAggrPeriodPK(companyId, aggrFrameCode);
		this.commandProxy().remove(KrcmtOptionalAggrPeriod.class, aggrPeriodPK);

	}

	/**
	 * 
	 */
	@Override
	public Optional<OptionalAggrPeriod> findByCid(String companyId) {
		return this.queryProxy().query(FIND_ALL, KrcmtOptionalAggrPeriod.class).setParameter("companyId", companyId)
				.getSingle(t -> convertToDomainOap(t));
	}

	/**
	 * 
	 */
	@Override
	public Optional<OptionalAggrPeriod> find(String companyId, String aggrFrameCode) {
		return this.queryProxy().query(FIND, KrcmtOptionalAggrPeriod.class).setParameter("companyId", companyId)
				.setParameter("aggrFrameCode", aggrFrameCode).getSingle(t -> convertToDomainOap(t));
	}
	
	@Override
	public boolean checkExit(String companyId, String aggrFrameCode) {
		List<KrcmtOptionalAggrPeriod> branchs = this.queryProxy().query(FIND, KrcmtOptionalAggrPeriod.class)
				.setParameter("companyId", companyId).setParameter("aggrFrameCode", aggrFrameCode).getList();
		return !branchs.isEmpty();
	}

}
