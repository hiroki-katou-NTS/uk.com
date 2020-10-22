package nts.uk.ctx.at.record.infra.repository.resultsperiod.optionalaggregationperiod;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.AnyAggrPeriod;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.AnyAggrPeriodRepository;
import nts.uk.ctx.at.record.infra.entity.resultsperiod.optionalaggregationperiod.KrcmtOptionalAggrPeriod;
import nts.uk.ctx.at.record.infra.entity.resultsperiod.optionalaggregationperiod.KrcmtOptionalAggrPeriodPK;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

/**
 * The class Jpa any aggr period Repository.<br>
 * Repository 任意集計期間
 *
 * @author nws-minhnb
 */
@Stateless
public class JpaAnyAggrPeriodRepository extends JpaRepository implements AnyAggrPeriodRepository {

	/** The query find all */
	private static final String QUERY_FIND_ALL = "SELECT e FROM KrcmtOptionalAggrPeriod e ";

	/** The query find by company id */
	private static final String QUERY_FIND_BY_COMPANY_ID = QUERY_FIND_ALL
			+ "WHERE e.krcmtOptionalAggrPeriodPK.companyId = :companyId "
			+ "ORDER BY e.krcmtOptionalAggrPeriodPK.aggrFrameCode ASC";

	/** The query find by company id and aggr frame code */
	private static final String QUERY_FIND_BY_COMPANY_ID_AND_AGGR_FRAME_CODE = QUERY_FIND_ALL
			+ "WHERE e.krcmtOptionalAggrPeriodPK.companyId = :companyId "
			+ "AND e.krcmtOptionalAggrPeriodPK.aggrFrameCode = :aggrFrameCode";

	/**
	 * Finds all.
	 *
	 * @param companyId the company id
	 * @return the <code>AnyAggrPeriod</code> list
	 */
	@Override
	public List<AnyAggrPeriod> findAll(String companyId) {
		return this.queryProxy().query(QUERY_FIND_BY_COMPANY_ID, KrcmtOptionalAggrPeriod.class)
								.setParameter("companyId", companyId)
								.getList((KrcmtOptionalAggrPeriod entity) -> AnyAggrPeriod.createFromMemento(entity.getCompanyId(), entity));
	}

	/**
	 * To entity.
	 *
	 * @param domain the domain 任意集計期間
	 * @return the entity 任意集計期間
	 */
	private static KrcmtOptionalAggrPeriod toEntity(AnyAggrPeriod domain) {
		KrcmtOptionalAggrPeriod entity = new KrcmtOptionalAggrPeriod();
		domain.setMemento(entity);
		return entity;
	}

	/**
	 * Add any aggr period.
	 *
	 * @param domain the domain 任意集計期間
	 */
	@Override
	public void addAnyAggrPeriod(AnyAggrPeriod domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	/**
	 * Update any aggr period.
	 *
	 * @param domain the domain 任意集計期間
	 */
	@Override
	public void updateAnyAggrPeriod(AnyAggrPeriod domain) {
		// Convert data to entity
		KrcmtOptionalAggrPeriod entity = toEntity(domain);
		KrcmtOptionalAggrPeriod oldEntity = this.queryProxy().find(entity.getKrcmtOptionalAggrPeriodPK(),
																   KrcmtOptionalAggrPeriod.class).get();
		oldEntity.setOptionalAggrName(entity.getOptionalAggrName());
		oldEntity.setStartDate(entity.getStartDate());
		oldEntity.setEndDate(entity.getEndDate());
		// Update entity
		this.commandProxy().update(entity);
	}

	/**
	 * Delete any aggr period.
	 *
	 * @param companyId     the company id
	 * @param aggrFrameCode the aggr frame code
	 */
	@Override
	public void deleteAnyAggrPeriod(String companyId, String aggrFrameCode) {
		KrcmtOptionalAggrPeriodPK aggrPeriodPK = new KrcmtOptionalAggrPeriodPK(companyId, aggrFrameCode);
		this.commandProxy().remove(KrcmtOptionalAggrPeriod.class, aggrPeriodPK);
	}

	/**
	 * Finds by company id.
	 *
	 * @param companyId the company id
	 * @return the optional <code>AnyAggrPeriod</code>
	 */
	@Override
	public Optional<AnyAggrPeriod> findByCompanyId(String companyId) {
		return this.queryProxy().query(QUERY_FIND_BY_COMPANY_ID, KrcmtOptionalAggrPeriod.class)
				   .setParameter("companyId", companyId)
				   .getSingle((KrcmtOptionalAggrPeriod entity) -> AnyAggrPeriod.createFromMemento(entity.getCompanyId(), entity));
	}

	/**
	 * Finds one.
	 *
	 * @param companyId     the company id
	 * @param aggrFrameCode the aggr frame code
	 * @return the optional <code>AnyAggrPeriod</code>
	 */
	@Override
	public Optional<AnyAggrPeriod> findOne(String companyId, String aggrFrameCode) {
		return this.queryProxy().query(QUERY_FIND_BY_COMPANY_ID_AND_AGGR_FRAME_CODE, KrcmtOptionalAggrPeriod.class)
								.setParameter("companyId", companyId)
								.setParameter("aggrFrameCode", aggrFrameCode)
								.getSingle((KrcmtOptionalAggrPeriod entity) -> AnyAggrPeriod.createFromMemento(entity.getCompanyId(), entity));
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
		List<KrcmtOptionalAggrPeriod> resultList = this.queryProxy().query(QUERY_FIND_BY_COMPANY_ID_AND_AGGR_FRAME_CODE,
																		   KrcmtOptionalAggrPeriod.class)
																	.setParameter("companyId", companyId)
																	.setParameter("aggrFrameCode", aggrFrameCode)
																	.getList();
		return !resultList.isEmpty();
	}

}
