/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.jobtitle.affiliate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistory;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistoryRepository;
import nts.uk.ctx.bs.employee.infra.entity.jobtitle.affiliate.KmnmtAffiliJobTitleHist;
import nts.uk.ctx.bs.employee.infra.entity.jobtitle.affiliate.KmnmtAffiliJobTitleHistPK_;
import nts.uk.ctx.bs.employee.infra.entity.jobtitle.affiliate.KmnmtAffiliJobTitleHist_;

/**
 * The Class JpaAffJobTitleHistoryRepository.
 */
@Stateless
public class JpaAffJobTitleHistoryRepository extends JpaRepository
		implements AffJobTitleHistoryRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.jobtitle.history.
	 * JobTitleHistoryRepository#searchJobTitleHistory(nts.arc.time.GeneralDate,
	 * java.util.List)
	 */
	@Override
	public List<AffJobTitleHistory> searchJobTitleHistory(GeneralDate baseDate,
			List<String> positionIds) {

		// check exist data
		if (CollectionUtil.isEmpty(positionIds)) {
			return new ArrayList<>();
		}

		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KMNMT_JOB_TITLE_HIST (KmnmtJobTitleHist SQL)
		CriteriaQuery<KmnmtAffiliJobTitleHist> cq = criteriaBuilder
				.createQuery(KmnmtAffiliJobTitleHist.class);

		// root data
		Root<KmnmtAffiliJobTitleHist> root = cq.from(KmnmtAffiliJobTitleHist.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// position id in data position id
		lstpredicateWhere
				.add(criteriaBuilder.and(root.get(KmnmtAffiliJobTitleHist_.kmnmtJobTitleHistPK)
						.get(KmnmtAffiliJobTitleHistPK_.jobId).in(positionIds)));

		// start date <= base date
		lstpredicateWhere.add(criteriaBuilder
				.lessThanOrEqualTo(root.get(KmnmtAffiliJobTitleHist_.kmnmtJobTitleHistPK)
						.get(KmnmtAffiliJobTitleHistPK_.strD), baseDate));

		// endDate >= base date
		lstpredicateWhere.add(criteriaBuilder
				.greaterThanOrEqualTo(root.get(KmnmtAffiliJobTitleHist_.endD), baseDate));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<KmnmtAffiliJobTitleHist> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream()
				.map(entity -> new AffJobTitleHistory(new JpaAffJobTitleHistoryGetMemento(entity)))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.jobtitle.history.
	 * JobTitleHistoryRepository#searchJobTitleHistory(java.util.List,
	 * nts.arc.time.GeneralDate, java.util.List)
	 */
	@Override
	public List<AffJobTitleHistory> searchJobTitleHistory(List<String> employeeIds,
			GeneralDate baseDate, List<String> positionIds) {
		// check exist data
		if (CollectionUtil.isEmpty(positionIds) || CollectionUtil.isEmpty(employeeIds)) {
			return new ArrayList<>();
		}

		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KMNMT_JOB_TITLE_HIST (KmnmtJobTitleHist SQL)
		CriteriaQuery<KmnmtAffiliJobTitleHist> cq = criteriaBuilder
				.createQuery(KmnmtAffiliJobTitleHist.class);

		// root data
		Root<KmnmtAffiliJobTitleHist> root = cq.from(KmnmtAffiliJobTitleHist.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// position id in data position id
		lstpredicateWhere
				.add(criteriaBuilder.and(root.get(KmnmtAffiliJobTitleHist_.kmnmtJobTitleHistPK)
						.get(KmnmtAffiliJobTitleHistPK_.jobId).in(positionIds)));

		// employee id in data employee id
		lstpredicateWhere
				.add(criteriaBuilder.and(root.get(KmnmtAffiliJobTitleHist_.kmnmtJobTitleHistPK)
						.get(KmnmtAffiliJobTitleHistPK_.empId).in(employeeIds)));

		// start date <= base date
		lstpredicateWhere.add(criteriaBuilder
				.lessThanOrEqualTo(root.get(KmnmtAffiliJobTitleHist_.kmnmtJobTitleHistPK)
						.get(KmnmtAffiliJobTitleHistPK_.strD), baseDate));

		// endDate >= base date
		lstpredicateWhere.add(criteriaBuilder
				.greaterThanOrEqualTo(root.get(KmnmtAffiliJobTitleHist_.endD), baseDate));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<KmnmtAffiliJobTitleHist> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream()
				.map(entity -> new AffJobTitleHistory(new JpaAffJobTitleHistoryGetMemento(entity)))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.employee.jobtile.
	 * AffJobTitleHistoryRepository#findWithOptions(java.util.List,
	 * java.util.List, nts.arc.time.GeneralDate)
	 */
	@Override
	public List<AffJobTitleHistory> findWithRelativeOptions(List<String> employeeIds,
			List<String> positionIds, GeneralDate baseDate) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KMNMT_JOB_TITLE_HIST (KmnmtJobTitleHist SQL)
		CriteriaQuery<KmnmtAffiliJobTitleHist> cq = criteriaBuilder
				.createQuery(KmnmtAffiliJobTitleHist.class);

		// root data
		Root<KmnmtAffiliJobTitleHist> root = cq.from(KmnmtAffiliJobTitleHist.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// employee id in data employee id
		if (!CollectionUtil.isEmpty(employeeIds)) {
			lstpredicateWhere.add(root.get(KmnmtAffiliJobTitleHist_.kmnmtJobTitleHistPK)
					.get(KmnmtAffiliJobTitleHistPK_.empId).in(employeeIds));
		}

		// employee id in data employee id
		if (!CollectionUtil.isEmpty(positionIds)) {
			lstpredicateWhere.add(root.get(KmnmtAffiliJobTitleHist_.kmnmtJobTitleHistPK)
					.get(KmnmtAffiliJobTitleHistPK_.jobId).in(positionIds));
		}

		if (baseDate != null) {
			// start date <= base date
			lstpredicateWhere.add(criteriaBuilder
					.lessThanOrEqualTo(root.get(KmnmtAffiliJobTitleHist_.kmnmtJobTitleHistPK)
							.get(KmnmtAffiliJobTitleHistPK_.strD), baseDate));

			// endDate >= base date
			lstpredicateWhere.add(criteriaBuilder
					.greaterThanOrEqualTo(root.get(KmnmtAffiliJobTitleHist_.endD), baseDate));
		}

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<KmnmtAffiliJobTitleHist> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream()
				.map(entity -> new AffJobTitleHistory(new JpaAffJobTitleHistoryGetMemento(entity)))
				.collect(Collectors.toList());
	}

}
