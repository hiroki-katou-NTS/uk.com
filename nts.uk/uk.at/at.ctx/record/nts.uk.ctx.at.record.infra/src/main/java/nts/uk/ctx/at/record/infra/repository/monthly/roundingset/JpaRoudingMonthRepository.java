/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.monthly.roundingset;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.roundingset.RoundingMonth;
import nts.uk.ctx.at.record.dom.monthly.roundingset.RoundingMonthRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.roundingset.KrcstMonItemRound;
import nts.uk.ctx.at.record.infra.entity.monthly.roundingset.KrcstMonItemRoundPK;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class JpaRoudingMonthRepository.
 *
 * @author phongtq
 */
@Stateless
public class JpaRoudingMonthRepository extends JpaRepository implements RoundingMonthRepository {

	/** The Constant SELECT_BY_CID. */
	private static final String SELECT_BY_CID;

	/** The Constant DELETE_BY_CID. */
	private static final String DELETE_BY_CID;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KrcstMonItemRound e");
		builderString.append(" WHERE e.kshstRoundingMonthSetPK.companyId = :companyId");
		SELECT_BY_CID = builderString.toString();

		builderString = new StringBuilder();
		builderString.append(" DELETE FROM KrcstMonItemRound e");
		builderString.append(" WHERE e.kshstRoundingMonthSetPK.companyId = :companyId");
		DELETE_BY_CID = builderString.toString();
	}

	/**
	 * convert To Domain Rounding Month.
	 *
	 * @param monthSet
	 *            the month set
	 * @return the rounding month
	 */
	private RoundingMonth convertToDomain(KrcstMonItemRound monthSet) {
		RoundingMonth month = RoundingMonth.createFromJavaType(monthSet.PK.companyId, monthSet.PK.attendanceItemId,
				monthSet.roundUnit, monthSet.roundProc);
		return month;
	}

	/**
	 * convert To Db Type KrcstMonItemRound.
	 *
	 * @param month
	 *            the month
	 * @return the krcst mon item round
	 */
	private KrcstMonItemRound convertToDbType(RoundingMonth month) {
		KrcstMonItemRound monthSet = new KrcstMonItemRound();
		KrcstMonItemRoundPK monthSetPK = new KrcstMonItemRoundPK(month.getCompanyId(), month.getTimeItemId());
		monthSet.roundUnit = month.getUnit().value;
		monthSet.roundProc = month.getRounding().value;
		monthSet.PK = monthSetPK;
		return monthSet;
	}

	/**
	 * find By CID.
	 *
	 * @param companyId
	 *            the company id
	 * @param itemTimeId
	 *            the item time id
	 * @return the list
	 */
	@Override
	public List<RoundingMonth> findByCompanyId(String companyId) {
		return this.queryProxy().query(SELECT_BY_CID, KrcstMonItemRound.class).setParameter("companyId", companyId)
				.getList(c -> convertToDomain(c));
	}

	/**
	 * Add Rounding Month.
	 *
	 * @param month
	 *            the month
	 */
	@Override
	public void add(RoundingMonth month) {
		this.commandProxy().insert(convertToDbType(month));
	}

	/**
	 * Update Rounding Month.
	 *
	 * @param month
	 *            the month
	 */
	@Override
	public void update(RoundingMonth month) {
		KrcstMonItemRoundPK primaryKey = new KrcstMonItemRoundPK(month.getCompanyId(), month.getTimeItemId());
		KrcstMonItemRound entity = this.queryProxy().find(primaryKey, KrcstMonItemRound.class).get();
		entity.roundUnit = month.getUnit().value;
		entity.roundProc = month.getRounding().value;
		entity.PK = primaryKey;
		this.commandProxy().update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.monthly.roundingset.RoundingMonthRepository#
	 * findByCId(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<RoundingMonth> findByCId(String companyId, Integer timeItemId) {
		return this.queryProxy().find(new KrcstMonItemRoundPK(companyId, timeItemId), KrcstMonItemRound.class)
				.map(c -> convertToDomain(c));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.calculation.holiday.roundingmonth.
	 * RoundingMonthRepository#addList(java.util.List)
	 */
	@Override
	public void addList(List<RoundingMonth> lstRoundingMonth) {
		this.getEntityManager().createQuery(DELETE_BY_CID).setParameter("companyId", AppContexts.user().companyId())
				.executeUpdate();
		this.commandProxy().insertAll(
				lstRoundingMonth.stream().map(domain -> convertToDbType(domain)).collect(Collectors.toList()));
	}

}
