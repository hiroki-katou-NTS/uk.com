/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.shortworktime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistoryRepository;
import nts.uk.ctx.at.shared.dom.shortworktime.ShortWorkTimeHistory;
import nts.uk.ctx.at.shared.infra.entity.shortworktime.BshmtWorktimeHist;
import nts.uk.ctx.at.shared.infra.entity.shortworktime.BshmtWorktimeHistPK_;
import nts.uk.ctx.at.shared.infra.entity.shortworktime.BshmtWorktimeHist_;

/**
 * The Class JpaSWorkTimeHistoryRepository.
 */
@Stateless
public class JpaSWorkTimeHistoryRepository extends JpaRepository
		implements SWorkTimeHistoryRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistoryRepository#
	 * findByKey(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<ShortWorkTimeHistory> findByKey(String empId, String histId) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<BshmtWorktimeHist> query = builder.createQuery(BshmtWorktimeHist.class);
		Root<BshmtWorktimeHist> root = query.from(BshmtWorktimeHist.class);

		List<Predicate> predicateList = new ArrayList<>();

		predicateList.add(builder.equal(
				root.get(BshmtWorktimeHist_.bshmtWorktimeHistPK).get(BshmtWorktimeHistPK_.sid),
				empId));
		predicateList.add(builder.equal(
				root.get(BshmtWorktimeHist_.bshmtWorktimeHistPK).get(BshmtWorktimeHistPK_.histId),
				histId));

		query.where(predicateList.toArray(new Predicate[] {}));

		List<BshmtWorktimeHist> result = em.createQuery(query).getResultList();

		// Check exist
		if (CollectionUtil.isEmpty(result)) {
			return Optional.empty();
		}

		// Return
		return Optional.of(new ShortWorkTimeHistory(new JpaSWorkTimeHistGetMemento(result)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistoryRepository#
	 * findByBaseDate(java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public Optional<ShortWorkTimeHistory> findByBaseDate(String empId, GeneralDate baseDate) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<BshmtWorktimeHist> query = builder.createQuery(BshmtWorktimeHist.class);
		Root<BshmtWorktimeHist> root = query.from(BshmtWorktimeHist.class);

		List<Predicate> predicateList = new ArrayList<>();

		predicateList.add(builder.equal(
				root.get(BshmtWorktimeHist_.bshmtWorktimeHistPK).get(BshmtWorktimeHistPK_.sid),
				empId));
		predicateList.add(builder.lessThanOrEqualTo(root.get(BshmtWorktimeHist_.strYmd), baseDate));
		predicateList
				.add(builder.greaterThanOrEqualTo(root.get(BshmtWorktimeHist_.endYmd), baseDate));

		query.where(predicateList.toArray(new Predicate[] {}));

		List<BshmtWorktimeHist> result = em.createQuery(query).getResultList();

		// Check exist
		if (CollectionUtil.isEmpty(result)) {
			return Optional.empty();
		}

		// Return
		return Optional.of(new ShortWorkTimeHistory(new JpaSWorkTimeHistGetMemento(result)));
	}

}
