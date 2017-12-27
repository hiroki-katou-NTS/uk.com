/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.classification.affiliate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import nts.uk.ctx.bs.employee.dom.classification.affiliate.AffClassHistory;
import nts.uk.ctx.bs.employee.dom.classification.affiliate.AffClassHistoryRepository;
import nts.uk.ctx.bs.employee.infra.entity.classification.affiliate.KmnmtAffiliClassificationHist;
import nts.uk.ctx.bs.employee.infra.entity.classification.affiliate.KmnmtAffiliClassificationHistPK_;
import nts.uk.ctx.bs.employee.infra.entity.classification.affiliate.KmnmtAffiliClassificationHist_;

/**
 * The Class JpaAffiliationClassificationHistoryRepository.
 */
@Stateless
public class JpaAffClassHistoryRepository extends JpaRepository
		implements AffClassHistoryRepository {

	/** The Constant FIRST_ITEM_INDEX. */
	private static final int FIRST_ITEM_INDEX = 0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.classification.history.
	 * ClassificationHistoryRepository#searchClassification(nts.arc.time.
	 * GeneralDate, java.util.List)
	 */
	@Override
	public List<AffClassHistory> searchClassification(GeneralDate baseDate,
			List<String> classificationCodes) {

		if (CollectionUtil.isEmpty(classificationCodes)) {
			return new ArrayList<>();
		}

		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KMNMT_CLASSIFICATION_HIST (KmnmtClassificationHist SQL)
		CriteriaQuery<KmnmtAffiliClassificationHist> cq = criteriaBuilder
				.createQuery(KmnmtAffiliClassificationHist.class);

		// root data
		Root<KmnmtAffiliClassificationHist> root = cq.from(KmnmtAffiliClassificationHist.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// classification in data classification
		lstpredicateWhere.add(criteriaBuilder
				.and(root.get(KmnmtAffiliClassificationHist_.kmnmtClassificationHistPK)
						.get(KmnmtAffiliClassificationHistPK_.clscd).in(classificationCodes)));

		// start date <= base date
		lstpredicateWhere.add(criteriaBuilder.lessThanOrEqualTo(
				root.get(KmnmtAffiliClassificationHist_.kmnmtClassificationHistPK)
						.get(KmnmtAffiliClassificationHistPK_.strD),
				baseDate));

		// endDate >= base date
		lstpredicateWhere.add(criteriaBuilder
				.greaterThanOrEqualTo(root.get(KmnmtAffiliClassificationHist_.endD), baseDate));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<KmnmtAffiliClassificationHist> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(category -> toDomain(category))
				.collect(Collectors.toList());
	}

	/**
	 * To domain.
	 *
	 * @param entity
	 *            the entity
	 * @return the classification history
	 */
	private AffClassHistory toDomain(KmnmtAffiliClassificationHist entity) {
		return new AffClassHistory(new JpaAffClassHistoryGetMemento(entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.classification.history.
	 * ClassificationHistoryRepository#searchClassification(java.util.List,
	 * nts.arc.time.GeneralDate, java.util.List)
	 */
	@Override
	public List<AffClassHistory> searchClassification(List<String> employeeIds,
			GeneralDate baseDate, List<String> classificationCodes) {

		// check not data
		if (CollectionUtil.isEmpty(classificationCodes) || CollectionUtil.isEmpty(employeeIds)) {
			return new ArrayList<>();
		}

		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KMNMT_CLASSIFICATION_HIST (KmnmtClassificationHist SQL)
		CriteriaQuery<KmnmtAffiliClassificationHist> cq = criteriaBuilder
				.createQuery(KmnmtAffiliClassificationHist.class);

		// root data
		Root<KmnmtAffiliClassificationHist> root = cq.from(KmnmtAffiliClassificationHist.class);

		// select root
		cq.select(root);
		
		List<KmnmtAffiliClassificationHist> resultList = new ArrayList<>();
		CollectionUtil.split(employeeIds, 1000, subList -> {
			CollectionUtil.split(classificationCodes, 1000, classSubList -> {
				// add where
				List<Predicate> lstpredicateWhere = new ArrayList<>();

				// employee id in data employee id
				lstpredicateWhere.add(criteriaBuilder
						.and(root.get(KmnmtAffiliClassificationHist_.kmnmtClassificationHistPK)
								.get(KmnmtAffiliClassificationHistPK_.empId).in(employeeIds)));

				// classification in data classification
				lstpredicateWhere.add(criteriaBuilder
						.and(root.get(KmnmtAffiliClassificationHist_.kmnmtClassificationHistPK)
								.get(KmnmtAffiliClassificationHistPK_.clscd).in(classificationCodes)));

				// start date <= base date
				lstpredicateWhere.add(criteriaBuilder.lessThanOrEqualTo(
						root.get(KmnmtAffiliClassificationHist_.kmnmtClassificationHistPK)
								.get(KmnmtAffiliClassificationHistPK_.strD),
						baseDate));

				// endDate >= base date
				lstpredicateWhere.add(criteriaBuilder
						.greaterThanOrEqualTo(root.get(KmnmtAffiliClassificationHist_.endD), baseDate));

				// set where to SQL
				cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

				// create query
				TypedQuery<KmnmtAffiliClassificationHist> query = em.createQuery(cq);
				resultList.addAll(query.getResultList());
			});
		});
		
		// convert.
		return resultList.stream().map(category -> toDomain(category))
				.collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.classification.affiliate.AffClassHistoryRepository#getAssignedClassificationBy(java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public Optional<AffClassHistory> getAssignedClassificationBy(String employeeId,
			GeneralDate baseDate) {

		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KMNMT_CLASSIFICATION_HIST (KmnmtClassificationHist SQL)
		CriteriaQuery<KmnmtAffiliClassificationHist> cq = criteriaBuilder
				.createQuery(KmnmtAffiliClassificationHist.class);

		// root data
		Root<KmnmtAffiliClassificationHist> root = cq.from(KmnmtAffiliClassificationHist.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// employee id in data employee id
		lstpredicateWhere.add(criteriaBuilder
				.equal(root.get(KmnmtAffiliClassificationHist_.kmnmtClassificationHistPK)
						.get(KmnmtAffiliClassificationHistPK_.empId), employeeId));

		// start date <= base date
		lstpredicateWhere.add(criteriaBuilder.lessThanOrEqualTo(
				root.get(KmnmtAffiliClassificationHist_.kmnmtClassificationHistPK)
						.get(KmnmtAffiliClassificationHistPK_.strD),
				baseDate));

		// endDate >= base date
		lstpredicateWhere.add(criteriaBuilder
				.greaterThanOrEqualTo(root.get(KmnmtAffiliClassificationHist_.endD), baseDate));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<KmnmtAffiliClassificationHist> query = em.createQuery(cq);
		
		List<KmnmtAffiliClassificationHist> result = query.getResultList(); 
		
		// Check exist
		if(CollectionUtil.isEmpty(result)) {
			return Optional.empty();
		}

		// exclude select
		return Optional.of(new AffClassHistory(
				new JpaAffClassHistoryGetMemento(result.get(FIRST_ITEM_INDEX))));
	}

}
